package de.lclutz.simulator.prozessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PIC16F84ATest {

	@Test
	public void addwfBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b00_0111_0000_1110L ); // F[14] + W -> W

		p.ladeProgramm( programm );
		p.datenSpeicher.set( 14, 3 );
		p.w = 4;

		p.schritt();
		assertEquals( (Integer) 7, p.w );
		assertEquals( (Integer) 3, p.datenSpeicher.get( 14 ) );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );

		programm = Arrays.asList( 0b00_0111_1000_1110L ); // F[14] + W -> F[14]

		p.ladeProgramm( programm );
		p.datenSpeicher.set( 14, 3 );
		p.w = 4;

		p.schritt();// 4 + 7 = 11 -> Z=0;C=0
		assertEquals( (Integer) 4, p.w );
		assertEquals( (Integer) 7, p.datenSpeicher.get( 14 ) );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT );

		programm = Arrays.asList( 0b00_0111_1000_1110L ); // F[14] + W -> F[14]

		p.ladeProgramm( programm );
		p.datenSpeicher.set( 14, 0 );
		p.w = 0;

		p.schritt(); // 0 + 0 = 0 -> Z=1;C=0
		assertEquals( (Integer) 0, p.w );
		assertEquals( (Integer) 0, p.datenSpeicher.get( 14 ) );
		assertTrue( (p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT) > 0 );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT );

		programm = Arrays.asList( 0b00_0111_1000_1110L ); // F[14] + W -> F[14]

		p.ladeProgramm( programm );
		p.w = 255;
		p.datenSpeicher.set( 14, 5 );

		p.schritt(); // 255 + 5 = 4 mod 256 -> Z=0;C=1
		assertEquals( (Integer) 255, p.w );
		assertEquals( (Integer) 4, p.datenSpeicher.get( 14 ) );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );
		assertTrue( (p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT) > 0 );

	}

	@Test
	public void andwfBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b00_0101_0000_1110L ); // F[14] AND W -> W

		p.ladeProgramm( programm );
		p.datenSpeicher.set( 14, 0b0101 );
		p.w = 0b0011;

		p.schritt();
		assertEquals( (Integer) 0b0101, p.datenSpeicher.get( 14 ) );
		assertEquals( (Integer) 0b0001, p.w );

		programm = Arrays.asList( 0b00_0101_1000_1110L ); // F[14] AND W -> F[14]

		p.ladeProgramm( programm );
		p.datenSpeicher.set( 14, 0b0101 );
		p.w = 0b0011;

		p.schritt();
		assertEquals( (Integer) 0b0001, p.datenSpeicher.get( 14 ) );
		assertEquals( (Integer) 0b0011, p.w );

	}

	@Test
	public void addlwBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b11_1110_0000_1110L ); // 14 -> W

		p.ladeProgramm( programm );
		p.w = 0;
		assertEquals( (Integer) 0, p.w );

		p.schritt(); // 0 + 14 = 14; Z=0,C=0
		assertEquals( (Integer) 14, p.w );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT );

		p.ladeProgramm( programm );
		p.w = 250;
		assertEquals( (Integer) 250, p.w );

		p.schritt(); // 250 + 14 = 8 % 156; Z=0,C=1
		assertEquals( (Integer) 8, p.w );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );
		assertEquals( DatenSpeicher.C_BIT, p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT );

		p.ladeProgramm( Arrays.asList( 0b11_1110_0000_0000L ) );
		p.w = 0;
		assertEquals( (Integer) 0, p.w );

		p.schritt(); // 0 + 0 = 0 % 156; Z=1,C=0
		assertEquals( (Integer) 0, p.w );
		assertEquals( DatenSpeicher.Z_BIT, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT );

	}

	@Test
	public void andlwBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b11_1001_0000_1110L ); // 14 & W -> W

		p.ladeProgramm( programm );
		p.w = 0xFF;
		assertEquals( (Integer) 0xFF, p.w );

		p.schritt(); // 0xFF & 14 = 14; Z=0,C=0
		assertEquals( (Integer) 14, p.w );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.Z_BIT );
		assertEquals( 0, p.datenSpeicher.getStatus() & DatenSpeicher.C_BIT );

	}

	@Test
	public void callBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b10_0000_0000_0101L ); /// call 5

		p.ladeProgramm( programm );
		assertEquals( (Integer) 0, p.pc );
		assertTrue( p.stack.isEmpty() );

		p.schritt();
		assertEquals( (Integer) 5, p.pc );
		assertEquals( (Integer) 1, p.stack.peek() );
	}

	@Test
	public void returnBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b10_0000_0000_0101L, 0L, 0L, 0L, 0L, 0b00_0000_0000_1000L ); /// call 5 -
																											/// return

		p.ladeProgramm( programm );
		assertEquals( (Integer) 0, p.pc );
		assertTrue( p.stack.isEmpty() );

		p.schritt();
		assertEquals( (Integer) 5, p.pc );
		assertEquals( (Integer) 1, p.stack.peek() );

		p.schritt();
		assertEquals( (Integer) 1, p.pc );
		assertTrue( p.stack.isEmpty() );

		p.schritt();
		assertEquals( (Integer) 2, p.pc );
	}

	@Test
	public void gotoBefehl() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList(
				0b10_1000_0000_0010L,
				0b00_0000_0000_0000L,
				0b10_1000_0000_0000L );

		p.ladeProgramm( programm );
		assertEquals( (Integer) 0, p.pc );

		p.schritt();
		assertEquals( (Integer) 2, p.pc );

		p.schritt();
		assertEquals( (Integer) 0, p.pc );
	}

	@Test
	public void movlw() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList(0x3011L);

		p.ladeProgramm(programm);
		assertEquals( (Integer) 0, p.pc );

		p.schritt();

		assertEquals( (Integer) 1, p.pc );
		assertEquals( (Integer) 0x11, p.w );
	}

	@Test
	public void iorlw() {

		PIC16F84A p = PIC16F84A.create();

		List<Long> programm = Arrays.asList( 0b110000_10101010L, 0b111000_00001111L );

		p.ladeProgramm( programm );
		assertEquals( (Integer) 0, p.w );

		p.schritt();
		assertEquals( (Integer) 1, p.pc );

		p.schritt();
		assertEquals( (Integer) 2, p.pc );

		assertEquals( (Integer) 0b10101111, p.w );
	}

}
