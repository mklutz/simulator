package de.mklutz.simulator.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import de.mklutz.simulator.prozessor.P1;
import de.mklutz.simulator.prozessor.Programme;
import de.mklutz.simulator.prozessor.Prozessor;

public class Simulator implements Runnable {

	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor( 2 );
	private JFrame frame;
	JPanel panel;
	JSlider warteZeit;
	private boolean laufModus = false;
	long pause = 1;
	final JFileChooser fc = new JFileChooser();
	JTextArea textArea;

	Prozessor prozessor = P1.create();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater( new Runnable() {
			@Override
			public void run() {
				try {
					Simulator window = new Simulator();
					window.frame.setVisible( true );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} );
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
		frame.setBounds( 100, 100, 527, 300 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout( null );

		JButton lauf = new JButton( "Lauf" );
		lauf.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laufModus = !laufModus;
				berechneUndZeige();
			}
		} );
		lauf.setBounds( 412, 45, 89, 23 );
		frame.getContentPane().add( lauf );

		JButton schritt = new JButton( "Schritt" );
		schritt.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laufModus = false;
				berechneUndZeige();
			}
		} );

		schritt.setBounds( 412, 11, 89, 23 );
		frame.getContentPane().add( schritt );

		warteZeit = new JSlider();
		warteZeit.setMaximum( 10 );
		warteZeit.setOrientation( SwingConstants.VERTICAL );
		warteZeit.setBounds( 422, 100, 46, 91 );
		frame.getContentPane().add( warteZeit );

		prozessor.ladeProgramm( Programme.INCREMENT_AKKUMULATOR_IN_SCHLEIFE );
		// panel = new JPanel();
		panel = new ProzessorPanel( prozessor );
		panel.setBounds( 22, 11, 172, 239 );
		panel.setDoubleBuffered( true );
		frame.getContentPane().add( panel );

		JLabel lblSchnell = new JLabel( "schnell" );
		lblSchnell.setBounds( 422, 202, 46, 14 );
		frame.getContentPane().add( lblSchnell );

		JLabel lblLangsam = new JLabel( "langsam" );
		lblLangsam.setBounds( 422, 79, 63, 14 );
		frame.getContentPane().add( lblLangsam );

		JButton ladenButton = new JButton( "Datei" );
		ladenButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog( frame );

				if ( returnVal == JFileChooser.APPROVE_OPTION ) {
					// lade Datei in textArea
					File file = fc.getSelectedFile();
					if ( file.isFile() ) {
						try (Stream<String> lines = Files.lines( file.toPath() )) {

							textArea.setText( lines.collect( Collectors.joining( "\n" ) ) );

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} );
		ladenButton.setBounds( 412, 227, 89, 23 );
		frame.getContentPane().add( ladenButton );

		JButton btnProgramm = new JButton( "<-" );
		btnProgramm.setIcon( new ImageIcon(
				Simulator.class.getResource( "/javax/swing/plaf/metal/icons/ocean/iconify-pressed.gif" ) ) );
		btnProgramm.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// lade Text aus testArea in Prozessor
				ladeProzessor( textArea.getText() );
			}
		} );
		btnProgramm.setBounds( 199, 114, 29, 23 );
		frame.getContentPane().add( btnProgramm );

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds( 237, 11, 165, 239 );
		frame.getContentPane().add( scrollPane );

		textArea = new JTextArea();
		scrollPane.setViewportView( textArea );

	}

	void ladeProzessor(String programmText) {

		ladeProzessor( Arrays.asList( programmText.split( "\n" ) ) );
	}

	void ladeProzessor(List<String> programmText) {
		List<Long> programm = new ArrayList<>();
		// entferne kommentar/leerzeichen und fuelle programm
		programmText.stream().map( l -> entferneKommentar( l ) ).filter( s -> s.length() > 0 )
				.forEach( l -> programm.add( Long.parseLong( l ) ) );

		try {

			prozessor.ladeProgramm( programm );

		} catch (Throwable t) {
			JOptionPane.showMessageDialog( null, "Problem beim laden des Programms", "Fehler",
					JOptionPane.ERROR_MESSAGE );
		}
		panel.repaint();
	}

	static String entferneKommentar(String zeile) {
		String[] split = zeile.split( "#" );
		return split[0].trim();
	}

	void berechneUndZeige() {

		prozessor.schrittAusfuehren();
		panel.repaint();

		if ( laufModus ) {
			executor.schedule( invokeLaterInThread, warteZeit.getValue() * 100, TimeUnit.MILLISECONDS );
		}
	}

	Callable<Boolean> invokeLaterInThread = new Callable<Boolean>() {

		@Override
		public Boolean call() throws Exception {
			EventQueue.invokeLater( Simulator.this );
			return true;
		}

	};

	@Override
	public void run() {
		berechneUndZeige();
	}
}
