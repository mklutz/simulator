package de.mklutz.simulator.prozessor;

import java.util.ArrayList;
import java.util.List;

public class Programme {

	public static List<Long> INCREMENT_AKKUMULATOR = new ArrayList<>();

	public static List<Long> INCREMENT_AKKUMULATOR_IN_SCHLEIFE = new ArrayList<>();

	static {
		INCREMENT_AKKUMULATOR.add(1L); // incAkkumulator

		INCREMENT_AKKUMULATOR_IN_SCHLEIFE.add(1L); // incAkkumulator
		INCREMENT_AKKUMULATOR_IN_SCHLEIFE.add(2L); // GOTO
		INCREMENT_AKKUMULATOR_IN_SCHLEIFE.add(0L); // 0

	}

}
