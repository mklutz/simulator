package de.mklutz.simulator.prozessor;

import java.util.List;

public interface Prozessor {

	List<String> getRegister();

	Long getWert(String registerName);

	void setRegister(String nane, Long wert);

	void ladeProgramm(List<Long> befehle);

	Prozessor schrittAusfuehren();
}
