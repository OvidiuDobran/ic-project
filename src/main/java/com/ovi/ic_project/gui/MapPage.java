package com.ovi.ic_project.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;

import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.ovi.ic_project.data.City;
import com.ovi.ic_project.data.File;
import com.ovi.ic_project.data.Offence;
import com.ovi.ic_project.services.InputService;
import com.ovi.ic_project.utils.Utils;

public class MapPage extends CustomComposite {

	private InputService inputService;
	private Composite map;
	private Map<City, Button> markers = new HashedMap<City, Button>();
	private Table filesTable;
	private Composite detailsSection;
	private City selectedCity;
	private Text cityText;
	private Text politicianText;
	private Text partyText;
	private Text yearText;
	private Text offencesText;

	public MapPage(Composite parent, int style) {
		super(parent, style);
		inputService = InputService.getInstance();
	}

	@Override
	public void createContent() {
		FormLayout formLayout = new FormLayout();
		setLayout(formLayout);

		map = new Composite(this, SWT.NONE);
		map.setLayout(new FormLayout());
		Image image = new Image(Display.getCurrent(), "map.png");
		map.setBackgroundImage(image);

		FormData mapData = new FormData();
		mapData.top = new FormAttachment(0, 10);
		mapData.left = new FormAttachment(0, 10);
		mapData.height = BackgroundImageManager.getInstance().getBackgroundHeight();
		mapData.width = BackgroundImageManager.getInstance().getBackgroundWidth();
		map.setLayoutData(mapData);

		filesTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		filesTable.setLinesVisible(true);
		filesTable.setHeaderVisible(true);
		String[] titles = { "Oras", "Politician", "An", "Partid" };

		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(filesTable, SWT.CENTER);
			column.setText(titles[loopIndex]);
			column.setWidth(170);
		}

		FormData filesTableData = new FormData();
		filesTableData.left = new FormAttachment(map, 10);
		filesTableData.top = new FormAttachment(0, 10);
		filesTableData.right = new FormAttachment(100, -10);
		filesTableData.height = 370;
		filesTable.setLayoutData(filesTableData);

		detailsSection = new Composite(this, SWT.BORDER);
		detailsSection.setLayout(new FormLayout());
		FormData detailsSectionData = new FormData();
		detailsSectionData.top = new FormAttachment(filesTable, 10);
		detailsSectionData.bottom = new FormAttachment(100, -49);
		detailsSectionData.left = new FormAttachment(map, 10);
		detailsSectionData.right = new FormAttachment(100, -10);
		detailsSection.setLayoutData(detailsSectionData);

		Label cityLabel = new Label(detailsSection, SWT.NONE);
		cityLabel.setText("Oras: ");
		FormData cityLabelData = new FormData();
		cityLabelData.top = new FormAttachment(0, 10);
		cityLabelData.left = new FormAttachment(0, 10);
		cityLabel.setLayoutData(cityLabelData);

		cityText = new Text(detailsSection, SWT.BORDER | SWT.READ_ONLY);
		FormData cityTextData = new FormData();
		cityTextData.top = new FormAttachment(0, 10);
		cityTextData.left = new FormAttachment(cityLabel, 40);
		cityTextData.right = new FormAttachment(100, -10);
		cityText.setLayoutData(cityTextData);

		Label politicianLabel = new Label(detailsSection, SWT.NONE);
		politicianLabel.setText("Politician: ");
		FormData politicianLabelData = new FormData();
		politicianLabelData.top = new FormAttachment(cityLabel, 10);
		politicianLabelData.left = new FormAttachment(0, 10);
		politicianLabel.setLayoutData(politicianLabelData);

		politicianText = new Text(detailsSection, SWT.BORDER | SWT.READ_ONLY);
		FormData politicianTextData = new FormData();
		politicianTextData.top = new FormAttachment(cityLabel, 10);
		politicianTextData.left = new FormAttachment(cityLabel, 40);
		politicianTextData.right = new FormAttachment(100, -10);
		politicianText.setLayoutData(politicianTextData);

		Label partyLabel = new Label(detailsSection, SWT.NONE);
		partyLabel.setText("Partid: ");
		FormData partyLabelData = new FormData();
		partyLabelData.top = new FormAttachment(politicianLabel, 10);
		partyLabelData.left = new FormAttachment(0, 10);
		partyLabel.setLayoutData(partyLabelData);

		partyText = new Text(detailsSection, SWT.BORDER | SWT.READ_ONLY);
		FormData partyTextData = new FormData();
		partyTextData.top = new FormAttachment(politicianLabel, 10);
		partyTextData.left = new FormAttachment(cityLabel, 40);
		partyTextData.right = new FormAttachment(100, -10);
		partyText.setLayoutData(partyTextData);

		Label yearLabel = new Label(detailsSection, SWT.NONE);
		yearLabel.setText("An: ");
		FormData yearLabelData = new FormData();
		yearLabelData.top = new FormAttachment(partyLabel, 10);
		yearLabelData.left = new FormAttachment(0, 10);
		yearLabel.setLayoutData(yearLabelData);

		yearText = new Text(detailsSection, SWT.BORDER | SWT.READ_ONLY);
		FormData yearTextData = new FormData();
		yearTextData.top = new FormAttachment(partyLabel, 10);
		yearTextData.left = new FormAttachment(cityLabel, 40);
		yearTextData.right = new FormAttachment(100, -10);
		yearText.setLayoutData(yearTextData);

		Label offencesLabel = new Label(detailsSection, SWT.NONE);
		offencesLabel.setText("Infractiuni: ");
		FormData offencesLabelData = new FormData();
		offencesLabelData.top = new FormAttachment(yearLabel, 10);
		offencesLabelData.left = new FormAttachment(0, 10);
		offencesLabel.setLayoutData(offencesLabelData);

		offencesText = new Text(detailsSection, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		FormData offencesTextData = new FormData();
		offencesTextData.top = new FormAttachment(yearLabel, 10);
		offencesTextData.left = new FormAttachment(cityLabel, 40);
		offencesTextData.right = new FormAttachment(100, -10);
		offencesTextData.bottom = new FormAttachment(100, -10);
		offencesText.setLayoutData(offencesTextData);

		loadMapWithData(inputService.getCities());
		addEventListeners();
	}

	private void loadTableWithData(City city) {
		filesTable.removeAll();
		city.getFiles().forEach(file -> {
			TableItem item = new TableItem(filesTable, SWT.NONE);
			item.setText(0, city.getName());
			item.setText(1, file.getPolitician().getName());
			item.setText(2, file.getYear() + "");
			item.setText(3, file.getParty().getName());
		});
	}

	private void addEventListeners() {
		markers.forEach((city, button) -> {
			button.addListener(SWT.MouseHover, event -> {
				button.setToolTipText(city.getName() + ", " + city.getCountyName());
			});
			button.addListener(SWT.Selection, event -> {
				InputService.getInstance().getFiles().forEach(file -> {
					if (file.getCity().equals(city)) {
						loadTableWithData(city);
						selectedCity = city;
						System.err.println(file.getPolitician().getName());
					}
				});
			});
		});

		filesTable.addListener(SWT.Selection, event -> {
			File currentFile = selectedCity.getFiles().get(filesTable.getSelectionIndex());
			loadDetailsSectionWithData(currentFile);
		});
	}

	private void loadDetailsSectionWithData(File currentFile) {
		cityText.setText(currentFile.getCity().getName() + ", " + currentFile.getCity().getCountyName());
		politicianText.setText(currentFile.getPolitician().getName());
		partyText.setText(currentFile.getParty().getName());
		yearText.setText(currentFile.getYear().toString());
		String offencesString = currentFile.getOffences().stream().map(offence -> offence.getDescription())
				.collect(Collectors.joining("\n"));
		offencesText.setText(offencesString);
	}

	private void loadMapWithData(List<City> cities) {
		cities.forEach(city -> createButtonForCity(city));
	}

	private Button createButtonForCity(City city) {
		Button button = new Button(map, SWT.NONE);
		button.setText("");
		String imagePath = inputService.getCorruptionLevel(city).getImagePath();
		Image buttonImage = new Image(Display.getCurrent(), imagePath);
		button.setImage(buttonImage);
		FormData labelTextData = new FormData();
		labelTextData.bottom = new FormAttachment(100 - Utils.getVirtualLatitudeOf(city.getLatitude()), 0);
		labelTextData.left = new FormAttachment(Utils.getVirtualLongitudeOf(city.getLongitude()), 0);
		labelTextData.height = 15;
		labelTextData.width = 15;
		button.setLayoutData(labelTextData);
		markers.put(city, button);
		return button;
	}

}
