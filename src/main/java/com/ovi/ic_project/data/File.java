package com.ovi.ic_project.data;

import java.util.ArrayList;
import java.util.List;

public class File {
	private Politician politician;
	private Party party;
	private Integer year;
	private List<Offence> offences = new ArrayList<Offence>();
	private City city;

	public File(Politician politician, Party party, Integer year) {
		super();
		this.politician = politician;
		this.party = party;
		this.year = year;
	}

	public File() {
	}

	public Politician getPolitician() {
		return politician;
	}

	public void setPolitician(Politician politician) {
		this.politician = politician;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Offence> getOffences() {
		return offences;
	}

	public void setOffences(List<Offence> offences) {
		this.offences = offences;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
