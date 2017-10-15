package de.lclutz.simulator.prozessor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DatenSpeicherTest {

	@Test
	public void setRegister0() {

		DatenSpeicher datenSpeicher = new DatenSpeicher();

		int indirekteAdresse = 10;
		Integer setReg0Wert = 4711;
		datenSpeicher.set( indirekteAdresse, setReg0Wert ); // datenSpeicher[10] = 4711
		datenSpeicher.set( DatenSpeicher.FSR, indirekteAdresse ); // datenSpeicher[FSR] = 10

		assertEquals( "datenSpeicher[0] = datenSpeicher[datenSpeicher[FSR]]", setReg0Wert, datenSpeicher.get( 0 ) );
	}

	@Test
	public void getRegister0() {

		DatenSpeicher datenSpeicher = new DatenSpeicher();

		int indirekteAdresse = 10;
		Integer getReg0Wert = 2017;

		datenSpeicher.set( DatenSpeicher.FSR, indirekteAdresse ); // datenSpeicher[FSR] = 10
		datenSpeicher.set( 0, getReg0Wert ); // datenSpeicher[0] = 2017

		assertEquals( "datenSpeicher[0] = datenSpeicher[datenSpeicher[FSR]]", getReg0Wert,
				datenSpeicher.get( indirekteAdresse ) );

	}

	@Test
	public void setEeprom() {

		DatenSpeicher datenSpeicher = new DatenSpeicher();

		Integer setWert1 = 4711;
		Integer setWert2 = 2017;
		int adresse1 = 12;
		int adresse2 = 13;

		datenSpeicher.set( DatenSpeicher.EEADR, adresse1 ); // eeAdresse = adresse1
		datenSpeicher.set( DatenSpeicher.EEDATA, setWert1 ); // eeData[ eeAdresse ] = wert1

		datenSpeicher.set( DatenSpeicher.EEADR, adresse2 ); // eeAdresse = adresse2
		datenSpeicher.set( DatenSpeicher.EEDATA, setWert2 ); // eeData[ eeAdresse ] = wert2

		datenSpeicher.set( DatenSpeicher.EEADR, adresse1 ); // eeAdresse = adresse1
		assertEquals( "eeData[ eeAdresse1 ] = wert1", setWert1, datenSpeicher.get( DatenSpeicher.EEDATA ) );

		datenSpeicher.set( DatenSpeicher.EEADR, adresse2 ); // eeAdresse = adresse2
		assertEquals( "eeData[ eeAdresse1 ] = wert1", setWert2, datenSpeicher.get( DatenSpeicher.EEDATA ) );
	}

}
