package com.eclipsesource.workflow.ui.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class WorkflowMainPage extends WizardPage {
	private static final Image CHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault()
			.getImage("org.eclipse.papyrus.uml.diagram.wizards", "icons/checked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final Image UNCHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault()
			.getImage("org.eclipse.papyrus.uml.diagram.wizards", "icons/unchecked.gif"); //$NON-NLS-1$ //$NON-NLS-2$
	private List<String> selectedActors = new ArrayList<String>();
	private List<String> selectedComponents = new ArrayList<String>();
	private static final String[] ACTORS = { "Assistant", "Devloper", "Managment", "CRM Team", "QA Team" };
	private static final String[] COMPONENTS = { "Printer", "MD5 Check", "MailServer", "PrinterServer", "3D Printer" };
	private Text modelName;
	private TableViewer actorViewer;

	private TableViewer componentViewer;

	private boolean pageComplete = true;

	private boolean noProject;

	public WorkflowMainPage(String pageName) {
		super(pageName);
		this.setTitle("Create Workflow Model");
		this.setDescription("Select the model name and selected the desired actors");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.DOWN, true, false));
		GridLayout gridLayout = new GridLayout(1, false);
		composite.setLayout(gridLayout);
		setControl(composite);
		createNameGroup(composite);
		createActorTable(composite);
		createComponentTable(composite);
		validatePage();

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	private void createComponentTable(Composite container) {

		componentViewer = new TableViewer(container,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		componentViewer.getTable().setLayoutData(gridData);

		// viewer.setContentProvider(new ArrayContentProvider());
		final Table table = componentViewer.getTable();
		componentViewer.setContentProvider(new ArrayContentProvider());
		table.setHeaderVisible(true);

		// The check column
		TableViewerColumn colCheckbox = createTableViewerColumn("", 20, componentViewer); //$NON-NLS-1$
		colCheckbox.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {

				if (selectedComponents.contains(element)) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}

			}

		});
		colCheckbox.setEditingSupport(new EditingSupport(actorViewer) {

			private CheckboxCellEditor checkboxCellEditor;

			@Override
			protected void setValue(Object element, Object value) {
				if (checkboxCellEditor.getValue() == Boolean.TRUE) {
					selectedComponents.add((String) element);
				} else {
					selectedComponents.remove(element);
				}
				componentViewer.update(element, null);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				checkboxCellEditor = new CheckboxCellEditor(container, SWT.CHECK | SWT.READ_ONLY);
				return checkboxCellEditor;
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected Object getValue(Object element) {

				return selectedComponents.contains(element);
			}
		});
		// no need to enable the resize on the check box column
		colCheckbox.getColumn().setResizable(false);

		// The Diagram name column
		TableViewerColumn colComponent = createTableViewerColumn("Components", 130, componentViewer);
		colComponent.setLabelProvider(new ColumnLabelProvider());
		componentViewer.setInput(COMPONENTS);
	}

	private void createActorTable(Composite container) {

		actorViewer = new TableViewer(container,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		actorViewer.getTable().setLayoutData(gridData);

		// viewer.setContentProvider(new ArrayContentProvider());
		final Table table = actorViewer.getTable();
		actorViewer.setContentProvider(new ArrayContentProvider());
		table.setHeaderVisible(true);

		// The check column
		TableViewerColumn colCheckbox = createTableViewerColumn("", 20, actorViewer); //$NON-NLS-1$
		colCheckbox.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {

				if (selectedActors.contains(element)) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}

			}

		});
		colCheckbox.setEditingSupport(new EditingSupport(actorViewer) {

			private CheckboxCellEditor checkboxCellEditor;

			@Override
			protected void setValue(Object element, Object value) {
				if (checkboxCellEditor.getValue() == Boolean.TRUE) {
					selectedActors.add((String) element);
				} else {
					selectedActors.remove(element);
				}
				actorViewer.update(element, null);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				checkboxCellEditor = new CheckboxCellEditor(container, SWT.CHECK | SWT.READ_ONLY);
				return checkboxCellEditor;
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected Object getValue(Object element) {
				// TODO Auto-generated method stub
				return selectedActors.contains(element);
			}
		});
		// no need to enable the resize on the check box column
		colCheckbox.getColumn().setResizable(false);

		// The Diagram name column
		TableViewerColumn colActor = createTableViewerColumn("Actors", 130, actorViewer);
		colActor.setLabelProvider(new ColumnLabelProvider());
		actorViewer.setInput(ACTORS);
	}

	private void createNameGroup(Composite container) {
		// TODO Auto-generated method stub
		Label label = new Label(container, SWT.NULL);
		label.setText("&Model name:");

		modelName = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		modelName.setLayoutData(gd);
		modelName.addModifyListener(ls -> validatePage());
	}

	@Override
	public boolean isPageComplete() {
		return pageComplete;
	}

	private void validatePage() {
		pageComplete = true;
		setErrorMessage(null);
		setMessage(null);
		if (noProject) {
			setErrorMessage("No project has been selected. Please set up a Java project before using this wizard");
			pageComplete = false;
			return;
		}
		if (modelName.getText() == null || modelName.getText().isEmpty()) {
			setErrorMessage("Please chooose a valid model name");
			pageComplete = false;
			return;
		}
		getContainer().updateButtons();
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

	public void setModelName(String name) {
		modelName.setText(name);
	}

	public String getModelName() {
		return modelName.getText();
	}

	public void setSelectedComponents(List<String> components) {
		this.selectedComponents = components;
	}

	public void setSelectedActors(List<String> actors) {
		this.selectedActors = actors;
	}

	public List<String> getSelectedActors() {
		return selectedActors;
	}

	public List<String> getSelectedComponents() {
		return selectedComponents;
	}

	public void setNoProject(boolean noProject) {
		this.noProject = noProject;
	}
}
