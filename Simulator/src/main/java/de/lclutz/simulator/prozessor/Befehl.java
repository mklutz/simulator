package de.lclutz.simulator.prozessor;

public abstract class Befehl {

	/**
	 * tue nichts
	 */
	static Befehl NOP = new Befehl() {

		@Override
		void doBefehl(PIC16F84A p1) {
			// tue nichts
		}

	};

	/**
	 * erhoehe den Wert des Akkumulators um 1
	 */
	static Befehl MOVLW = new Befehl() {

		Long k;

		@Override
		void doBefehl(PIC16F84A p1) {
			p1.setRegister(PIC16F84A.W, k);

		}

		@Override
		void setParameter(Long code) {

			k = code & 0xFF;
		}
	};


	abstract void doBefehl(PIC16F84A p1);

	void setParameter(Long parameter) {

	}

}
