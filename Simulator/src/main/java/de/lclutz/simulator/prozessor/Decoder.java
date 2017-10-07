package de.lclutz.simulator.prozessor;

public class Decoder {

	public static Befehl decode(Long code) {

		return Befehl.getOperation( code );

	}

}
