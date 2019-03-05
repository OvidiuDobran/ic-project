package com.ovi.ic_project.utils;

public class Utils {
	public static int getVirtualLongitudeOf(Double realLongitude) {
		int virtualLongitude = (int) (((realLongitude - CONSTANTS.minLongitude) * 100)
				/ (CONSTANTS.maxLongitude - CONSTANTS.minLongitude));
		return virtualLongitude;
	}

	public static int getVirtualLatitudeOf(Double realLatitude) {
		int virtualLatitude = (int) (((realLatitude - CONSTANTS.minLatitude) * 100)
				/ (CONSTANTS.maxLatitude - CONSTANTS.minLatitude));
		return virtualLatitude;
	}
}
