package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;

import org.json.JSONObject;
import org.json.JSONArray;

import main.java.memoranda.util.Local;
import main.java.memoranda.util.AccountUtils;

/*$Id: LoginDialog.java,v 1.0 2024/01/22 jrfeathe Exp $*/
public class LoginDialog extends JDialog {
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    JPanel areaPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    JLabel jLabel1 = new JLabel();
    public JTextField usernameField = new JTextField();
    JLabel jLabel2 = new JLabel();
    public JTextField passField = new JTextField();
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    JButton okB = new JButton();
    JButton cancelB = new JButton();
    public boolean CANCELLED = true;
    JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel messageLabel = new JLabel();

    public LoginDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
            ex.printStackTrace();
        }
    }

	/**
	 * setup user interface and init dialog
	 */
	 
    void jbInit() throws Exception {
		this.setResizable(false);
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Log In"));
        header.setIcon(new ImageIcon(LoginDialog.class.getResource(
            "/ui/icons/userIcon.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);

        messageLabel.setText(Local.getString("Incorrect Password"));
        messageLabel.setIcon(new ImageIcon(LoginDialog.class.getResource(
                "/ui/icons/pr_high.png")));
        messagePanel.add(messageLabel);
        this.getContentPane().add(messagePanel, BorderLayout.WEST);
        messagePanel.setVisible(false);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 2; gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        jLabel1.setText(Local.getString("Username")+": ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(jLabel1, gbc);
        usernameField.setMinimumSize(new Dimension(4, 24));
        usernameField.setPreferredSize(new Dimension(250, 24));
        usernameField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                usernameField_caretUpdate(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(usernameField, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2; gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        jLabel2.setText(Local.getString("Password")+":  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        areaPanel.add(jLabel2, gbc);
        passField.setMinimumSize(new Dimension(4, 24));
        passField.setPreferredSize(new Dimension(250, 24));
        passField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent e) {
                passField_caretUpdate(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(passField, gbc);
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        okB.setEnabled(false);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okB);
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

	/**
	 * set CANCELLED variable to false so we can know the user 
	 * pressed the ok button and close this dialog.
	 */
	 
    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        boolean disposeFlag = true;

        // Check Account Credentials
        String username = usernameField.getText();
        String password = passField.getText();
        JSONObject account = AccountUtils.readFile(username, AccountUtils.defaultFilename);
        if(account.has(AccountUtils.nullKey) && account.getBoolean(AccountUtils.nullKey)) {  // No account with name
            // TODO: IMPLEMENT ACCOUNT DOES NOT EXIST MESSAGE / CREATE ACCOUNT
            JSONArray data = AccountUtils.readFile(AccountUtils.defaultFilename);

            // Currently the account is automatically created when username does not exist
            JSONObject newAccount = new JSONObject();
            newAccount.put(AccountUtils.usernameKey, username);
            newAccount.put(AccountUtils.passwordKey, password);
            newAccount.put(AccountUtils.permissionKey, "STUDENT");
            data.put(newAccount);

            AccountUtils.writeFile(data, AccountUtils.defaultFilename);
        }
        else {
            // Compare password with account credentials
            String truePassword = account.getString(AccountUtils.passwordKey);
            if(password.equals(truePassword)) {
                // TODO: IMPLEMENT SUCCESSFUL LOGIN
                // Write the current user to a file
                AccountUtils.writeFile(account, AccountUtils.defaultCurrUserFile);
            }
            else {
                // Show incorrect password message
                disposeFlag = false;
                messagePanel.setVisible(true);
                this.setSize((int)(this.getMinimumSize().getWidth() * 2), this.getHeight());
            }
        }
		if(disposeFlag) {
            this.dispose();
        }
    }

	/**
	 * close the dialog window
	 */
	 
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

	/**
	 * disable the ok button if usernameField is empty
	 */
	 
    void usernameField_caretUpdate(CaretEvent e) {
        checkOkEnabled();
    }

	/**
	 * disable the ok button if passField is empty
	 */
	
    void passField_caretUpdate(CaretEvent e) {
        checkOkEnabled();
    }

	/**
	 * do not enable the ok button until the text field is not empty.
	 */

    void checkOkEnabled() {
         okB.setEnabled(
            (usernameField.getText().length() > 0) ||
            (passField.getText().length() > 0)
         );
    }

}