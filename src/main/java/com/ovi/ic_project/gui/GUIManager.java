package com.ovi.ic_project.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.ovi.ic_project.utils.CONSTANTS;

public class GUIManager {

	private static GUIManager instance;
	private Display display;
	private Shell shell;
	private List<CustomComposite> pages = new ArrayList<>();
	private Composite contentPanel;
	private StackLayout layout;
	private Button nextButton;

	private GUIManager() {
		this.display = new Display();
		this.shell = new Shell(display, SWT.ALT | SWT.MAX | SWT.MIN | SWT.CLOSE);
	}

	public static GUIManager getInstance() {
		if (Objects.isNull(instance)) {
			instance = new GUIManager();
		}
		return instance;
	}

	public void run() {
		shell.open();
		shell.setMaximized(true);
		initializeContent();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void initializeContent() {
		shell.setLayout(new FormLayout());
		contentPanel = new Composite(shell, SWT.BORDER);
		layout = new StackLayout();
		contentPanel.setLayout(layout);

		FormData contentPanelData = new FormData();
		contentPanelData.top = new FormAttachment(0, 0);
		contentPanelData.right = new FormAttachment(100, 0);
		contentPanelData.left = new FormAttachment(0, 0);
		contentPanelData.bottom = new FormAttachment(100, -80);
		contentPanel.setLayoutData(contentPanelData);

		nextButton = new Button(shell, SWT.NONE);
		nextButton.setText("Next");
		FormData nextButtonData = new FormData();
		nextButtonData.right = new FormAttachment(100, -20);
		nextButtonData.bottom = new FormAttachment(100, -20);
		nextButtonData.height = CONSTANTS.BUTTON_HEIGHT;
		nextButtonData.width = CONSTANTS.BUTTON_WIDTH;
		nextButton.setLayoutData(nextButtonData);

		CustomComposite welcomePage = new WelcomePage(contentPanel, SWT.NONE);
		CustomComposite mapPage = new MapPage(contentPanel, SWT.NONE);
		pages.add(welcomePage);
		pages.add(mapPage);

		addContentInPages();

		addWizardBehaviour();

		changeToPage(welcomePage);

	}

	private void addContentInPages() {
		pages.forEach(page -> page.createContent());
	}

	private void addWizardBehaviour() {
		nextButton.addListener(SWT.Selection, event -> {
			changeToNextPage();
		});
	}

	private void changeToNextPage() {
		Control currentPage = layout.topControl;
		if (pages.indexOf(currentPage) != pages.size() - 1) {
			CustomComposite nextPage = pages.get(pages.indexOf(currentPage) + 1);
			changeToPage(nextPage);
		}
	}

	private void changeToPage(CustomComposite page) {
		layout.topControl = page;
		contentPanel.layout();
	}

}
