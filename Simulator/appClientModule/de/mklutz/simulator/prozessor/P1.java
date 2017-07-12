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
	}

	@Override
	public Prozessor schrittAusfuehren() {

		Long befehlsZeiger = getFrom(BEFEHLS_ZEIGER);
		Long befehl = getFrom(befehlsZeiger);
		setTo(BEFEHLS_ZEIGER, ++befehlsZeiger);

		int anzahlParameter = Decoder.anzahlParameter(befehl);
		Befehl maschienenBefehl = Befehl.noOp;

		if ( anzahlParameter == 0 ) {

			maschienenBefehl = Decoder.decode(befehl);
		} else if ( anzahlParameter == 1 ) {

			Long parameter = getFrom(befehlsZeiger);
			setTo(BEFEHLS_ZEIGER, ++befehlsZeiger);

			maschienenBefehl = Decoder.decode(befehl, parameter);
		}

		maschienenBefehl.doBefehl(this);

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

}
