package bernabe;

import base.Main;
import delarama.AuditTrail;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.awt.Color;

public class UserProfilePage {

    private static final JPanel panel = new JPanel();
    private static final JTextArea addr = new JTextArea();
    private static final JPasswordField pass_field = new JPasswordField();
    private static final JTextField
        username = new JTextField(),
        no = new JTextField(),
        full_name = new JTextField(),
        email = new JTextField();
    private static final JLabel 
        username_label = new JLabel("Username:"),
        password_label = new JLabel("Password:"),
        fullname_label = new JLabel("Full Name:"),
        email_label = new JLabel("Email:"),
        no_label = new JLabel("Contact Number:"),
        addr_label = new JLabel("Address:"),
        theme_label = new JLabel("App Theme:");
    private static final JComboBox<String> theme_dropdown = new JComboBox<>(new String[]{"Default","Dark","Light"});
    

    static void setFields(ArrayList<String> account_row) {
        username.setText(account_row.get(0));
        pass_field.setText(account_row.get(1));
        full_name.setText(account_row.get(2));
        email.setText(account_row.get(3));
        no.setText(account_row.get(4));
        addr.setText(account_row.get(5));
    }


    static String[] getFields() {
        return new String[] {
            username.getText(),
            new String(pass_field.getPassword()),
            full_name.getText(),
            email.getText(),
            no.getText(),
            addr.getText()
        };
    }


    static JPanel createPanel() {
        panel.setLayout(null);
        JPanel
            top_bar = new JPanel(),
            bottom_bar = new JPanel(),
            backdrop = new JPanel();
        JButton 
            back_button = new JButton("<"),
            view_pass_button = new JButton("View"),
            save_changes = new JButton("Save Changes"),
            logout_button = new JButton("Log Out");
        JScrollPane addr_pane = new JScrollPane(addr);

        panel.setBackground(Main.getDarkColor());
        top_bar.setBackground(Main.getLightColor());
        bottom_bar.setBackground(Main.getLightColor());
        back_button.setBackground(Main.getMidColor());
        back_button.setForeground(Color.WHITE);
        backdrop.setBackground(Main.getLightColor());
        theme_label.setForeground(Color.WHITE);
        theme_dropdown.setBackground(Main.getMidColor());
        theme_dropdown.setForeground(Color.WHITE);
        view_pass_button.setBackground(Main.getMidColor());
        view_pass_button.setForeground(Color.WHITE);
        save_changes.setBackground(Main.getMidColor());
        save_changes.setForeground(Color.WHITE);
        logout_button.setBackground(Main.getMidColor());
        logout_button.setForeground(Color.WHITE);
        username_label.setForeground(Color.BLACK);
        password_label.setForeground(Color.BLACK);
        fullname_label.setForeground(Color.BLACK);
        email_label.setForeground(Color.BLACK);
        no_label.setForeground(Color.BLACK);
        addr_label.setForeground(Color.BLACK);
        
        password_label.setBounds(82,97,234,38);
        email_label.setBounds(82,244,234,38);
        addr_label.setBounds(82,391,234,38);
        no_label.setBounds(82,317,234,38);
        fullname_label.setBounds(82,170,234,38);
        username_label.setBounds(82,24,234,38);
        logout_button.setBounds(700,556,110,37);
        backdrop.setBounds(64,8,432,645);
        back_button.setBounds(0,0,45,30);
        top_bar.setBounds(0,0,880,30);
        bottom_bar.setBounds(0,630,880,30);
        username.setBounds(82,62,234,38);
        pass_field.setBounds(82,135,234,38);
        full_name.setBounds(82,208,369,38);
        email.setBounds(82,282,369,38);
        no.setBounds(82,355,234,38);
        addr_pane.setBounds(82,429,395,92);
        theme_label.setBounds(555,87,145,27);
        save_changes.setBounds(196,556,168,37);
        view_pass_button.setBounds(323,136,82,37);
        theme_dropdown.setBounds(700,85,101,32);

        addr_label.setFont(Main.getFont(24));
        no_label.setFont(Main.getFont(24));
        email_label.setFont(Main.getFont(24));
        fullname_label.setFont(Main.getFont(24));
        password_label.setFont(Main.getFont(24));
        username_label.setFont(Main.getFont(24));
        logout_button.setFont(Main.getFont(18));
        save_changes.setFont(Main.getFont(18));
        view_pass_button.setFont(Main.getFont(18));
        theme_dropdown.setFont(Main.getFont(18));
        theme_label.setFont(Main.getFont(24));
        username.setFont(Main.getFont(19));
        no.setFont(Main.getFont(19));
        full_name.setFont(Main.getFont(19));
        email.setFont(Main.getFont(19));
        addr.setFont(Main.getFont(19));

        addr.setLineWrap(true);
        addr.setWrapStyleWord(true);

        panel.add(username_label);
        panel.add(password_label);
        panel.add(fullname_label);
        panel.add(email_label);
        panel.add(no_label);
        panel.add(addr_label);
        panel.add(logout_button);
        panel.add(theme_dropdown);
        panel.add(save_changes);
        panel.add(view_pass_button);
        panel.add(theme_label);
        panel.add(username);
        panel.add(pass_field);
        panel.add(full_name);
        panel.add(email);
        panel.add(no);
        panel.add(addr_pane);
        panel.add(backdrop);
        panel.add(back_button);
        panel.add(top_bar);
        panel.add(bottom_bar);

        logout_button.addActionListener(e -> {
            AuditTrail.Audit_Trail("Logged out");
            Main.changeCard("login");
        });
        view_pass_button.addActionListener(e -> Main.popupMessage(new String(pass_field.getPassword())));
        save_changes.addActionListener(e -> UserProfileMain.saveAccountChanges());
        theme_dropdown.addActionListener(e -> UserProfileMain.changeAppTheme(theme_dropdown.getSelectedItem()));
        back_button.addActionListener(e -> Main.gotoDashboard());

        return panel;
    }
}