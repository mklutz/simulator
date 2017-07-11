package de.mklutz.simulator.gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.mklutz.simulator.model.Ball;

public class Simulator implements Runnable {

	private JFrame frame;
	JPanel panel;
	JSlider warteZeit;
	private boolean laufModus = false;
	long pause = 1;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton lauf = new JButton("Lauf");
		lauf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laufModus = true;
				berechneUndZeige();
			}
		});
		lauf.setBounds(309, 11, 89, 23);
		frame.getContentPane().add(lauf);

		JButton schritt = new JButton("Schritt");
		schritt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laufModus = false;
				berechneUndZeige();
			}
		});

		schritt.setBounds(210, 11, 89, 23);
		frame.getContentPane().add(schritt);

		warteZeit = new JSlider();
		warteZeit.setBounds(33, 11, 167, 26);
		frame.getContentPane().add(warteZeit);

		panel = new JPanel();
		panel.setBounds(22, 56, 381, 176);
		frame.getContentPane().add(panel);

		JLabel lblSchnell = new JLabel("schnell");
		lblSchnell.setBounds(10, 0, 46, 14);
		frame.getContentPane().add(lblSchnell);

		JLabel lblLangsam = new JLabel("langsam");
		lblLangsam.setBounds(156, 0, 46, 14);
		frame.getContentPane().add(lblLangsam);

	}

	void berechneUndZeige() {

		Ball ball = Ball.newBall();

		print(panel.getGraphics(), ball);

		if (laufModus) {
			try {
				Thread.sleep(warteZeit.getValue() * 10);
				EventQueue.invokeLater(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	void print(Graphics gr, Ball ball) {
		gr.drawOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
	}

	@Override
	public void run() {
		berechneUndZeige();

		// if (laufModus) {
		// EventQueue.invokeLater(this);
		// }
	}
}
