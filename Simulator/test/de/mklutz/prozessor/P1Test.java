package de.mklutz.prozessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.mklutz.simulator.prozessor.P1;
import de.mklutz.simulator.prozessor.Prozessor;

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

}
