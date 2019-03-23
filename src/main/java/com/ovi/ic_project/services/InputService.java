package com.ovi.ic_project.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ovi.ic_project.data.County;
import com.ovi.ic_project.data.CorruptionLevel;
import com.ovi.ic_project.data.Offence;
import com.ovi.ic_project.data.Party;
import com.ovi.ic_project.data.Politician;
import com.ovi.ic_project.data.ScoreLevel;
import com.ovi.ic_project.utils.CONSTANTS;

public class InputService {
	private static InputService instance;
	private List<County> counties = new ArrayList<County>();
	private List<Politician> politicians = new ArrayList<Politician>();
	private List<Offence> offences = new ArrayList<Offence>();
	private List<Party> parties = new ArrayList<Party>();
	private List<com.ovi.ic_project.data.File> files = new ArrayList<com.ovi.ic_project.data.File>();
	private HashMap<County, Long> totalScores;

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
			workbook = WorkbookFactory.create(new File("Counties.xls"));
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheetAt(0);

		sheet.forEach(row -> {
			String cityName = row.getCell(0).getStringCellValue();
			String countyName = row.getCell(1).getStringCellValue();
			Double latitude = row.getCell(2).getNumericCellValue();
			Double longitude = row.getCell(3).getNumericCellValue();
			Integer score = (int) row.getCell(4).getNumericCellValue();
			County county = new County(cityName, countyName, longitude, latitude, score);
			getCities().add(county);
			System.out.println(county);
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

			Optional<County> findFirst = counties.stream().filter(c -> {
				return (c.getCountyName().equals(countyName));
			}).findFirst();
			County city = null;
			try {
				city = findFirst.get();
				file.setCounty(city);
				file.setCityName(cityName);
				city.getFiles().add(file);
				files.add(file);
			} catch (NoSuchElementException e) {
				System.err.println(countyName);
			}
		});

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CorruptionLevel getCorruptionLevel(County city) {
		int offencesCounter = city.getFiles().stream().mapToInt(file -> file.getOffences().size()).sum();
		if (offencesCounter == CONSTANTS.ZERO_CORRUPTION_LEVEL) {
			return CorruptionLevel.ZERO;
		}
		if (offencesCounter < CONSTANTS.MEDIUM_CORRUPTION_LEVEL)
			return CorruptionLevel.LOW;
		if (offencesCounter >= CONSTANTS.HIGH_CORRUPTION_LEVEL)
			return CorruptionLevel.HIGH;
		return CorruptionLevel.MEDIUM;
	}

	public ScoreLevel getScoreLevel(County city) {
		int score = city.getScore();
		if (score > CONSTANTS.BEST_SCORE) {
			return ScoreLevel.BEST;
		}
		if (score > CONSTANTS.HIGH_SCORE)
			return ScoreLevel.HIGH;
		if (score > CONSTANTS.MEDIUM_SCORE)
			return ScoreLevel.MEDIUM;
		return ScoreLevel.LOW;
	}

	public List<County> getCities() {
		return counties;
	}

	public void setCities(List<County> cities) {
		this.counties = cities;
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

	public void calculateTotalScores() {
		setTotalScores(new HashMap<County, Long>());
		counties.forEach(c -> {
			Long totalScoreForCounty = getTotalScoreForCounty(c);
			getTotalScores().put(c, totalScoreForCounty);
			System.err.println(c.getCountyName() + " : " + totalScoreForCounty);
		});
	}

	private Long getTotalScoreForCounty(County c) {
		int noOfOffences = c.getNoOfOffences();
		return Math.round(c.getScore() * 1.0 / (noOfOffences + 1));
	}

	public HashMap<County, Long> getTotalScores() {
		return totalScores;
	}

	public void setTotalScores(HashMap<County, Long> totalScores) {
		this.totalScores = totalScores;
	}

	public void writeToExcelStat() {
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Statistics");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		String columns[] = { "County", "Numar infractiuni", "Investitii", "Raport" };

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for (County county : counties) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(county.getCountyName());

			row.createCell(1).setCellValue(county.getNoOfOffences());

			row.createCell(2).setCellValue(county.getScore());

			row.createCell(3).setCellValue(getTotalScoreForCounty(county));
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("Statistics.xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.write(fileOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
