package de.lclutz.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

//import de.mklutz.simulator.prozessor.Prozessor;

public class ProzessorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	Prozessor prozessor;

	ProzessorPanel(Prozessor prozessor) {
		this.prozessor = prozessor;
	}

	public void setProzessor(Prozessor prozessor) {
		this.prozessor = prozessor;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		String befehlsZeiger = prozessor.getBefehlsZeiger();
		List<String> register = prozessor.getRegister();

		int x = 10, y = 10;

		for (int i = 0; i < register.size(); i++) {

			String registerName = register.get(i);
			boolean isBefehlszeiger = registerName.equals( "PC" );
			String registerWert = isBefehlszeiger 
					? prozessor.getWert( registerName ).toString() 
					: Long.toBinaryString( prozessor.getWert( registerName ) );
			if ( befehlsZeiger.equals(registerName) ) {
				graphics.setColor(Color.RED);
			} else {
				graphics.setColor(Color.BLACK);
			}
			graphics.drawString(registerName, x, y);
			graphics.drawString(registerWert, x + 100, y);
			y = y + 10;
		}

	};

}
