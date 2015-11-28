package view;

import utils.ProjectConstant;

import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class About extends JDialog {

	private static final long serialVersionUID = 1L;

	public About(JFrame parent) {
		super(parent, "Sobre o Gestor de Mudanças", true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

        String iconPath = "resources" + File.separator + "icone.png";

		JLabel title = new JLabel("Gestor de Mudanças", new ImageIcon(iconPath), SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		getContentPane().add(title).setBounds(10, 11, 364, 54);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(arg0 -> About.this.dispose());
        getContentPane().add(btnOk).setBounds(150, 223, 89, 27);
		
		JLabel version = new JLabel("Versão: 3.0");
		version.setFont(ProjectConstant.getFont());
		getContentPane().add(version).setBounds(20, 75, 95, 14);
		
		setSize(400, 300);
        setLocationRelativeTo(null);
		setResizable(false);

		setVisible(true);
	}
}