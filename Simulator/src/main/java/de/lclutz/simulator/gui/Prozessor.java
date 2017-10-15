package de.lclutz.simulator.gui;

import java.util.List;

public interface Prozessor {

	List<String> getRegister();

	Long getWert(String registerName);

	void ladeProgramm(List<Long> befehle);

	Prozessor schrittAusfuehren();

	String getBefehlsZeiger();
}
