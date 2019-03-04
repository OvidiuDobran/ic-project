package com.ovi.ic_project.gui;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class BackgroundImageManager {

	private Display display;
	private Image image;
	private static BackgroundImageManager instance;

	private BackgroundImageManager() {
		display = Display.getCurrent();
		image = new Image(display, "map.png");
	}

	public static BackgroundImageManager getInstance() {
		if (Objects.isNull(instance)) {
			instance = new BackgroundImageManager();
		}
		return instance;
	}

	public int getBackgroundWidth() {
		return image.getBounds().width;
	}

	public int getBackgroundHeight() {
		return image.getBounds().height;
	}

}
