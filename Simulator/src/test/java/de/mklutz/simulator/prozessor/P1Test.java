package de.mklutz.simulator.prozessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.mklutz.simulator.prozessor.util.Programme;

public class P1Test {

	@Test
	public void testRegister() {

		Prozessor prozessor = P1.create();

		assertTrue(prozessor.getRegister().contains("akkumulator"));
		assertTrue(prozessor.getRegister().contains("befehlsZeiger"));
		assertTrue(prozessor.getRegister().contains("r0"));
		assertTrue(prozessor.getRegister().contains("r1"));
	}

	@Test
	public void ladeProgramm() {

		Prozessor prozessor = P1.create();

		List<Long> programmCode = new ArrayList<>();
		programmCode.add(1L);
		programmCode.add(4L);
		programmCode.add(8L);

		prozessor.ladeProgramm(programmCode);

		assertEquals(1L, prozessor.getWert("r0").longValue());
		assertEquals(4L, prozessor.getWert("r1").longValue());
		assertEquals(8L, prozessor.getWert("r2").longValue());
	}

	@Test
	public void testIncAkku() {

		Prozessor prozessor = P1.create();

		prozessor.ladeProgramm(Programme.INCREMENT_AKKUMULATOR);

		assertEquals(0L, prozessor.getWert(P1.AKKUMULATOR).longValue());
		assertEquals(0L, prozessor.getWert(P1.BEFEHLS_ZEIGER).longValue());
		assertEquals(1L, prozessor.getWert("r0").longValue());

		prozessor.schrittAusfuehren();

		assertEquals(1L, prozessor.getWert(P1.AKKUMULATOR).longValue());
		assertEquals(1L, prozessor.getWert(P1.BEFEHLS_ZEIGER).longValue());
		assertEquals(1L, prozessor.getWert("r0").longValue());

		prozessor.schrittAusfuehren();
	}

	@Test
	public void schleife() {

		Prozessor prozessor = P1.create();

		prozessor.ladeProgramm(Programme.INCREMENT_AKKUMULATOR_IN_SCHLEIFE);

		assertEquals(0L, prozessor.getWert(P1.AKKUMULATOR).longValue());

		prozessor.schrittAusfuehren();
		assertEquals(1L, prozessor.getWert(P1.AKKUMULATOR).longValue());

		prozessor.schrittAusfuehren();
		prozessor.schrittAusfuehren();
		assertEquals(2L, prozessor.getWert(P1.AKKUMULATOR).longValue());
	}

	@Test
	public void neueBefehle() {

		List<Long> programm = Arrays.asList( 1L, 2L, 5L, 0L, 0L, 2L, 1L );
		Prozessor prozessor = P1.create();

		prozessor.ladeProgramm( programm );

		prozessor.schrittAusfuehren();

		prozessor.schrittAusfuehren();

		prozessor.schrittAusfuehren();

		prozessor.schrittAusfuehren();

		prozessor.schrittAusfuehren();

	}

}
