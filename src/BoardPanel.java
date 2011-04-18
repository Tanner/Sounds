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
	public static final boolean DEBUG = false;
	
	public static final int CYCLE_DURATION = 500;
	
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
		
		board[10][10].setPower(2000);
		pulseOccurred(board[10][10]);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == timer) {
			if (DEBUG) System.out.println("------------ Timer fired!");
			for (int r = 0; r < NUM_OF_SQUARES; r++) {
				for (int c = 0; c < NUM_OF_SQUARES; c++) {
					board[r][c].timerFired();
				}
			}

			if (DEBUG) System.out.println("------------ Timer painting!");
			for (int r = 0; r < NUM_OF_SQUARES; r++) {
				for (int c = 0; c < NUM_OF_SQUARES; c++) {
					if (board[r][c].isAwake()) board[r][c].repaint();
				}
			}
		}
	}
	
	public void pulseOccurred(Square source) {
		int r = source.getRow();
		int c = source.getColumn();
		
		int pulsePower = source.getPower() + source.getPulse();
		pulsePower -= pulsePower * PULSE_DECAY;
		
		if (DEBUG) System.out.println("------------ Pulse Occurred - ("+c+", "+r+") pow: "+pulsePower);
		
		if (pulsePower > 0) {
			doPulse(r, c + 1, pulsePower);
			doPulse(r, c - 1, pulsePower);
			doPulse(r + 1, c, pulsePower);
			doPulse(r - 1, c, pulsePower);
//			doPulse(r + 1, c - 1, pulsePower);
//			doPulse(r - 1, c + 1, pulsePower);
			
			//Corner
//			doPulse(r + 1, c + 1, pulsePower);
//			doPulse(r - 1, c - 1, pulsePower);
		}
	}
	
	public void doPulse(int r, int c, int pulsePower) {
		if (r >= 0 && r < NUM_OF_SQUARES && c >= 0 && c < NUM_OF_SQUARES) {
			if (!board[r][c].isPulsing()) {
				board[r][c].pulseDown(pulsePower);
			}
		}
	}
}
