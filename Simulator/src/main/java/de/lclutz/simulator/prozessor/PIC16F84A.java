package de.lclutz.simulator.prozessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PIC16F84A implements Prozessor {

	static String W = "w";
	static String PC = "befehlsZeiger";
	static int anzahlRegister = 20;
	List<String> registerNamen = new ArrayList<>();
	Map<String, Long> register = new HashMap<>();

	PIC16F84A() {
	};

	public static Prozessor create() {

		PIC16F84A p1 = new PIC16F84A();
		p1.registerNamen.add(W);
		p1.registerNamen.add(PC);

		for (int i = 0; i < anzahlRegister; i++) {

			p1.registerNamen.add("r" + i);
		}

		p1.initWerte();

		return p1;
	}

	private void initWerte() {
		registerNamen.forEach(n -> register.put(n, 0L));
	}

	@Override
	public List<String> getRegister() {
		return Collections.unmodifiableList(registerNamen);
	}

	@Override
	public Long getWert(String registerName) {
		return register.get(registerName);
	}

	// @Override
	public void setRegister(String nane, Long wert) {
		register.put(nane, wert);
	}

	@Override
	public void ladeProgramm(List<Long> befehle) {

		if ( befehle.size() > anzahlRegister )
			throw new IllegalArgumentException();

		for (int i = 0; i < befehle.size(); i++) {

			setTo(i, befehle.get(i));
		}
		setTo(PC, 0L);
	}

	@Override
	public Prozessor schrittAusfuehren() {

		Long befehlsZeiger = getFrom(PC);
		Long befehl = getFrom(befehlsZeiger);
		setTo(PC, ++befehlsZeiger);

		Befehl maschinenBefehl = Decoder.decode(befehl);

		maschinenBefehl.doBefehl(this);

		return this;
	}

	private Long getFrom(long i) {
		return register.get("r" + i);
	}

	private Long getFrom(String registerName) {
		return register.get(registerName);
	}

	private void setTo(long i, Long value) {
		register.put("r" + i, value);
	}

	private void setTo(String registerName, Long value) {
		register.put(registerName, value);
	}

	@Override
	public String getBefehlsZeiger() {
		return "r" + getFrom(PC);
	}

}
