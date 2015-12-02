package view.Panels;

import db.manipulate.IssueDAO;
import exception.InvalidParamsException;
import exception.PasswordException;
import model.Issue;
import model.Software;
import model.User;
import model.enums.IssueCategory;
import model.enums.UserType;
import utils.ProjectConstant;
import view.Manager.UIManager;
import view.dialogs.ApproveDialog;
import view.dialogs.DetailsDialog;

import java.awt.*;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.*;

public class SystemPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;
    private JTable softwareTable;
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
        tabIssue();
        boolean isAdmin = currentUser.isAdmin();
        if (isAdmin || currentUser.isAnalyst()){
            tabRegisterIssue();
            tabSoftware();
        }
        if (isAdmin){
            tabRegisterSoftware();
            tabUser();
            tabRegisterUser();
        }
    }

    private void tabIssue() {
        JPanel issues = new JPanel();
        issues.setLayout(null);
        issues.setOpaque(false);
        tabbedPane.addTab("Mudanças", issues);

        JLabel title = new JLabel("Mudanças", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        issues.add(title).setBounds(0, 11, 995, 23);


        ArrayList<Issue> issueList = ProjectConstant.getManager().getIssues();
        int len = issueList.size();

        JTable table = new JTable(len, 7);
        issues.add(setTable(table,issueList)).setBounds(100, 54, 800, 388);

        boolean isApproving = ProjectConstant.getManager().getCurrentUser().isApproving();
        boolean isDeveloper = ProjectConstant.getManager().getCurrentUser().isDeveloper();

        JButton detail = new JButton("Detalhes");
        detail.addActionListener(arg0 -> {
            int row = table.getSelectedRow();
            if (row > -1)
                new DetailsDialog(
                        new JFrame(),
                        ProjectConstant.getManager().getIssues().get(row)
                );
            else JOptionPane.showMessageDialog(null,"Por Favor, selecione uma mudança", "", JOptionPane.WARNING_MESSAGE);
        });
        issues.add(detail).setBounds(isApproving || isDeveloper? 450 : 500, 468, 100, 27);

        if (isApproving) {
            JButton approve = new JButton("Avaliar");
            approve.addActionListener(arg0 -> {
                int row = table.getSelectedRow();
                if (row > -1)
                    new ApproveDialog(
                            new JFrame(),
                            ProjectConstant.getManager().getIssues().get(row)
                    );
                else JOptionPane.showMessageDialog(null,"Por Favor, selecione uma mudança", "", JOptionPane.WARNING_MESSAGE);
            });
            issues.add(approve).setBounds(550, 468, 100, 27);
        }
        if (isDeveloper){
            JButton implement = new JButton("Implementar");
            implement.addActionListener(arg0 -> {
                // TODO change issue status to IssueStatus(2)
            });
            issues.add(implement).setBounds(550, 468, 120, 27);
        }
    }

    private void tabRegisterIssue() {
        //TODO list software to assign issue
        JPanel registerIssue = new JPanel(null, true);
        registerIssue.setOpaque(false);
        tabbedPane.addTab("Cadastrar mudança", registerIssue);

        JLabel title = new JLabel("Cadastro de Mudanças", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        registerIssue.add(title).setBounds(0, 11, 995, 23);

        JLabel name = new JLabel("Nome da Mudança:");
        name.setFont(ProjectConstant.getFont());
        name.setHorizontalAlignment(SwingConstants.RIGHT);
        registerIssue.add(name).setBounds(94, 120, 130, 27);

        JTextField nameField = new JTextField();
        registerIssue.add(nameField).setBounds(233, 120, 438, 27);
        nameField.setColumns(20);

        JLabel description = new JLabel("Desrição:");
        description.setFont(ProjectConstant.getFont());
        description.setHorizontalAlignment(SwingConstants.RIGHT);
        registerIssue.add(description).setBounds(162, 160, 61, 14);

        JTextArea descriptionField = new JTextArea();
        registerIssue.add(descriptionField).setBounds(233, 160, 438, 209);

        ArrayList<Software> softwareList = ProjectConstant.getManager().getSoftwares();
        int len = softwareList.size();

        JTable table = new JTable(len, 2);
        registerIssue.add(setTable(table, softwareList, true)).setBounds(700, 58, 250, 388);

        JRadioButton release = new JRadioButton(IssueCategory.getCategory(2).name().replace('_', ' '), true);
        JRadioButton minor = new JRadioButton(IssueCategory.getCategory(1).name(), false);
        JRadioButton fix = new JRadioButton(IssueCategory.getCategory(0).name(), false);

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(release);
        buttons.add(minor);
        buttons.add(fix);

        release.setFont(ProjectConstant.getFont());
        minor.setFont(ProjectConstant.getFont());
        fix.setFont(ProjectConstant.getFont());

        registerIssue.add(release).setBounds(300, 415, 180, 27);
        registerIssue.add(minor).setBounds(480, 415, 105, 27);
        registerIssue.add(fix).setBounds(580, 415, 130, 27);

        JLabel deadline = new JLabel("Prazo:");
        deadline.setFont(ProjectConstant.getFont());
        registerIssue.add(deadline).setBounds(380, 385, 60, 27);

        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int year = calendar.get(GregorianCalendar.YEAR);
        boolean leapYear = calendar.isLeapYear(year);
        int monthLength = Month.JANUARY.length(leapYear);

        JComboBox<Object> dayBox = new JComboBox<>();
        for (int i = 1; i <= monthLength; i++) dayBox.addItem(i);
        registerIssue.add(dayBox).setBounds(445, 385, 50, 27);

        JComboBox<Object> monthBox = new JComboBox<>();
        for (Month month : Month.values()) monthBox.addItem(month.getValue());
        registerIssue.add(monthBox).setBounds(500, 385, 50, 27);

        monthBox.addItemListener(e -> {
            dayBox.removeAllItems();
            Month currentMouth = Month.of(Integer.parseInt(monthBox.getSelectedItem().toString()));
            for (int i = 1; i <= currentMouth.length(leapYear); i++) dayBox.addItem(i);
        });

        JComboBox<Object> yearBox = new JComboBox<>();
        for (int i = year; i < year+10; i++) yearBox.addItem(i);
        registerIssue.add(yearBox).setBounds(555, 385, 80, 27);

        JButton register = new JButton("Cadastrar");
        register.addActionListener(arg0 -> {
            IssueCategory category;
            if (release.isSelected()) category = IssueCategory.getCategory(2);
            else {
                if (minor.isSelected()) category = IssueCategory.getCategory(1);
                else category = IssueCategory.getCategory(0);
            }
            calendar.set(
                    Integer.parseInt(yearBox.getSelectedItem().toString()),
                    Integer.parseInt(monthBox.getSelectedItem().toString()),
                    Integer.parseInt(dayBox.getSelectedItem().toString()));
            ProjectConstant.getManager().createIssue(
                    nameField.getText(),
                    descriptionField.getText(),
                    category,
                    calendar.getTime(),
                    table.getSelectedRow()
            );
            calendar.setTime(new Date());
        });
        registerIssue.add(register).setBounds(454, 440, 130, 27);
    }

    private void tabSoftware() {
        JPanel softwares = new JPanel();
        softwares.setOpaque(false);
        softwares.setLayout(null);
        tabbedPane.addTab("Softwares", softwares);

        JLabel title = new JLabel("Softwares", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        softwares.add(title).setBounds(0, 11, 995, 23);

        String[] columns = {
                "Nome",
                "versão",
                "Mudanças Associadas"
        };
        List<Software> softwareList = ProjectConstant.getManager().getSoftwares();
        int len = softwareList.size();
        String[][] values = new String[len][columns.length];
        for (int i = 0; i < len; i++){
            String[] softwareDetails = softwareList.get(i).toString().split(" [|] ");
            System.arraycopy(softwareDetails, 1, values[i], 0, 2);
            try {
                values[i][2] = String.valueOf(IssueDAO.count(Integer.parseInt(softwareDetails[0])));
            } catch (SQLException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um problema, tente mais tarde", "Conexão", JOptionPane.ERROR_MESSAGE);
            }
        }

        softwareTable = new JTable(values, columns) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        softwareTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(softwareTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        softwares.add(scrollPane).setBounds(200, 54, 600, 388);

        scrollPane.setViewportView(softwareTable);

        JButton deprecate = new JButton("Desativar");
        deprecate.addActionListener(arg0 ->{
            System.out.println(softwareTable.getSelectedRow());
        });
        softwares.add(deprecate).setBounds(450, 450, 130, 29);
    }

    private void tabRegisterSoftware() {
        JPanel registerSoftware = new JPanel();
        registerSoftware.setOpaque(false);
        registerSoftware.setLayout(null);
        tabbedPane.addTab("Cadastrar Software", registerSoftware);

        JLabel title = new JLabel("Cadastro de Software", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        registerSoftware.add(title).setBounds(0, 11, 995, 23);

        JLabel name = new JLabel("Nome:", SwingConstants.RIGHT);
        name.setFont(ProjectConstant.getFont());
        registerSoftware.add(name).setBounds(235, 120, 60, 22);

        JLabel version = new JLabel("Versão:", SwingConstants.RIGHT);
        version.setFont(ProjectConstant.getFont());
        registerSoftware.add(version).setBounds(235, 190, 60, 27);

        JTextField nameField = new JTextField(50);
        registerSoftware.add(nameField).setBounds(300, 120, 300, 27);

        JTextField versionField = new JTextField(20);
        registerSoftware.add(versionField).setBounds(300, 190, 300, 27);

        JButton register = new JButton("Cadastrar");
        register.addActionListener(arg0 ->{
            String nameText = nameField.getText();
            if (!nameText.equals("")) {
                String versionText = versionField.getText();
                if (versionText.equals("")) versionText = "1.0.0";
                if (ProjectConstant.getManager().createSoftware(nameText, versionText.split("[.]")))
                    System.out.println("okay");
                else JOptionPane.showMessageDialog(null, "Ocorreu um problema, tente mais tarde", "Conexão", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Verifique se está preenchido o campo nome", "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        });
        registerSoftware.add(register).setBounds(443, 399, 130, 29);

    }

    private void tabUser(){

    }

    private void tabRegisterUser() {
        //TODO enable button when all fields filled and password and confirm password are the same
        JPanel registerUser = new JPanel();
        registerUser.setOpaque(false);
        registerUser.setLayout(null);
        tabbedPane.addTab("Cadastrar Usuário", registerUser);

        JLabel title = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        registerUser.add(title).setBounds(0, 11, 995, 23);

        JLabel name = new JLabel("Nome:", SwingConstants.RIGHT);
        name.setFont(ProjectConstant.getFont());
        registerUser.add(name).setBounds(235, 120, 60, 22);

        JLabel login = new JLabel("Login:", SwingConstants.RIGHT);
        login.setFont(ProjectConstant.getFont());
        registerUser.add(login).setBounds(235, 190, 60, 27);

        JLabel password = new JLabel("Senha:", SwingConstants.RIGHT);
        password.setFont(ProjectConstant.getFont());
        registerUser.add(password).setBounds(235, 225, 60, 23);

        JLabel passwordConfirm = new JLabel("Confirmar senha:", SwingConstants.RIGHT);
        passwordConfirm.setFont(ProjectConstant.getFont());
        registerUser.add(passwordConfirm).setBounds(175, 260, 120, 23);

        JTextField nameField = new JTextField(50);
		registerUser.add(nameField).setBounds(300, 120, 300, 27);

        JTextField loginField = new JTextField(20);
        registerUser.add(loginField).setBounds(300, 190, 300, 27);

        JTextField passwordField = new JPasswordField(20);
        registerUser.add(passwordField).setBounds(300, 225, 300, 27);

        JTextField passwordFieldConfirm = new JPasswordField(20);
        registerUser.add(passwordFieldConfirm).setBounds(300, 260, 300, 27);

        JRadioButton developer = new JRadioButton(UserType.getType(0).name(), true);
        JRadioButton approving = new JRadioButton(UserType.getType(1).name(), false);
        JRadioButton analyst = new JRadioButton(UserType.getType(2).name(), false);
        JRadioButton admin = new JRadioButton(UserType.getType(3).name(), false);

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(developer);
        buttons.add(approving);
        buttons.add(analyst);
        buttons.add(admin);

        developer.setFont(ProjectConstant.getFont());
        approving.setFont(ProjectConstant.getFont());
        analyst.setFont(ProjectConstant.getFont());
        admin.setFont(ProjectConstant.getFont());

        registerUser.add(developer).setBounds(650, 160, 130, 27);
        registerUser.add(approving).setBounds(650, 190, 130, 27);
        registerUser.add(analyst).setBounds(650, 215, 130, 27);
        registerUser.add(admin).setBounds(650, 240, 130, 27);

        JButton register = new JButton("Cadastrar");
        //register.setEnabled(false);
        register.addActionListener(arg0 ->{
            UserType type;
            if (developer.isSelected()) type = UserType.getType(0);
            else {
                if (approving.isSelected()) type = UserType.getType(1);
                else{
                    if (analyst.isSelected()) type = UserType.getType(2);
                    else type = UserType.getType(3);
                }
            }
            try {
                ProjectConstant.getManager().createUser(
                        loginField.getText(),
                        nameField.getText(),
                        passwordField.getText(),
                        passwordFieldConfirm.getText(),
                        type
                );
            }catch (PasswordException e){
                JOptionPane.showMessageDialog(null, "Usuário(login) já existente", "Já existe", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidParamsException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Campos vazios", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Senha inválida", JOptionPane.ERROR_MESSAGE);
            }catch (ClassNotFoundException e){
                JOptionPane.showMessageDialog(null, "Problemas na conexão com o banco", "Conexão", JOptionPane.ERROR_MESSAGE);
            }
        });
        registerUser.add(register).setBounds(443, 399, 130, 29);
	}

    private JScrollPane setTable(JTable table, ArrayList<Issue> infos) {
        JScrollPane scrollPane = new JScrollPane(setInfosIssue(table, infos), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(table);
        return scrollPane;
    }

    private JTable setInfosIssue(JTable table, ArrayList<Issue> infos){
        String[] columns = {
                "Nome",
                "Status",
                "Data de Criação",
                "Aprovador",
                "Software",
                "Desenvolvedor",
                "Prazo"
        };
        for (int i= 0; i < columns.length; i++) table.getColumnModel().getColumn(i).setHeaderValue(columns[i]);
        if (!infos.isEmpty())
            for (int i =0; i < infos.size(); i++){
                Issue issue = infos.get(i);
                String[] values = issue.toString().split(" [|] ");
                table.setValueAt(values[0], i, 0);
                table.setValueAt(values[1], i, 1);
                table.setValueAt(values[2], i, 2);
                table.setValueAt(values[3], i, 3);
                table.setValueAt(values[4], i, 4);
                table.setValueAt(values[5], i, 5);
                table.setValueAt(values[6], i, 6);
            }
        return table;
    }

    private JScrollPane setTable(JTable table, ArrayList<Software> infos, boolean register){
        JScrollPane scrollPane = new JScrollPane(setInfosSoftware(table, infos, register), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(table);
        return scrollPane;
    }

    private JTable setInfosSoftware(JTable table, ArrayList<Software> infos, boolean register){
        String[] columns = {
                "Software",
                "Vesion",
                "Mudanças"
        };
        for (int i = 0; i < 3; i ++){
            if (register && i == 2) break;
            table.getColumnModel().getColumn(i).setHeaderValue(columns[i]);
        }
        for (int i = 0; i < infos.size(); i++) {
            Software software = infos.get(i);
            String[] values = software.toString().split(" [|] ");
            table.setValueAt(values[1], i, 0);
            table.setValueAt(values[2], i, 1);
            if (!register) {
                try {
                    table.setValueAt(IssueDAO.count(Long.parseLong(values[0])), i, 2);
                } catch (SQLException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Erro de conexão", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return table;
    }
}
