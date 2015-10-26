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

        JButton exit = new JButton("Sair");
        exit.addActionListener(arg0 -> UIManager.setPanel(new Login(new Manager())));
        add(exit).setBounds(459, 564, 100, 27);

	}

    private void criarAbaMudanca() {
        JPanel panelMudanca = new JPanel(null, true);
        panelMudanca.setOpaque(false);
        tabbedPane.addTab("Mudanças", null, panelMudanca, null);

        String[] columns = { "Número", "Nome", "Status", "Data de Encerramento", "Data de Criação", "Aprovador",
                "Data de Liberação", "Descrição" };
        String[][] values = {
                { "123", "Correção de funcionalidade", "Aberto", "Data de Encerramento", "Data de Criação", "Aprovador",
                        "Data de Liberação", "Descrição" },
                { "456", "Nova funcionalidade", "Aberto", "Data de Encerramento", "Data de Criação", "Aprovador",
                        "Data de Liberação", "Descrição" } };

        JTable table = new JTable(values, columns) {
            private static final long serialVersionUID = 1L;
            // N�o permite editar o valor da tabela
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelMudanca.add(scrollPane).setBounds(27, 54, 936, 388);
        scrollPane.setViewportView(table);

        JButton edit = new JButton("Editar");
        edit.addActionListener(arg0 -> {
            // TODO Auto-generated method stub
        });
        panelMudanca.add(edit).setBounds(457, 468, 100, 27);
    }

    private void criarAbaCadastroUsuario() {
        JPanel panelCadastrarUsuario = new JPanel(null, true);
        panelCadastrarUsuario.setOpaque(false);

        tabbedPane.addTab("Cadastrar Usuário", null, panelCadastrarUsuario, null);

        panelCadastrarUsuario.add(new JCheckBox("Aprovador")).setBounds(47, 329, 130, 23);
        panelCadastrarUsuario.add(new JCheckBox("Administrador")).setBounds(183, 329, 138, 23);

        panelCadastrarUsuario.add(new JButton("Cadastrar")).setBounds(443, 399, 89, 29);
        JLabel lblCadastroDeUsurio = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
        lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelCadastrarUsuario.add(lblCadastroDeUsurio).setBounds(0, 11, 995, 23);

        JLabel lblNome = new JLabel("Nome:", SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblNome).setBounds(72, 98, 36, 22);

        JLabel lblLogin = new JLabel("Login:", SwingConstants.RIGHT);
        lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblLogin).setBounds(62, 189, 46, 27);

        JLabel lblSenha = new JLabel("Senha:", SwingConstants.RIGHT);
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblSenha).setBounds(48, 227, 60, 23);

        JLabel lblConfirmarSenha = new JLabel("Confirmar senha:", SwingConstants.RIGHT);
        lblConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panelCadastrarUsuario.add(lblConfirmarSenha).setBounds(10, 261, 98, 23);

        panelCadastrarUsuario.add(new JPasswordField()).setBounds(115, 225, 203, 27);
		panelCadastrarUsuario.add(new JPasswordField()).setBounds(115, 260, 203, 27);
		panelCadastrarUsuario.add(new JTextField(10)).setBounds(115, 190, 203, 27);
		panelCadastrarUsuario.add(new JTextField(10)).setBounds(115, 97, 325, 27);
	}

	private void criarAbaCadastroMudanca() {
		JPanel panelCadastrarMudanca = new JPanel(null, true);
		panelCadastrarMudanca.setOpaque(false);
		tabbedPane.addTab("Cadastrar mudança", null, panelCadastrarMudanca, null);

        JLabel lblNomeMudanca = new JLabel("Nome da Mudança:", SwingConstants.RIGHT);
		lblNomeMudanca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCadastrarMudanca.add(lblNomeMudanca).setBounds(147, 58, 116, 27);

        JLabel lblDescricao = new JLabel("Desrição:", SwingConstants.RIGHT);
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCadastrarMudanca.add(lblDescricao).setBounds(202, 105, 61, 14);

        panelCadastrarMudanca.add(new JTextField(10)).setBounds(273, 58, 438, 27);
		panelCadastrarMudanca.add(new JTextArea()).setBounds(273, 100, 438, 209);
		panelCadastrarMudanca.add(new JButton("Cadastrar")).setBounds(454, 351, 89, 27);
	}
}
