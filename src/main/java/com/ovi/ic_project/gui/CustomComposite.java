package com.ovi.ic_project.gui;

import org.eclipse.swt.widgets.Composite;

public abstract class CustomComposite extends Composite {

	public CustomComposite(Composite parent, int style) {
		super(parent, style);
		createContent();
	}

	public abstract void createContent();

}
