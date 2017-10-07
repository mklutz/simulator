package de.lclutz.simulator.prozessor;

import java.util.HashMap;
import java.util.Map;

public class Decoder {

	static final Map<String, Befehl> codeDecode = new HashMap<>();

	static {

		codeDecode.put("MOVLW", Befehl.MOVLW);
	}

	public static Befehl decode(Long code) {

		Befehl befehl = codeDecode.get( toBefehl( code ) );

		if ( befehl != null ) befehl.setParameter( code );

		return befehl != null ? befehl : Befehl.NOP;

	}

	private static String toBefehl(Long code) {

		Long result = code & 0x3C00;
		if ( result == 0x3000 ) {
			return "MOVLW";
		}

		return "";
	}

	public static Befehl decode(Long code, Long parameter) {

		Befehl befehl = codeDecode.get( code );

		befehl = befehl != null ? befehl : Befehl.NOP;

		befehl.setParameter( parameter );

		return befehl;

	}

}
