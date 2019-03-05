package com.ovi.ic_project.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ovi.ic_project.data.City;
import com.ovi.ic_project.data.CorruptionLevel;
import com.ovi.ic_project.data.Offence;
import com.ovi.ic_project.data.Party;
import com.ovi.ic_project.data.Politician;

public class InputService {
	private static InputService instance;
	private List<City> cities = new ArrayList<City>();
	private List<Politician> politicians = new ArrayList<Politician>();
	private List<Offence> offences = new ArrayList<Offence>();
	private List<Party> parties = new ArrayList<Party>();
	private List<com.ovi.ic_project.data.File> files = new ArrayList<com.ovi.ic_project.data.File>();

	private InputService() {

	}

	public static InputService getInstance() {
		if (Objects.isNull(instance)) {
			instance = new InputService();
		}
		return instance;
	}

	public void readCitiesFromExcel() {

		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(new File("Cities.xls"));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheetAt(0);

		sheet.forEach(row -> {
			String cityName = row.getCell(0).getStringCellValue();
			String countyName = row.getCell(1).getStringCellValue();
			Double latitude = row.getCell(2).getNumericCellValue();
			Double longitude = row.getCell(3).getNumericCellValue();
			City city = new City(cityName, countyName, longitude, latitude);
			getCities().add(city);
			System.out.println(city);
		});

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readFilesFromExcel() {

		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(new File("Files.xls"));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheetAt(0);

		sheet.forEach(row -> {
			com.ovi.ic_project.data.File file = new com.ovi.ic_project.data.File();
			String politicianName = row.getCell(0).getStringCellValue();
			Politician politician = new Politician(politicianName);
			if (!politicians.contains(politician)) {
				politicians.add(politician);
			}
			file.setPolitician(politician);
			politician.getFile().add(file);
			Integer year = (int) row.getCell(1).getNumericCellValue();
			file.setYear(year);
			String cityName = row.getCell(2).getStringCellValue();
			String countyName = row.getCell(3).getStringCellValue();
			String partyName = row.getCell(4).getStringCellValue();
			Party party = new Party(partyName);
			if (!parties.contains(party)) {
				parties.add(party);
			}
			file.setParty(party);
			String[] offencesNames = row.getCell(5).getStringCellValue().split(";");
			Arrays.stream(offencesNames).forEach(offenceName -> {
				Offence offence = new Offence(offenceName);
				if (!offences.contains(offence)) {
					offences.add(offence);
				}
				file.getOffences().add(offence);
			});

			Optional<City> findFirst = cities.stream().filter(c -> {
				return (c.getName().equals(cityName) && c.getCountyName().equals(countyName));
			}).findFirst();
			City city = findFirst.get();
			file.setCity(city);
			city.getFiles().add(file);
			files.add(file);
		});

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CorruptionLevel getCorruptionLevel(City city) {
		int offencesCounter = city.getFiles().stream().mapToInt(file -> file.getOffences().size()).sum();
		if (offencesCounter < 3)
			return CorruptionLevel.LOW;
		if (offencesCounter >= 5)
			return CorruptionLevel.HIGH;
		return CorruptionLevel.MEDIUM;

	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<Politician> getPoliticians() {
		return politicians;
	}

	public void setPoliticians(List<Politician> politicians) {
		this.politicians = politicians;
	}

	public List<Offence> getOffences() {
		return offences;
	}

	public void setOffences(List<Offence> offences) {
		this.offences = offences;
	}

	public List<Party> getParties() {
		return parties;
	}

	public void setParties(List<Party> parties) {
		this.parties = parties;
	}

	public List<com.ovi.ic_project.data.File> getFiles() {
		return files;
	}

	public void setFiles(List<com.ovi.ic_project.data.File> files) {
		this.files = files;
	}

}
