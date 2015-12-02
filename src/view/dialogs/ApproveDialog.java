package view.dialogs;

import model.Issue;
import utils.ProjectConstant;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;

public class ApproveDialog extends JDialog {

    public ApproveDialog(Frame owner, Issue issue) {
        super(owner);
        open(issue);
    }

    private void open(Issue issue) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel title = new JLabel("Analise da Mudança", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(title).setBounds(0, 11, 400, 23);

        JLabel name = new JLabel("Nome:", SwingConstants.RIGHT);
        name.setFont(ProjectConstant.getFont());
        add(name).setBounds(10, 50, 100, 10);

        JTextField nameField = new JTextField(issue.getName());
        nameField.setEditable(false);
        nameField.setBackground(getBackground());
        add(nameField).setBounds(120, 45, 200, 25);

        JLabel description = new JLabel("Descrição:", SwingConstants.RIGHT);
        description.setFont(ProjectConstant.getFont());
        add(description).setBounds(10, 80, 100, 15);

        JTextArea descriptionField = new JTextArea(issue.getName());
        descriptionField.setEditable(false);
        descriptionField.setBackground(getBackground());
        add(descriptionField).setBounds(120, 75, 200, 100);

        JLabel software = new JLabel("Software:", SwingConstants.RIGHT);
        software.setFont(ProjectConstant.getFont());
        add(software).setBounds(10, 190, 100, 10);

        JTextField softwareField = new JTextField(issue.getSoftware().getName());
        softwareField.setEditable(false);
        softwareField.setBackground(getBackground());
        add(softwareField).setBounds(120, 185, 200, 25);

        JLabel deadline = new JLabel("Prazo:", SwingConstants.RIGHT);
        deadline.setFont(ProjectConstant.getFont());
        add(deadline).setBounds(10, 220, 100, 10);

        JTextField deadlineField =
                new JTextField(
                        DateFormat.getDateTimeInstance().format(
                                issue.getDeadline()
                        ).split(" ")[0]
                );
        deadlineField.setEditable(false);
        deadlineField.setBackground(getBackground());
        add(deadlineField).setBounds(120, 215, 200, 25);

        JButton cancel = new JButton("Cancelar");
        add(cancel).setBounds(50, 250, 150, 25);

        JButton approve = new JButton("Aprovar");
        add(approve).setBounds(200, 250, 150, 25);

        setSize(400, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
