package view;

import view.dialogs.About;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	public Window() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1000, 21);

		menuBar.add(new JMenu("Ajuda"))
                .add(new JMenuItem("Sobre"))
                .addActionListener(arg0 -> new About(this));
		setJMenuBar(menuBar);

		panel = new JPanel(null, true);
		this.getContentPane().add(panel);
	}

	public JPanel getPanel() {
		return panel;
	}

}
