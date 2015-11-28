package view;

import model.User;
import model.enums.IssueCategory;
import model.enums.UserType;
import utils.ProjectConstant;
import view.Manager.UIManager;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;

public class SystemPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    public SystemPanel() {

        setLayout(null);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane).setBounds(0, 0, 1000, 553);

        openTabs(ProjectConstant.getManager().getCurrentUser());

        JButton exit = new JButton("Sair");
        exit.addActionListener(arg0 -> {
            ProjectConstant.getManager().reset();
            UIManager.setPanel(new Login());
        });
        add(exit).setBounds(459, 564, 100, 27);

	}

    private void openTabs(User currentUser){
        // admin no restrictions create software
        // approving list, cancel and approve issues
        // developer list start and finish issues
        // analyst list, create and edit issues and list software


        tabIssue();
        if (currentUser.getType() == UserType.ADMIN || currentUser.getType() == UserType.ANALYST){
            tabRegisterIssue();
            tabSoftware();
        }
        if (currentUser.getType() == UserType.ADMIN){
            tabRegisterSoftware();
            tabRegisterUser();
        }
    }

    private void tabIssue() {
        JPanel panelMudanca = new JPanel();
        panelMudanca.setLayout(null);
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

    private void tabRegisterIssue() {
        JPanel registerIssue = new JPanel(null, true);
        registerIssue.setOpaque(false);

        tabbedPane.addTab("Cadastrar mudança", null, registerIssue, null);

        JLabel name = new JLabel("Nome da Mudança:");
        name.setFont(ProjectConstant.getFont());
        name.setHorizontalAlignment(SwingConstants.RIGHT);
        registerIssue.add(name).setBounds(134, 58, 130, 27);

        JTextField nameField = new JTextField();
        registerIssue.add(nameField).setBounds(273, 58, 438, 27);
        nameField.setColumns(20);

        JLabel description = new JLabel("Desrição:");
        description.setFont(ProjectConstant.getFont());
        description.setHorizontalAlignment(SwingConstants.RIGHT);
        registerIssue.add(description).setBounds(202, 105, 61, 14);

        JTextArea descriptionField = new JTextArea();
        registerIssue.add(descriptionField).setBounds(273, 100, 438, 209);

        JRadioButton release = new JRadioButton(IssueCategory.NOVA_FUNCINALIDADE.name().replace('_', ' '), true);
        JRadioButton minor = new JRadioButton(IssueCategory.MELHORIA.name(), false);
        JRadioButton fix = new JRadioButton(IssueCategory.CORRECAO.name(), false);

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(release);
        buttons.add(minor);
        buttons.add(fix);

        release.setFont(ProjectConstant.getFont());
        minor.setFont(ProjectConstant.getFont());
        fix.setFont(ProjectConstant.getFont());

        registerIssue.add(release).setBounds(300, 355, 180, 27);
        registerIssue.add(minor).setBounds(480, 355, 105, 27);
        registerIssue.add(fix).setBounds(580, 355, 130, 27);

        JLabel deadline = new JLabel("Prazo:");
        deadline.setFont(ProjectConstant.getFont());
        registerIssue.add(deadline).setBounds(380, 325, 60, 27);

        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int year = calendar.get(GregorianCalendar.YEAR);
        boolean leapYear = calendar.isLeapYear(year);
        int monthLength = Month.JANUARY.length(leapYear);

        JComboBox<Object> dayBox = new JComboBox<>();
        for (int i = 1; i <= monthLength; i++) dayBox.addItem(i);
        registerIssue.add(dayBox).setBounds(430, 325, 60, 27);

        JComboBox<Object> monthBox = new JComboBox<>();
        for (Month month : Month.values()) monthBox.addItem(month.getValue());
        registerIssue.add(monthBox).setBounds(500, 325, 60, 27);

        monthBox.addItemListener(e -> {
            dayBox.removeAllItems();
            Month currentMouth = Month.of(Integer.parseInt(monthBox.getSelectedItem().toString()));
            for (int i = 1; i <= currentMouth.length(leapYear); i++) dayBox.addItem(i);
        });

        JComboBox<Object> yearBox = new JComboBox<>();
        for (int i = year; i < year+10; i++) yearBox.addItem(i);

        JButton register = new JButton("Cadastrar");
        register.addActionListener(arg0 ->{
            IssueCategory category;
            if (release.isSelected()){
               category = IssueCategory.NOVA_FUNCINALIDADE;
            }else if (minor.isSelected()){
                category = IssueCategory.MELHORIA;
            }else {
                category = IssueCategory.CORRECAO;
            }
            calendar.set(
                    Integer.parseInt(yearBox.getSelectedItem().toString()),
                    Integer.parseInt(monthBox.getSelectedItem().toString()),
                    Integer.parseInt(dayBox.getSelectedItem().toString()));
            ProjectConstant.getManager().createIssue(
                    nameField.getText(),
                    descriptionField.getText(),
                    category,
                    calendar.getTime()
            );
            calendar.setTime(new Date());
        });
        registerIssue.add(register).setBounds(454, 401, 130, 27);
    }

    private void tabRegisterSoftware() {
    }

    private void tabSoftware() {
    }


    private void tabRegisterUser() {
        JPanel panelCadastrarUsuario = new JPanel();
        panelCadastrarUsuario.setOpaque(false);
        panelCadastrarUsuario.setLayout(null);
        tabbedPane.addTab("Cadastrar Usuário", null, panelCadastrarUsuario, null);

        panelCadastrarUsuario.add(new JCheckBox("Aprovador")).setBounds(47, 329, 130, 23);

        panelCadastrarUsuario.add(new JCheckBox("Administrador")).setBounds(183, 329, 138, 23);

        panelCadastrarUsuario.add(new JButton("Cadastrar")).setBounds(443, 399, 130, 29);

        JLabel lblCadastroDeUsurio = new JLabel("Cadastro de Usuário");
        lblCadastroDeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
        lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelCadastrarUsuario.add(lblCadastroDeUsurio).setBounds(0, 11, 995, 23);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(ProjectConstant.getFont());
        panelCadastrarUsuario.add(lblNome).setBounds(72, 98, 36, 22);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLogin.setFont(ProjectConstant.getFont());
        panelCadastrarUsuario.add(lblLogin).setBounds(62, 189, 46, 27);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSenha.setFont(ProjectConstant.getFont());
        panelCadastrarUsuario.add(lblSenha).setBounds(48, 227, 60, 23);

        JLabel lblConfirmarSenha = new JLabel("Confirmar senha:");
        lblConfirmarSenha.setHorizontalAlignment(SwingConstants.RIGHT);
        lblConfirmarSenha.setFont(ProjectConstant.getFont());
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
}
