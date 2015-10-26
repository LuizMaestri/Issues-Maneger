package view;

import controller.Manager;
import view.Manager.UIManager;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class PanelSistema extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;

    public PanelSistema() {

		setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane).setBounds(0, 0, 1000, 553);

		criarAbaMudanca();
		criarAbaCadastroMudanca();
		criarAbaCadastroUsuario();

		JButton botaoSair = new JButton("Sair");
		botaoSair.addActionListener(arg0 -> UIManager.setPanel(new Login(new Manager())));
		add(botaoSair).setBounds(459, 564, 100, 27);

	}

	private void criarAbaMudanca() {
		JPanel panelMudanca = new JPanel();
		panelMudanca.setLayout(null);
		panelMudanca.setOpaque(false);
		tabbedPane.addTab("Mudan�as", null, panelMudanca, null);

		String[] columns = { "N�mero", "Nome", "Status", "Data de Encerramento", "Data de Cria��o", "Aprovador",
				"Data de Libera��o", "Descri��o" };
		String[][] values = {
				{ "123", "Corre��o de funcionalidade", "Aberto", "Data de Encerramento", "Data de Cria��o", "Aprovador",
						"Data de Libera��o", "Descri��o" },
				{ "456", "Nova funcionalidade", "Aberto", "Data de Encerramento", "Data de Cria��o", "Aprovador",
						"Data de Libera��o", "Descri��o" } };

		JTable table = new JTable(values, columns) {
			private static final long serialVersionUID = 1L;

			// N�o permite editar o valor da tabela
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // N�o permite selecionar mais de uma linha

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelMudanca.add(scrollPane).setBounds(27, 54, 936, 388);

		scrollPane.setViewportView(table);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(arg0 -> {
            // TODO Auto-generated method stub
        });
		panelMudanca.add(btnEditar).setBounds(457, 468, 100, 27);
	}

	private void criarAbaCadastroUsuario() {
		JPanel panelCadastrarUsuario = new JPanel();
		panelCadastrarUsuario.setOpaque(false);
		panelCadastrarUsuario.setLayout(null);
		tabbedPane.addTab("Cadastrar Usu\u00E1rio", null, panelCadastrarUsuario, null);

		JCheckBox chckbxAprovador = new JCheckBox("Aprovador");
		chckbxAprovador.setBounds(47, 329, 130, 23);
		panelCadastrarUsuario.add(chckbxAprovador);

		JCheckBox chckbxAdministrador = new JCheckBox("Administrador");
		chckbxAdministrador.setBounds(183, 329, 138, 23);
		panelCadastrarUsuario.add(chckbxAdministrador);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(443, 399, 89, 29);
		panelCadastrarUsuario.add(btnCadastrar);

		JLabel lblCadastroDeUsurio = new JLabel("Cadastro de Usu\u00E1rio");
		lblCadastroDeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastroDeUsurio.setBounds(0, 11, 995, 23);
		panelCadastrarUsuario.add(lblCadastroDeUsurio);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(72, 98, 36, 22);
		panelCadastrarUsuario.add(lblNome);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLogin.setBounds(62, 189, 46, 27);
		panelCadastrarUsuario.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSenha.setBounds(48, 227, 60, 23);
		panelCadastrarUsuario.add(lblSenha);

		JLabel lblConfirmarSenha = new JLabel("Confirmar senha:");
		lblConfirmarSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmarSenha.setBounds(10, 261, 98, 23);
		panelCadastrarUsuario.add(lblConfirmarSenha);

        JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(115, 225, 203, 27);
		panelCadastrarUsuario.add(passwordField);

        JPasswordField passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(115, 260, 203, 27);
		panelCadastrarUsuario.add(passwordField_1);

        JTextField textField = new JTextField();
		textField.setBounds(115, 190, 203, 27);
		panelCadastrarUsuario.add(textField);
		textField.setColumns(10);

        JTextField textField_1 = new JTextField();
		textField_1.setBounds(115, 97, 325, 27);
		panelCadastrarUsuario.add(textField_1);
		textField_1.setColumns(10);
	}

	private void criarAbaCadastroMudanca() {
		JPanel panelCadastrarMudanca = new JPanel();
		panelCadastrarMudanca.setOpaque(false);
		panelCadastrarMudanca.setLayout(null);

		tabbedPane.addTab("Cadastrar mudan\u00E7a", null, panelCadastrarMudanca, null);

		JLabel lblNomeMudanca = new JLabel("Nome da Mudan\u00E7a:");
		lblNomeMudanca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeMudanca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeMudanca.setBounds(147, 58, 116, 27);
		panelCadastrarMudanca.add(lblNomeMudanca);

		JLabel lblDescricao = new JLabel("Desri\u00E7\u00E3o:");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescricao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescricao.setBounds(202, 105, 61, 14);
		panelCadastrarMudanca.add(lblDescricao);

        JTextField textField_2 = new JTextField();
		textField_2.setBounds(273, 58, 438, 27);
		panelCadastrarMudanca.add(textField_2);
		textField_2.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(273, 100, 438, 209);
		panelCadastrarMudanca.add(textArea);

		JButton btnCadastrar_1 = new JButton("Cadastrar");
		btnCadastrar_1.setBounds(454, 351, 89, 27);
		panelCadastrarMudanca.add(btnCadastrar_1);
	}
}
