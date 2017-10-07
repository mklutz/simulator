package de.lclutz.simulator.prozessor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PIC16F84ATest {

	@Test
	public void gotoBfehl() {

		Prozessor p = PIC16F84A.create();

		List<Long> programm = Arrays.asList(
				0b10_1000_0000_0010L,
				0b00_0000_0000_0000L,
				0b10_1000_0000_0000L );

		p.ladeProgramm( programm );
		assertEquals( (Long) 0L, p.getWert( PIC16F84A.PC ) );

		p.schrittAusfuehren();
		assertEquals( (Long) 2L, p.getWert( PIC16F84A.PC ) );

		p.schrittAusfuehren();
		assertEquals( (Long) 0L, p.getWert( PIC16F84A.PC ) );
	}

	@Test
	public void movlw() {

		Prozessor p = PIC16F84A.create();

		List<Long> programm = Arrays.asList(0x3011L);

		p.ladeProgramm(programm);
		assertEquals( (Long) 0L, p.getWert( PIC16F84A.PC ) );

		p.schrittAusfuehren();

		assertEquals( (Long) 1L, p.getWert( PIC16F84A.PC ) );
		assertEquals((Long) 0x11L, p.getWert(PIC16F84A.W));
	}

	@Test
	public void iorlw() {

		Prozessor p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b110000_10101010L, 0b111000_00001111L );

		p.ladeProgramm( programm );
		assertEquals( (Long) 0L, p.getWert( PIC16F84A.PC ) );

		p.schrittAusfuehren();
		assertEquals( (Long) 1L, p.getWert( PIC16F84A.PC ) );

		p.schrittAusfuehren();
		assertEquals( (Long) 2L, p.getWert( PIC16F84A.PC ) );

		assertEquals( (Long) 0b10101111L, p.getWert( PIC16F84A.W ) );
	}

}
