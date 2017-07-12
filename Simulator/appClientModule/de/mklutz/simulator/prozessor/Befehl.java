package de.mklutz.simulator.prozessor;

public abstract class Befehl {

	static Befehl noOp = new Befehl() {

		@Override
		void doBefehl(P1 p1) {
			// tue nichts
		}

	};

	static Befehl incAkkumulator = new Befehl() {

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister(P1.AKKUMULATOR, p1.getWert(P1.AKKUMULATOR) + 1);

		}

	};

	static Befehl goTo = new Befehl() {

		Long toValue;

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister(P1.BEFEHLS_ZEIGER, toValue);

		}

		@Override
		int getAnzahlParameter() {
			return 1;
		}

		@Override
		void setParameter1(Long parameter1) {
			toValue = parameter1;
		}
	};

	abstract void doBefehl(P1 p1);

	int getAnzahlParameter() {
		return 0;
	}

	void setParameter1(Long parameter) {

	}

}
