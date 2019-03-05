package com.ovi.ic_project.data;

public enum CorruptionLevel {
	
	LOW("yellow.png"), MEDIUM("orange.png"), HIGH("red.png");
	

	private String image;

	private CorruptionLevel(String image) {
		this.setImage(image);
	}

	public String getImagePath() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
