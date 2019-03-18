package com.ovi.ic_project.data;

public enum ScoreLevel {
	LOW("red.png"), MEDIUM("orange.png"), HIGH("yellow.png"), BEST("green.png");

	private String image;

	private ScoreLevel(String image) {
		this.setImage(image);
	}

	public String getImagePath() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
