package de.lclutz.simulator.prozessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Befehl {

	public enum Operationen {
		NOP, IORLW, MOVLW
	}

	public static Befehl getOperation(Long code) {

		String binaryString = Long.toBinaryString( code );

		Befehl result = NOP;

		if ( binaryString.matches( "^101.*" ) ) result = GOTO;
		if ( binaryString.matches( "^1100.*" ) ) result = MOVLW;
		if ( binaryString.matches( "^111000.*" ) ) result = IORLW;

		result.setParameter( code );

		return result;
	}

	/**
	 * tue nichts
	 */
	static Befehl NOP = new Befehl() {

		@Override
		void doBefehl(PIC16F84A p1) {
			// tue nichts
		}

	};

	static Befehl GOTO = new Befehl() {

		Long k;

		@Override
		void doBefehl(PIC16F84A p1) {

			p1.setRegister( PIC16F84A.PC, k );

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^101(.*)" );
		}
	};

	static Befehl IORLW = new Befehl() {

		Long k;

		@Override
		void doBefehl(PIC16F84A p1) {

			Long wert = p1.getWert( PIC16F84A.W );
			p1.setRegister( PIC16F84A.W, k | wert );

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^111000(.*)" );
		}
	};

	static Befehl MOVLW = new Befehl() {

		Long k;

		@Override
		void doBefehl(PIC16F84A p1) {
			p1.setRegister(PIC16F84A.W, k);

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^1100..(.*)" );
		}
	};

	private static Long getParameter(Long code, String pattern) {

		String binaryString = Long.toBinaryString( code );

		Matcher m = Pattern.compile( pattern ).matcher( binaryString );
		m.matches();

		String group = m.group( 1 );

		return Long.parseLong( group, 2 );
	}


	abstract void doBefehl(PIC16F84A p1);

	void setParameter(Long parameter) {

	}

}
