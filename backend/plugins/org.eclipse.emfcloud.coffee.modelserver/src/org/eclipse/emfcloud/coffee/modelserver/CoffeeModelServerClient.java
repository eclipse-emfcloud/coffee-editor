/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.ModelServerNotification;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.client.SubscriptionListener;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParameters;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV1;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.common.codecs.DefaultJsonCodec;
import org.eclipse.emfcloud.modelserver.common.codecs.XmiCodec;
import org.eclipse.emfcloud.modelserver.emf.common.JsonResponseMember;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableSet;

import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class CoffeeModelServerClient extends ModelServerClient {

	private Map<String, List<WebSocket>> coffeeOpenSockets = new HashMap<>();

	private static final Set<String> DEFAULT_SUPPORTED_FORMATS = ImmutableSet.of(ModelServerPathParameters.FORMAT_JSON,
			ModelServerPathParameters.FORMAT_XMI, CoffeeResource.FILE_EXTENSION);

	private static Logger LOGGER = Logger.getLogger(CoffeeModelServerClient.class.getSimpleName());

	public CoffeeModelServerClient(final String baseUrl) throws MalformedURLException {
		super(baseUrl);
	}

	@Override
	protected boolean isSupportedFormat(final String format) {
		return DEFAULT_SUPPORTED_FORMATS.contains(format);
	}

	@Override
	public Optional<EObject> decode(final String payload, final String format) {
		try {
			if (format.equals(CoffeeResource.FILE_EXTENSION)) {
				return new CoffeeCodec().decode(payload);
			} else if (format.equals(ModelServerPathParametersV1.FORMAT_XMI)) {
				return new XmiCodec().decode(payload);
			}
			return new DefaultJsonCodec().decode(payload);
		} catch (DecodingException e) {
			LOGGER.error("Decoding of " + payload + " with " + format + " format failed");
		}
		return Optional.empty();
	}

	protected void doSubscribe(final String modelUri, final SubscriptionListener subscriptionListener,
			final Request request) {
		final WebSocket socket = client.newWebSocket(request, new WebSocketListener() {
			@Override
			public void onOpen(@NotNull final WebSocket webSocket, @NotNull final okhttp3.Response response) {
				subscriptionListener.onOpen(new Response<>(response, body -> require(Optional.ofNullable(body))));
			}

			@Override
			public void onMessage(@NotNull final WebSocket webSocket, @NotNull final String text) {
				Optional<String> type = CoffeeModelServerClient.this.parseJsonField(text, JsonResponseMember.TYPE);
				Optional<String> data = CoffeeModelServerClient.this.parseJsonField(text, JsonResponseMember.DATA);
				subscriptionListener.onNotification(new ModelServerNotification(type.orElse("unknown"), data));
			}

			@Override
			public void onClosing(@NotNull final WebSocket webSocket, final int code, @NotNull final String reason) {
				subscriptionListener.onClosing(code, reason);
			}

			@Override
			public void onClosed(@NotNull final WebSocket webSocket, final int code, @NotNull final String reason) {
				subscriptionListener.onClosed(code, reason);
			}

			@Override
			public void onFailure(@NotNull final WebSocket webSocket, @NotNull final Throwable t,
					@Nullable final okhttp3.Response response) {
				if (response != null) {
					subscriptionListener.onFailure(t, new Response<>(response));
				} else {
					subscriptionListener.onFailure(t);
				}
			}
		});
		List<WebSocket> list = coffeeOpenSockets.get(modelUri);
		if (list == null) {
			List<WebSocket> arrayList = new ArrayList<WebSocket>();
			arrayList.add(socket);
			coffeeOpenSockets.put(modelUri, arrayList);
		} else {
			list.add(socket);
			coffeeOpenSockets.put(modelUri, list);
		}
	}

	@Override
	public boolean unsubscribe(final String modelUri) {
		final List<WebSocket> webSocket = coffeeOpenSockets.get(modelUri);
		if (webSocket != null) {
			webSocket.forEach(socket -> socket.close(1000, "Websocket closed by client."));
			return true;
		}
		return false;
	}

}
