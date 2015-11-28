package view.Manager;

import controller.Manager;
import view.Window;
import view.Login;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UIManager {

	private static Window prog;

	private static void open(String title) {
        String iconPath = "resources" + File.separator + "icone.png";
		LookAndFeel.aparenciaNimbus();
        prog = new Window();
        setPanel(new Login());
        prog.setTitle(title);
		prog.pack();
		prog.setIconImage(new ImageIcon(iconPath).getImage());
		prog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		prog.setSize(1000, 650);
		prog.setLocationRelativeTo(null);
		prog.setResizable(false);
		prog.setVisible(true);
	}

	public static void setPanel(JPanel panel) {
		JPanel currentPanel = prog.getPanel();
		currentPanel.removeAll();
		for (Component comp : panel.getComponents()) currentPanel.add(comp);
        String backPath = "resources" + File.separator + "background.jpg";
		currentPanel.add(new JLabel(new ImageIcon(backPath))).setBounds(0, 0, 1000, 600);
		currentPanel.repaint();
	}

	public static void run() {
        open("Sistema de Gestão de Mudanças");
	}
}
