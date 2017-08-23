package de.mklutz.simulator.prozessor;

public abstract class Befehl {

	/**
	 * tue nichts
	 */
	static Befehl noOp = new Befehl() {

		@Override
		void doBefehl(P1 p1) {
			// tue nichts
		}

	};

	/**
	 * erhoehe den Wert des Akkumulators um 1
	 */
	static Befehl incAkkumulator = new Befehl() {

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister( P1.AKKUMULATOR, p1.getWert( P1.AKKUMULATOR ) + 1 );

		}

	};

	/**
	 * setze den Befehlszeiger auf den Wert des Arguments
	 */
	static Befehl goTo = new Befehl() {

		Long toValue;

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister( P1.BEFEHLS_ZEIGER, toValue );

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

	/**
	 * addiere das Argument zum Akkumulator
	 */
	static Befehl addArgToAkkumulator = new Befehl() {

		Long addValue;

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister( P1.AKKUMULATOR, p1.getWert( P1.AKKUMULATOR ) + addValue );

		}

		@Override
		int getAnzahlParameter() {
			return 1;
		}

		@Override
		void setParameter1(Long parameter1) {
			addValue = parameter1;
		}
	};

	/**
	 * addiere den Inhalt des Registers zum Akkumulator
	 */
	static Befehl addRegToAkkumulator = new Befehl() {

		Long regValue;

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister( P1.AKKUMULATOR, p1.getWert( P1.AKKUMULATOR ) + p1.getWert( "r" + regValue ) );
		}

		@Override
		int getAnzahlParameter() {
			return 1;
		}

		@Override
		void setParameter1(Long parameter1) {
			regValue = parameter1;
		}
	};

	/**
	 * setze das Argument in den Akkumulator
	 */
	static Befehl setArgToAkkumulator = new Befehl() {

		Long setValue;

		@Override
		void doBefehl(P1 p1) {
			p1.setRegister( P1.AKKUMULATOR, setValue );

		}

		@Override
		int getAnzahlParameter() {
			return 1;
		}

		@Override
		void setParameter1(Long parameter1) {
			setValue = parameter1;
		}
	};

	abstract void doBefehl(P1 p1);

	int getAnzahlParameter() {
		return 0;
	}

	void setParameter1(Long parameter) {

	}

}
