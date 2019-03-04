package com.ovi.ic_project;

import java.util.Objects;

import com.ovi.ic_project.gui.GUIManager;

public class ApplicationSession {

	private static ApplicationSession instance;
	private GUIManager guiManager;

	public ApplicationSession() {
		guiManager = new GUIManager();
	}

	public static void run() {
		if (Objects.isNull(instance)) {
			instance = new ApplicationSession();
		}
		instance.guiManager.run();
	}

	public GUIManager getGuiManager() {
		return guiManager;
	}

	public void setGuiManager(GUIManager guiManager) {
		this.guiManager = guiManager;
	}

}
