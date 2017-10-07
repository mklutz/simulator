package de.lclutz.simulator.prozessor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PIC16F84ATest {

	@Test
	public void movlw() {

		Prozessor p = PIC16F84A.create();

		List<Long> programm = Arrays.asList(0x3011L);

		p.ladeProgramm(programm);
		p.schrittAusfuehren();
		assertEquals((Long) 0x11L, p.getWert(PIC16F84A.W));
	}

}
