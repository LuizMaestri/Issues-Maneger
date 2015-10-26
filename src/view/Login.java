package view;

import controller.Manager;
import exception.AuthenticationUserException;
import exception.ConnectionException;
import view.Manager.UIManager;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField loginField;
	private JPasswordField passwordField;
	
	public Login(Manager issuesManager) {
		
		setLayout(null);
		
		JLabel lblDescricaoSistema =
				new JLabel(
                        "<html> \r\nBem vindo ao programa\r\n<br>\r\n(Descrição do sistema)\r\n</html>",
                        SwingConstants.CENTER
                );
        lblDescricaoSistema.setBorder(
                new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)
        );
        add(lblDescricaoSistema).setBounds(31, 174, 280, 302);

        JLabel lblLogin = new JLabel("Login:", SwingConstants.RIGHT);
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(lblLogin).setBounds(572, 218, 38, 27);

		JLabel lblSenha = new JLabel("Senha:", SwingConstants.RIGHT);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(lblSenha).setBounds(572, 248, 38, 26);

		loginField = new JTextField(20);
        add(loginField).setBounds(614, 219, 222, 27);
		
		passwordField = new JPasswordField();
        add(passwordField).setBounds(614, 249, 222, 27);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.addActionListener(arg0 -> {
            try {
                if(
                        issuesManager.login(
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
        add(botaoEntrar).setBounds(660, 303, 100, 28);

		JLabel labelBorda = new JLabel("");
        labelBorda.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(labelBorda).setBounds(534, 177, 339, 178);

        JLabel lblTitulo = new JLabel("Manager de Mudan\u00E7as", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(lblTitulo).setBounds(10, 11, 980, 73);
	}
}
