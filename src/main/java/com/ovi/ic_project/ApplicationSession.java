package com.ovi.ic_project;

import java.util.Objects;

import com.ovi.ic_project.gui.GUIManager;
import com.ovi.ic_project.services.InputService;

public class ApplicationSession {

	private static ApplicationSession instance;
	private InputService inputService;
	private GUIManager guiManager;

	private ApplicationSession() {
		inputService = InputService.getInstance();
		guiManager = GUIManager.getInstance();
	}

	public static ApplicationSession getInstance() {
		if (Objects.isNull(instance)) {
			instance = new ApplicationSession();
		}
		return instance;
	}

	public void run() {
		this.inputService.readCitiesFromExcel();
		this.inputService.readFilesFromExcel();
		this.guiManager.run();
	}

	public GUIManager getGuiManager() {
		return guiManager;
	}

	public void setGuiManager(GUIManager guiManager) {
		this.guiManager = guiManager;
	}

}
