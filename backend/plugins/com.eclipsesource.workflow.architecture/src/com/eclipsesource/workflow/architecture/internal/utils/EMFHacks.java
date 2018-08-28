/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package com.eclipsesource.workflow.architecture.internal.utils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * A number of utilities for hacking EMF objects.
 */
public class EMFHacks {

	/**
	 * Not instantiable by clients.
	 */
	private EMFHacks() {
		super();
	}

	/**
	 * Queries whether there is currently a read-write transaction open on the
	 * current thread in the context of an {@link object}.
	 * 
	 * @param object
	 *            an object
	 * @return whether its editing domain currently has an open read-write
	 *         transaction
	 */
	public static boolean isReadWriteTransactionActive(EObject object) {
		InternalTransactionalEditingDomain domain = TypeUtils.as(TransactionUtil.getEditingDomain(object),
				InternalTransactionalEditingDomain.class);
		Transaction active = (domain == null) ? null : domain.getActiveTransaction();
		return (active != null) && !active.isReadOnly();
	}

	/**
	 * Performs an {@code action} during which all notifications from the given
	 * {@code notifier} are suppressed.
	 * 
	 * @param notifier
	 *            a notifier. Must not be {@code null}
	 * @param action
	 *            an action to perform on the {@code notifier} silently
	 * 
	 * @return the {@code notifier}, once again notifying, for the convenience of
	 *         call chaining
	 * 
	 * @see #coneOfSilence(Notifier...)
	 */
	public static <T extends Notifier> T silently(T notifier, Consumer<? super T> action) {
		boolean wasDeliver = notifier.eDeliver();
		notifier.eSetDeliver(false);
		try {
			action.accept(notifier);
		} finally {
			notifier.eSetDeliver(wasDeliver);
		}

		return notifier;
	}

	/**
	 * Performs an {@code action} during which all notifications from an optional
	 * {@code notifier} are suppressed.
	 * 
	 * @param notifier
	 *            an optional notifier. Must not be {@code null} but may be
	 *            {@linkplain Optional#isPresent() absent}
	 * @param action
	 *            an action to perform on the {@code notifier} silently if it is
	 *            {@linkplain Optional#isPresent() present}
	 * 
	 * @return the optional {@code notifier}, once again notifying, for the
	 *         convenience of call chaining
	 * 
	 * @see #coneOfSilence(Notifier...)
	 */
	public static <T extends Notifier> Optional<T> silently(Optional<T> notifier, Consumer<? super T> action) {
		notifier.ifPresent(n -> silently(n, action));
		return notifier;
	}

	/**
	 * Obtains an executor that suppresses all notifications from the given
	 * {@code notifiers} for the duration of each action that it executes.
	 * Notifications are only suppressed for the duration of each action, not at any
	 * other time.
	 * 
	 * @param notifiers
	 *            zero or more notifiers to silence while running actions
	 * 
	 * @return a cone of silence
	 * 
	 * @see #silently(Notifier, Consumer)
	 * @see #silently(Optional, Consumer)
	 */
	public static ConeOfSilence coneOfSilence(Notifier... notifiers) {
		return new ConeOfSilence(notifiers);
	}

	/**
	 * Injects a notification of the addition of a new object to the model. This
	 * would usually only be necessary if it was originally added
	 * {@link #silently(Notifier, Consumer) silently}.
	 * 
	 * @param newObject
	 *            the new object added to the model
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code newObject} was not actually added to the model
	 * 
	 * @see #silently(Notifier, Consumer)
	 */
	public static void notifyAdded(EObject newObject) {
		InternalEObject notifier = (InternalEObject) newObject.eContainer();
		if (notifier == null) {
			throw new IllegalArgumentException("newObject is not attached"); //$NON-NLS-1$
		}

		EReference containment = newObject.eContainmentFeature();
		int index = (containment.isMany()) ? ((List<?>) notifier.eGet(containment)).indexOf(newObject)
				: Notification.NO_INDEX;

		ENotificationImpl notification = new ENotificationImpl(notifier, Notification.ADD, containment, null, newObject,
				index);
		notifier.eNotify(notification);
	}

	//
	// Nested types
	//

	/**
	 * An executor that suppresses notifications in a set of {@linkplain Notifier
	 * notifiers} for the duration of any action that it runs.
	 */
	public static final class ConeOfSilence implements Executor {
		private final List<Notifier> scope;

		ConeOfSilence(Notifier... notifiers) {
			super();

			this.scope = Stream.of(notifiers).filter(Objects::nonNull).collect(Collectors.toList());
		}

		/**
		 * Ensures the silence of the given {@code notifiers} when I run an action.
		 * Whenever I am not running any action, these notifiers are not silenced.
		 * 
		 * @param notifiers
		 *            notifiers to silence
		 * @return myself, for the convenience of call chaining
		 */
		public ConeOfSilence with(Notifier... notifiers) {
			for (int i = 0; i < notifiers.length; i++) {
				if (notifiers[i] != null) {
					scope.add(notifiers[i]);
				}
			}
			return this;
		}

		/**
		 * Executes an {@code action} without the possibility of any of the silenced
		 * notifiers producing notifications.
		 *
		 * @param action
		 *            an action to run silently
		 */
		@Override
		public void execute(Runnable command) {
			Boolean[] wasDeliver = scope.stream().map(Notifier::eDeliver).toArray(Boolean[]::new);
			scope.forEach(n -> n.eSetDeliver(false));
			try {
				command.run();
			} finally {
				IntStream.range(0, wasDeliver.length).forEach(i -> scope.get(i).eSetDeliver(wasDeliver[i]));
			}
		}
	}
}