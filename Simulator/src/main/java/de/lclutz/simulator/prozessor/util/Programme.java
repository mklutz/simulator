package de.lclutz.simulator.prozessor.util;

import java.util.Arrays;
import java.util.List;

public class Programme {

	public static List<Long> SCHLEIFE;
	public static List<Long> ZWEI_PLUS_DREI;

	static {

		SCHLEIFE = Arrays.asList( 
				0b10_1000_0000_0010L, // GOTO 2 
				0b00_0000_0000_0000L,
				0b10_1000_0000_0000L ); // GOTO 0

		ZWEI_PLUS_DREI = Arrays.asList( 
				0b11_1110_0000_0010L, // w + 2 -> w 
				0b11_1110_0000_0011L // w + 3 -> w
				); 
	}

}
