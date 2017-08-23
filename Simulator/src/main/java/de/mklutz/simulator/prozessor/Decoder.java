package de.mklutz.simulator.prozessor;

import java.util.HashMap;
import java.util.Map;

public class Decoder {

	static Map<Long, Befehl> codeDecode = new HashMap<>();

	static {

		codeDecode.put( 1L, Befehl.incAkkumulator );
		codeDecode.put( 2L, Befehl.goTo );
		codeDecode.put( 3L, Befehl.setArgToAkkumulator ); // addiere das Argument zum
		codeDecode.put( 4L, Befehl.addArgToAkkumulator );
		codeDecode.put( 5L, Befehl.addRegToAkkumulator );

	}

	public static Befehl decode(Long code) {

		Befehl befehl = codeDecode.get( code );
		return befehl != null ? befehl : Befehl.noOp;

	}

	public static Befehl decode(Long code, Long parameter) {

		Befehl befehl = codeDecode.get( code );

		befehl = befehl != null ? befehl : Befehl.noOp;

		befehl.setParameter1( parameter );

		return befehl;

	}

	public static int anzahlParameter(Long code) {

		Befehl befehl = codeDecode.get( code );

		befehl = befehl != null ? befehl : Befehl.noOp;

		return befehl.getAnzahlParameter();
	}

}
