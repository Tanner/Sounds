import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardPanel extends JPanel implements ActionListener, KeyListener, PulseDelegate {
	private static final int NUM_OF_SQUARES = 5;
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
		board[2][2].setPower(500);

		timer = new Timer(CYCLE_DURATION, this);
		//timer.start();
		
		setFocusable(true);
		addKeyListener(this);
		
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
		}
	}
	
	public void pulseOccurred(Square source) {
		int r = source.getRow();
		int c = source.getColumn();
		
		int pulsePower = (int)(source.getPower() + source.getPulse());
		pulsePower = (int)(pulsePower - Math.ceil(pulsePower * PULSE_DECAY));

		System.out.println("Pulse Occured at ("+c+", "+r+") with Power "+pulsePower);
		
		if (pulsePower > 0) {
			//Top Left Corner
			if (r - 1 >= 0 && !board[r - 1][c].isPulsing()) {
				board[r - 1][c].dieWithPulse(pulsePower);
				System.out.println("Pulse for ("+(r - 1)+", "+c+")");
			}
			
			if (c - 1 >= 0 && !board[r][c - 1].isPulsing()) {
				board[r][c - 1].dieWithPulse(pulsePower);
				System.out.println("Pulse for ("+(r)+", "+(c - 1)+")");
			}
			
			if (r - 1 >= 0 && c - 1 >= 0 && !board[r - 1][c - 1].isPulsing()) {
				board[r - 1][c - 1].dieWithPulse(pulsePower);
				System.out.println("Pulse for ("+(r - 1)+", "+(c - 1)+")");
			}
			
			//Bottom Right Corner
//			if (r + 1 < NUM_OF_SQUARES && !board[r + 1][c].isPulsing()) {
//				board[r + 1][c].dieWithPulse(pulsePower);
//				board[r + 1][c].repaint();
//				board[r + 1][c].waitUntilNextCycle();
//			}
//			
			if (c + 1 < NUM_OF_SQUARES && !board[r][c + 1].isPulsing()) {
				if (r == 1 && c + 1 == 3) {
					System.out.println("on location");
				}
				board[r][c + 1].dieWithPulse(pulsePower);
				board[r][c + 1].waitUntilNextCycle();
				board[r][c + 1].repaint();
				
				System.out.println("Pulse for ("+(c + 1)+", "+(r)+") on next round");
			}
//			
//			if (r + 1 < NUM_OF_SQUARES && c + 1 < NUM_OF_SQUARES && !board[r + 1][c + 1].isPulsing()) {
//				board[r + 1][c + 1].dieWithPulse(pulsePower);
//				board[r + 1][c + 1].repaint();
//				board[r + 1][c + 1].waitUntilNextCycle();
//			}
			
			//Diagonal Corner Bits
//			if (r + 1 < NUM_OF_SQUARES && c - 1 >= 0 && !board[r + 1][c - 1].isPulsing()) {
//				board[r + 1][c - 1].dieWithPulse(pulsePower);
//				board[r + 1][c - 1].repaint();
//				board[r + 1][c - 1].waitUntilNextCycle();
//			}
			
//			if (r - 1 >= 0 && c + 1 < NUM_OF_SQUARES && !board[r - 1][c + 1].isPulsing()) {
//				board[r - 1][c + 1].dieWithPulse(pulsePower);
//				board[r - 1][c + 1].repaint();
//				board[r - 1][c + 1].waitUntilNextCycle();
//			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 's') {
			pulseOccurred(board[2][2]);
		}
		
		for (int r = 0; r < NUM_OF_SQUARES; r++) {
			for (int c = 0; c < NUM_OF_SQUARES; c++) {
				board[r][c].timerFired();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
