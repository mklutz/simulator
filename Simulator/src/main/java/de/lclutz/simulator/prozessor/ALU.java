package de.lclutz.simulator.prozessor;


public class ALU {

	Integer wert;
	boolean z;
	boolean c;

	private ALU() {

	};

	public static ALU add(Integer l, Integer r) {

		Integer w = l + r;

		ALU result = new ALU();

		result.wert = w % 256;
		result.z = result.wert == 0;
		result.c = w > 256;

		return result;
	}

	public static ALU and(Integer l, Integer r) {

		Integer w = l & r;

		ALU result = new ALU();

		result.wert = w % 256;
		result.z = result.wert == 0;
		result.c = w > 256;

		return result;
	}

}
