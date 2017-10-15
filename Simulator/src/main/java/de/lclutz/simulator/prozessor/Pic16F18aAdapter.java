package de.lclutz.simulator.prozessor;

import java.util.ArrayList;
import java.util.List;

import de.lclutz.simulator.gui.Prozessor;

public class Pic16F18aAdapter extends PIC16F84A implements Prozessor {

	static String PC = "PC";
	static String W = "W";
	static String F = "F";
	static String P = "P";
	static String STATUS = "F3";

	Pic16F18aAdapter() {};

	public static Pic16F18aAdapter create() {

		return new Pic16F18aAdapter();
	}

	@Override
	public List<String> getRegister() {

		List<String> register = new ArrayList<String>();

		register.add( PC );
		register.add( W );
		for (int i = 0; i < 20; i++) {
			register.add( "P" + i );
		}

		for (int i = 0; i < 20; i++) {
			register.add( F + i );
		}

		return register;
	}

	@Override
	public Long getWert(String registerName) {

		if ( registerName.equals( PC ) ) {
			return pc.longValue();
		}

		if ( registerName.equals( W ) ) {
			return w.longValue();
		}

		if ( registerName.startsWith( P ) ) {
			String adresse = registerName.substring( 1, registerName.length() );
			return programmSpeicher.get( Integer.parseInt( adresse ) );
		}

		if ( registerName.startsWith( F ) ) {
			String adresse = registerName.substring( 1, registerName.length() );
			return datenSpeicher.get( Integer.parseInt( adresse ) ).longValue();
		}

		return null;
	}

	void setWert(String registerName, Long wert) {

		if ( registerName.equals( PC ) ) {
			pc = wert.intValue();
		}

		if ( registerName.equals( W ) ) {
			w = wert.intValue();
		}

		if ( registerName.startsWith( P ) ) {
			String adresse = registerName.substring( 1, registerName.length() );
			programmSpeicher.set( Integer.parseInt( adresse ), wert );
		}

		if ( registerName.startsWith( F ) ) {
			String adresse = registerName.substring( 1, registerName.length() );
			datenSpeicher.set( Integer.parseInt( adresse ), wert.intValue() );
		}

	}

	@Override
	public void ladeProgramm(List<Long> befehle) {

		super.ladeProgramm( befehle );
	}

	@Override
	public Prozessor schrittAusfuehren() {

		super.schritt();

		return this;
	}

	@Override
	public String getBefehlsZeiger() {

		return "P" + pc.toString();
	}

}
