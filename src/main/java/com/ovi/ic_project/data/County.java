package com.ovi.ic_project.data;

import java.util.ArrayList;
import java.util.List;

public class County {
	private String capitalName;
	private String countyName;
	private List<File> files = new ArrayList<File>();
	private Double longitude;
	private Double latitude;
	private Integer score;

	public County() {
		super();
	}

	public County(String name, String countyName, Double longitude, Double latitude, Integer score) {
		super();
		this.capitalName = name;
		this.countyName = countyName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.setScore(score);
	}

	public String getName() {
		return capitalName;
	}

	public void setName(String name) {
		this.capitalName = name;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "County [capitalName=" + capitalName + ", countyName=" + countyName + ", files=" + files + ", longitude="
				+ longitude + ", latitude=" + latitude + ", score=" + score + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countyName == null) ? 0 : countyName.hashCode());
		result = prime * result + ((capitalName == null) ? 0 : capitalName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		County other = (County) obj;
		if (countyName == null) {
			if (other.countyName != null)
				return false;
		} else if (!countyName.equals(other.countyName))
			return false;
		if (capitalName == null) {
			if (other.capitalName != null)
				return false;
		} else if (!capitalName.equals(other.capitalName))
			return false;
		return true;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public int getNoOfOffences() {
		return this.getFiles().stream().mapToInt(file -> file.getOffences().size()).sum();
	}

}
