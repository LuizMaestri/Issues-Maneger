package view.dialogs;

import model.Issue;
import model.User;
import utils.ProjectConstant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by luiz on 02/12/15.
 */
public class DetailsDialog extends JDialog {

    public DetailsDialog(Frame owner, Issue details) {
        super(owner);
        open( details);
    }

    private void open(Issue details) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel title = new JLabel("Detalhes", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(title).setBounds(0, 11, 400, 23);

        JLabel name = new JLabel("Nome:", SwingConstants.RIGHT);
        name.setFont(ProjectConstant.getFont());
        add(name).setBounds(10, 50, 100, 10);

        JTextField nameField = new JTextField(details.getName());
        nameField.setEditable(false);
        nameField.setBackground(getBackground());
        add(nameField).setBounds(120, 45, 200, 25);

        JLabel description = new JLabel("Descrição:", SwingConstants.RIGHT);
        description.setFont(ProjectConstant.getFont());
        add(description).setBounds(10, 80, 100, 15);

        JTextArea descriptionField = new JTextArea(details.getName());
        descriptionField.setEditable(false);
        descriptionField.setBackground(getBackground());
        add(descriptionField).setBounds(120, 75, 200, 100);

        JLabel requester = new JLabel("Analista:", SwingConstants.RIGHT);
        requester.setFont(ProjectConstant.getFont());
        add(requester).setBounds(10, 190, 100, 10);

        JTextField requesterField = new JTextField(details.getRequester().getName());
        requesterField.setEditable(false);
        requesterField.setBackground(getBackground());
        add(requesterField).setBounds(120, 185, 200, 25);

        JLabel approving = new JLabel("Aprovador:", SwingConstants.RIGHT);
        approving.setFont(ProjectConstant.getFont());
        add(approving).setBounds(10, 220, 100, 15);

        User approvingUser = details.getApproving();
        JTextField approvingField =
                new JTextField(
                        approvingUser != null? approvingUser.getName() : "ESPERANDO APROVAÇÃO"
                );
        approvingField.setEditable(false);
        approvingField.setBackground(getBackground());
        add(approvingField).setBounds(120, 215, 200, 25);

        JLabel developer = new JLabel("Desenvolvedor:", SwingConstants.RIGHT);
        developer.setFont(ProjectConstant.getFont());
        add(developer).setBounds(10, 250, 100, 10);

        JTextField developerField;
        if (approvingUser != null) {
            User developerUser = details.getMaker();
            developerField = new JTextField(developerUser != null ? developerUser.getName() : "EM ABERTO");
        }
        else developerField = new JTextField("ESPERANDO APROVAÇÃO");
        developerField.setEditable(false);
        developerField.setBackground(getBackground());
        add(developerField).setBounds(120, 245, 200, 25);

        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
