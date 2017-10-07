package de.mklutz.simulator.prozessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P1 implements Prozessor {

	static String AKKUMULATOR = "akkumulator";
	static String BEFEHLS_ZEIGER = "befehlsZeiger";
	static int anzahlRegister = 20;
	List<String> registerNamen = new ArrayList<>();
	Map<String, Long> register = new HashMap<>();

	P1() {
	};

	public static Prozessor create() {

		P1 p1 = new P1();
		p1.registerNamen.add(AKKUMULATOR);
		p1.registerNamen.add(BEFEHLS_ZEIGER);

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

	@Override
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
		setTo(BEFEHLS_ZEIGER, 0L);
	}

	@Override
	public Prozessor schrittAusfuehren() {

		Long befehlsZeiger = getFrom(BEFEHLS_ZEIGER);
		Long befehl = getFrom(befehlsZeiger);
		setTo(BEFEHLS_ZEIGER, ++befehlsZeiger);

		int anzahlParameter = Decoder.anzahlParameter(befehl);
		Befehl maschinenBefehl = Befehl.noOp;

		if ( anzahlParameter == 0 ) {

			maschinenBefehl = Decoder.decode(befehl);
		} else if ( anzahlParameter == 1 ) {

			Long parameter = getFrom(befehlsZeiger);
			setTo(BEFEHLS_ZEIGER, ++befehlsZeiger);

			maschinenBefehl = Decoder.decode(befehl, parameter);
		}

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
		return "r" + getFrom(BEFEHLS_ZEIGER);
	}

}
