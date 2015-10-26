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

public class SystemPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    public SystemPanel() {

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

        panelCadastrarUsuario.add(new JCheckBox("Aprovador")).setBounds(47, 329, 130, 23);

        panelCadastrarUsuario.add(new JCheckBox("Administrador")).setBounds(183, 329, 138, 23);

        panelCadastrarUsuario.add(new JButton("Cadastrar")).setBounds(443, 399, 89, 29);

        JLabel lblCadastroDeUsurio = new JLabel("Cadastro de Usu\u00E1rio");
        lblCadastroDeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
        lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelCadastrarUsuario.add(lblCadastroDeUsurio).setBounds(0, 11, 995, 23);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblNome).setBounds(72, 98, 36, 22);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblLogin).setBounds(62, 189, 46, 27);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblSenha).setBounds(48, 227, 60, 23);

        JLabel lblConfirmarSenha = new JLabel("Confirmar senha:");
        lblConfirmarSenha.setHorizontalAlignment(SwingConstants.RIGHT);
        lblConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblConfirmarSenha).setBounds(10, 261, 98, 23);

        panelCadastrarUsuario.add(new JPasswordField()).setBounds(115, 225, 203, 27);

		panelCadastrarUsuario.add( new JPasswordField()).setBounds(115, 260, 203, 27);

        JTextField textField = new JTextField();
		panelCadastrarUsuario.add(textField).setBounds(115, 190, 203, 27);
		textField.setColumns(10);

        JTextField textField_1 = new JTextField();
		panelCadastrarUsuario.add(textField_1).setBounds(115, 97, 325, 27);
		textField_1.setColumns(10);
	}

	private void criarAbaCadastroMudanca() {
		JPanel panelCadastrarMudanca = new JPanel(null, true);
		panelCadastrarMudanca.setOpaque(false);

		tabbedPane.addTab("Cadastrar mudan\u00E7a", null, panelCadastrarMudanca, null);

		JLabel lblNomeMudanca = new JLabel("Nome da Mudan\u00E7a:");
		lblNomeMudanca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeMudanca.setHorizontalAlignment(SwingConstants.RIGHT);
		panelCadastrarMudanca.add(lblNomeMudanca).setBounds(147, 58, 116, 27);

		JLabel lblDescricao = new JLabel("Desri\u00E7\u00E3o:");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescricao.setHorizontalAlignment(SwingConstants.RIGHT);
		panelCadastrarMudanca.add(lblDescricao).setBounds(202, 105, 61, 14);

        JTextField textField_2 = new JTextField();
		panelCadastrarMudanca.add(textField_2).setBounds(273, 58, 438, 27);
		textField_2.setColumns(10);

		panelCadastrarMudanca.add(new JTextArea()).setBounds(273, 100, 438, 209);
		panelCadastrarMudanca.add(new JButton("Cadastrar")).setBounds(454, 351, 89, 27);
	}
}
