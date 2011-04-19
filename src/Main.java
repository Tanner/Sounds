import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoardPanel boardPanel = new BoardPanel();
		frame.getContentPane().add(boardPanel);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
