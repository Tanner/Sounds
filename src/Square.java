import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Square extends JPanel implements MouseListener {
	private static final Color defaultColor = new Color(0, 0, 0);
	private static final Color activeColor = new Color(127, 127, 127);
	private static int MAX_POWER = 1000;
	private static double MAX_PULSE_PERCENT = 1.0;
	private static double PULSE_STEP_UP_PERCENT = 0.10;
	
	private boolean charging, pulsing;
	private boolean pulseUp;
	private int timerCycles;
	private int power, pulse;
	private int row, column;
	
	private PulseDelegate pulseDelegate;
	
	public Square(int r, int c) {
		power = 0;
		
		row = r;
		column = c;
		pulseUp = true;
		
		setBackground(defaultColor);
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Update the power color
		int red = (int)scale(power + pulse, 0, MAX_POWER, defaultColor.getRed(), activeColor.getRed());
		int green = (int)scale(power + pulse, 0, MAX_POWER, defaultColor.getGreen(), activeColor.getGreen());
		int blue = (int)scale(power + pulse, 0, MAX_POWER, defaultColor.getBlue(), activeColor.getBlue());
		
		if (red > 255) red = 255; else if (red < 0) red = 0;
		if (green > 255) green = 255; else if (green < 0) green = 0;
		if (blue > 255) blue = 255; else if (blue < 0) blue = 0;
		
		setBackground(new Color(red, green, blue));
	}

	public void mouseClicked(MouseEvent arg0) { }
	public void mouseEntered(MouseEvent arg0) {	}
	public void mouseExited(MouseEvent arg0) { }
	
	public void mousePressed(MouseEvent arg0) {
		if (power == 0) {
			timerCycles = 0;
			charging = true;
		}
	}
	
	public void mouseReleased(MouseEvent arg0) {
		updatePower();
		pulsing = true;
		pulseUp = true;
		charging = false;
	}
	
	public void updatePower() {
		power = (int)scale(timerCycles, 0, BoardPanel.CYCLE_DURATION, 0, MAX_POWER);
		repaint();
	}
	
	public void pulse() {
		if (pulseUp) {
			pulse += power * PULSE_STEP_UP_PERCENT;
		} else {
			pulse -= power * PULSE_STEP_UP_PERCENT;
		}
				
		//Hit the max pulse we are allowed, start going down...
		if (power != 0 && pulse / power > MAX_PULSE_PERCENT) {
			pulseUp = false;
			pulseDelegate.pulseOccurred(this);
		}
		
		//Pulse is over
		if (pulse <= 0) {
			pulse = 0;
			pulsing = false;
			pulseUp = true;
		}
		
		repaint();
	}
	
	public void timerFired() {
		timerCycles++;
		
		if (charging) {
			updatePower();
		}
		
		if (pulsing) {
			pulse();
		}
	}
	
	private static double scale(double current, double currentMin, double currentMax, double targetMin, double targetMax) {
		double scaled = targetMin + (targetMax - targetMin) * ((current - currentMin) / (currentMax - currentMin));
		
		return scaled;
	}
	
	public void setPulseDelegate(PulseDelegate pd) {
		pulseDelegate = pd;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getPower() {
		return power;
	}
	
	public int getPulse() {
		return pulse;
	}
	
	public boolean isPulsing() {
		return pulsing;
	}
}
