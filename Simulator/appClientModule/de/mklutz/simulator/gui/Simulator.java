package de.mklutz.simulator.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import de.mklutz.simulator.prozessor.P1;
import de.mklutz.simulator.prozessor.Programme;
import de.mklutz.simulator.prozessor.Prozessor;

public class Simulator implements Runnable {

	private JFrame frame;
	JPanel panel;
	JSlider warteZeit;
	private boolean laufModus = false;
	long pause = 1;

	Prozessor prozessor = P1.create();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Simulator window = new Simulator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Simulator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton lauf = new JButton("Lauf");
		lauf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laufModus = !laufModus;
				berechneUndZeige();
			}
		});
		lauf.setBounds(412, 45, 89, 23);
		frame.getContentPane().add(lauf);

		JButton schritt = new JButton("Schritt");
		schritt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laufModus = false;
				berechneUndZeige();
			}
		});

		schritt.setBounds(412, 11, 89, 23);
		frame.getContentPane().add(schritt);

		warteZeit = new JSlider();
		warteZeit.setOrientation(SwingConstants.VERTICAL);
		warteZeit.setBounds(448, 121, 46, 87);
		frame.getContentPane().add(warteZeit);

		prozessor.ladeProgramm(Programme.INCREMENT_AKKUMULATOR_IN_SCHLEIFE);
		// panel = new JPanel();
		panel = new ProzessorPanel(prozessor);
		panel.setBounds(22, 11, 381, 239);
		panel.setDoubleBuffered(true);
		frame.getContentPane().add(panel);

		JLabel lblSchnell = new JLabel("schnell");
		lblSchnell.setBounds(455, 218, 46, 14);
		frame.getContentPane().add(lblSchnell);

		JLabel lblLangsam = new JLabel("langsam");
		lblLangsam.setBounds(448, 100, 63, 14);
		frame.getContentPane().add(lblLangsam);

	}

	void berechneUndZeige() {

		prozessor.schrittAusfuehren();
		panel.repaint();

		if ( laufModus ) {
			try {
				Thread.sleep(warteZeit.getValue() * 10);
				EventQueue.invokeLater(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		berechneUndZeige();
	}
}
