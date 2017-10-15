package de.lclutz.simulator.prozessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Befehl {

	public static Befehl getOperation(Long code) {

		String binaryString = getBinary( code );

		Befehl result = NOP;

		if ( binaryString.matches( "^000111.*" ) ) result = ADDWF;
		if ( binaryString.matches( "^000101.*" ) ) result = ANDWF;
		if ( binaryString.matches( "^11111.*" ) ) result = ADDLW;
		if ( binaryString.matches( "^111001.*" ) ) result = ANDLW;
		if ( binaryString.matches( "^100.*" ) ) result = CALL;
		if ( binaryString.matches( "^101.*" ) ) result = GOTO;
		if ( binaryString.matches( "^1100.*" ) ) result = MOVLW;
		if ( binaryString.matches( "^111000.*" ) ) result = IORLW;
		if ( binaryString.matches( "^00000000001000" ) ) result = RETURN;

		result.setParameter( code );

		return result;
	}

	static Befehl ADDWF = new Befehl() {

		Integer d;
		Integer f;

		@Override
		void doBefehl(PIC16F84A p1) {

			ALU alu = ALU.add( p1.w, p1.datenSpeicher.get( f ) );

			if ( d == 0 ) {
				p1.w = alu.wert;
			} else {
				p1.datenSpeicher.set( f, alu.wert );
			}

			p1.datenSpeicher.setZ( alu.z );
			p1.datenSpeicher.setC( alu.c );
		}

		@Override
		void setParameter(Long code) {

			d = getParameter( code, "^000111(.).*" );
			f = getParameter( code, "^000111.(.*)" );
		}
	};

	static Befehl ANDWF = new Befehl() {

		Integer d;
		Integer f;

		@Override
		void doBefehl(PIC16F84A p1) {

			ALU alu = ALU.and( p1.w, p1.datenSpeicher.get( f ) );

			if ( d == 0 ) {
				p1.w = alu.wert;
			} else {
				p1.datenSpeicher.set( f, alu.wert );
			}

			p1.datenSpeicher.setZ( alu.z );
		}

		@Override
		void setParameter(Long code) {

			d = getParameter( code, "^000101(.).*" );
			f = getParameter( code, "^000101.(.*)" );
		}
	};

	/**
	 * tue nichts
	 */
	static Befehl NOP = new Befehl() {

		@Override
		void doBefehl(PIC16F84A p1) {

		}

	};

	static Befehl ADDLW = new Befehl() {

		Integer k;

		@Override
		void doBefehl(PIC16F84A p1) {

			ALU alu = ALU.add( p1.w, k );

			p1.w = alu.wert;

			p1.datenSpeicher.setZ( alu.z );
			p1.datenSpeicher.setC( alu.c );
		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^11111.(.*)" );
		}
	};

	static Befehl ANDLW = new Befehl() {

		Integer k;

		@Override
		void doBefehl(PIC16F84A p1) {

			ALU alu = ALU.and( p1.w, k );

			p1.w = alu.wert;

			p1.datenSpeicher.setC( alu.c );
		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^111001(.*)" );
		}
	};

	static Befehl CALL = new Befehl() {

		Integer k;

		@Override
		void doBefehl(PIC16F84A p1) {

			p1.stack.push( p1.pc );
			p1.pc = k;

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^100(.*)" );
		}
	};

	static Befehl GOTO = new Befehl() {

		Integer k;

		@Override
		void doBefehl(PIC16F84A p1) {

			p1.pc = k;

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^101(.*)" );
		}
	};

	static Befehl IORLW = new Befehl() {

		Integer k;

		@Override
		void doBefehl(PIC16F84A p1) {

			Integer wert = p1.w;
			p1.w = k | wert;

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^111000(.*)" );
		}
	};

	static Befehl MOVLW = new Befehl() {

		Integer k;

		@Override
		void doBefehl(PIC16F84A p1) {

			p1.w = k;

		}

		@Override
		void setParameter(Long code) {

			k = getParameter( code, "^1100..(.*)" );
		}
	};

	static Befehl RETURN = new Befehl() {

		@Override
		void doBefehl(PIC16F84A p1) {

			p1.pc = p1.stack.pop();
		}
	};

	private static Integer getParameter(Long code, String pattern) {

		String binaryString = getBinary( code );

		Matcher m = Pattern.compile( pattern ).matcher( binaryString );
		m.matches();

		String group = m.group( 1 );

		return Integer.parseInt( group, 2 );
	}


	abstract void doBefehl(PIC16F84A p1);

	void setParameter(Long parameter) {

	}

	static String getBinary(Long code) {

		String binaryString = "00000000000000" + Long.toBinaryString( code );
		return binaryString.substring( binaryString.length() - 14, binaryString.length() );
	}

}
