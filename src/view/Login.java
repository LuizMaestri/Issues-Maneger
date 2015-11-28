package view;

import exception.AuthenticationUserException;
import exception.ConnectionException;
import utils.ProjectConstant;
import view.Manager.UIManager;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField loginField;
	private JPasswordField passwordField;

	public Login() {

		setLayout(null);

		JLabel description =
				new JLabel(
                        "<html> \r\nBem vindo ao programa\r\n<br>\r\n\r\n</html>",
                        SwingConstants.CENTER
                );
        description.setBorder(
                new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)
        );
        add(description).setBounds(31, 174, 280, 302);

        JLabel login = new JLabel("Login:", SwingConstants.RIGHT);
		login.setFont(ProjectConstant.getFont());
        add(login).setBounds(572, 218, 38, 27);

		JLabel password = new JLabel("Senha:", SwingConstants.RIGHT);
		password.setFont(ProjectConstant.getFont());
        add(password).setBounds(535, 248, 78, 26);

		loginField = new JTextField(20);
        add(loginField).setBounds(614, 219, 222, 27);

		passwordField = new JPasswordField();
        add(passwordField).setBounds(614, 249, 222, 27);

        JButton enter = new JButton("Entrar");
        enter.addActionListener(arg0 -> {
            try {
                if (
                        ProjectConstant.getManager().login(
                                loginField.getText(),
                                String.valueOf(
                                        passwordField.getPassword()
                                )
                        )
                        ) UIManager.setPanel(new SystemPanel());
            } catch (AuthenticationUserException | ConnectionException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
        });
        add(enter).setBounds(660, 303, 100, 28);

		JLabel border = new JLabel("");
        border.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(border).setBounds(534, 177, 339, 178);

        JLabel title = new JLabel("Gestor de Mudanças", SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(title).setBounds(10, 11, 980, 73);
	}
}
