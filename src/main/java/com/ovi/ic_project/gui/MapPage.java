package com.ovi.ic_project.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

public class MapPage extends CustomComposite {

	public MapPage(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void createContent() {
		FormLayout formLayout = new FormLayout();
		setLayout(formLayout);

		Composite map = new Composite(this, SWT.NONE);
		map.setLayout(new FormLayout());
		Image image = new Image(Display.getCurrent(), "map.png");
		map.setBackgroundImage(image);

		FormData mapData = new FormData();
		mapData.top = new FormAttachment(0, 10);
		mapData.left = new FormAttachment(0, 10);
		mapData.height = BackgroundImageManager.getInstance().getBackgroundHeight();
		mapData.width = BackgroundImageManager.getInstance().getBackgroundWidth();
		map.setLayoutData(mapData);

		Button labelText = new Button(map, SWT.NONE);
		labelText.setText("");
		labelText.setImage(new Image(Display.getCurrent(), "red.png"));
		FormData labelTextData = new FormData();
		labelTextData.top = new FormAttachment(0, 300);
		labelTextData.left = new FormAttachment(0, 300);
		labelTextData.height = 15;
		labelTextData.width = 15;
		labelText.setLayoutData(labelTextData);
		
		labelText.addListener(SWT.Selection, event->{
			MessageBox messageBox = new MessageBox(getShell());
			messageBox.open();
		});

	}

}
