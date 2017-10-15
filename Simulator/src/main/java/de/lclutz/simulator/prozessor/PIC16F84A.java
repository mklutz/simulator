package de.lclutz.simulator.prozessor;

import java.util.List;
import java.util.Stack;

public class PIC16F84A {

	Integer w = 0;
	Integer pc = 0;
	Stack<Integer> stack = new Stack<>();
	ProgrammSpeicher programmSpeicher = new ProgrammSpeicher();
	DatenSpeicher datenSpeicher = new DatenSpeicher();

	PIC16F84A() {

		initWerte();
	};

	public static PIC16F84A create() {

		return new PIC16F84A();
	}

	private void initWerte() {

	}

	public void ladeProgramm(List<Long> befehle) {

		for (int i = 0; i < befehle.size(); i++) {

			programmSpeicher.set( i, befehle.get( i ) );
		}
		pc = 0;
	}

	public PIC16F84A schritt() {

		Long befehl = programmSpeicher.get( pc++ );

		Befehl maschinenBefehl = Befehl.getOperation( befehl );

		maschinenBefehl.doBefehl(this);

		return this;
	}

}
