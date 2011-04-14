import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardPanel extends JPanel implements ActionListener, PulseDelegate {
	private static final int NUM_OF_SQUARES = 20;
	private static final double PULSE_DECAY = 0.1;

	public static final int CYCLE_DURATION = 50;
	
	private Square[][] board;
	private Timer timer;

	public BoardPanel() {
		board = new Square[NUM_OF_SQUARES][NUM_OF_SQUARES];
		
		setLayout(new GridLayout(NUM_OF_SQUARES, NUM_OF_SQUARES));
		
		for (int r = 0; r < NUM_OF_SQUARES; r++) {
			for (int c = 0; c < NUM_OF_SQUARES; c++) {
				board[r][c] = new Square(r, c);
				board[r][c].setPulseDelegate(this);
				add(board[r][c]);
			}
		}

		timer = new Timer(CYCLE_DURATION, this);
		timer.start();
		
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(500, 500));
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == timer) {
			for (int r = 0; r < NUM_OF_SQUARES; r++) {
				for (int c = 0; c < NUM_OF_SQUARES; c++) {
					board[r][c].timerFired();
				}
			}
			
			for (int r = 0; r < NUM_OF_SQUARES; r++) {
				for (int c = 0; c < NUM_OF_SQUARES; c++) {
					board[r][c].repaint();
				}
			}
		}
	}
	
	public void pulseOccurred(Square source) {
		//
	}
}
