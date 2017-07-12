package de.mklutz.simulator.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	final JFileChooser fc = new JFileChooser();

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
		warteZeit.setBounds(422, 100, 46, 87);
		frame.getContentPane().add(warteZeit);

		prozessor.ladeProgramm(Programme.INCREMENT_AKKUMULATOR_IN_SCHLEIFE);
		// panel = new JPanel();
		panel = new ProzessorPanel(prozessor);
		panel.setBounds(22, 11, 381, 239);
		panel.setDoubleBuffered(true);
		frame.getContentPane().add(panel);

		JLabel lblSchnell = new JLabel("schnell");
		lblSchnell.setBounds(429, 197, 46, 14);
		frame.getContentPane().add(lblSchnell);

		JLabel lblLangsam = new JLabel("langsam");
		lblLangsam.setBounds(422, 79, 63, 14);
		frame.getContentPane().add(lblLangsam);

		JButton ladenButton = new JButton("Laden");
		ladenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(frame);

				if ( returnVal == JFileChooser.APPROVE_OPTION ) {
					File file = fc.getSelectedFile();
					if ( file.isFile() ) {
						try {

							List<Long> programm = new ArrayList<>();
							Stream<String> lines = Files.lines(file.toPath());
							lines.forEach(l -> programm.add(Long.parseLong(l)));
							lines.close();

							prozessor.ladeProgramm(programm);
							berechneUndZeige();

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		ladenButton.setBounds(412, 222, 89, 23);
		frame.getContentPane().add(ladenButton);

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
