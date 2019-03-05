package com.ovi.ic_project.gui;

import javax.rmi.CORBA.Util;

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

import com.ovi.ic_project.utils.Utils;

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

		Button labelText = createButtonAt(map, 44.439663, 26.096306);

		labelText.addListener(SWT.Selection, event -> {
			MessageBox messageBox = new MessageBox(getShell());
			messageBox.open();
		});

		Button labelText2 = createButtonAt(map, 46.770439, 23.591423);

		Button labelText3 = createButtonAt(map, 45.75372, 21.22571);

	}

	private Button createButtonAt(Composite map, double realLatitude, double realLongitude) {
		Button labelText = new Button(map, SWT.NONE);
		labelText.setText("");
		labelText.setImage(new Image(Display.getCurrent(), "red.png"));
		FormData labelTextData = new FormData();
		labelTextData.bottom = new FormAttachment(100 - Utils.getVirtualLatitudeOf(realLatitude), 0);
		labelTextData.left = new FormAttachment(Utils.getVirtualLongitudeOf(realLongitude), 0);
		labelTextData.height = 15;
		labelTextData.width = 15;
		labelText.setLayoutData(labelTextData);
		return labelText;
	}

}
