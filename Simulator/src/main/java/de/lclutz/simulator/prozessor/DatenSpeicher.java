package de.lclutz.simulator.prozessor;


class DatenSpeicher {

	static final int STATUS_ADRESSE = 3;
	static final int C_BIT = 0b00000001;
	static final int DC_BIT = 0b00000010;
	static final int Z_BIT = 0b00000100;
	static final int FSR = 4;
	static final int EEDATA = 8;
	static final int EEADR = 9;

	static final int MAX_SPEICHER_REGISTER = 68;
	Integer[] speicher = new Integer[MAX_SPEICHER_REGISTER];
	static final int MAX_EEPROM_SPEICHER = 64;
	Integer[] eepromSpeicher = new Integer[MAX_EEPROM_SPEICHER];

	DatenSpeicher() {

		for (int i = 0; i < MAX_SPEICHER_REGISTER; i++) {
			speicher[i] = 0;
		}
		for (int i = 0; i < MAX_EEPROM_SPEICHER; i++) {
			eepromSpeicher[i] = 0;
		}
	}

	Integer get(int i) {

		int adresse = i % MAX_SPEICHER_REGISTER;

		if ( adresse == 0 ) {
			adresse = speicher[FSR];
		}

		if ( adresse == EEDATA ) {
			return getEeprom( speicher[EEADR] );
		}

		return speicher[adresse];
	}

	void set(int i, Integer wert) {

		int adresse = i % MAX_SPEICHER_REGISTER;

		if ( adresse == 0 ) {
			adresse = speicher[FSR];
		}

		if ( adresse == EEDATA ) {
			setEeprom( speicher[EEADR], wert );
		} else {
			speicher[adresse] = wert;
		}
	}

	private Integer getEeprom(int i) {

		int adresse = i % MAX_EEPROM_SPEICHER;

		return eepromSpeicher[adresse];
	}

	private void setEeprom(int i, Integer wert) {

		int adresse = i % MAX_EEPROM_SPEICHER;

		eepromSpeicher[adresse] = wert;
	}

	void setStatus(Byte status) {

		speicher[STATUS_ADRESSE] = status.intValue();
	}

	void setStatus(Integer status) {

		speicher[STATUS_ADRESSE] = status.intValue();
	}

	Byte getStatus() {

		return speicher[STATUS_ADRESSE].byteValue();
	}

	void setZ(boolean b) {

		if ( b ) {
			setStatus( setBit( getStatus(), Z_BIT ) );
		} else {
			setStatus( unsetBit( getStatus(), Z_BIT ) );
		}
	}

	void setC(boolean b) {

		if ( b ) {
			setStatus( setBit( getStatus(), C_BIT ) );
		} else {
			setStatus( unsetBit( getStatus(), C_BIT ) );
		}
	}

	int setBit(byte value, int b) {

		return value | b;
	}

	int unsetBit(byte value, int b) {

		return value & ~b;
	}

}
