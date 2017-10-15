package de.lclutz.simulator.prozessor;


class ProgrammSpeicher {

	static final int MAX_PROGRAMM_REGISTER = 1024;
	Long[] programmSpeicher = new Long[MAX_PROGRAMM_REGISTER];

	ProgrammSpeicher() {

		for (int i = 0; i < MAX_PROGRAMM_REGISTER; i++) {
			programmSpeicher[i] = 0L;
		}
	}

	Long get(int i) {

		int adresse = i % MAX_PROGRAMM_REGISTER;
		return programmSpeicher[adresse];
	}

	void set(int i, Long wert) {

		int adresse = i % MAX_PROGRAMM_REGISTER;
		programmSpeicher[adresse] = wert;
	}
}
