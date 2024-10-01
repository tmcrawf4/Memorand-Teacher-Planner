package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import main.java.memoranda.util.Local;

/*$Id: LoginPanel.java,v 1.0 2024/01/22 jrfeathe Exp $*/
public class LoginPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar toolBar = new JToolBar();
    JButton loginB = new JButton();
    ResourcesTable resourcesTable = new ResourcesTable();
    JScrollPane scrollPane = new JScrollPane();

    public LoginPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        toolBar.setFloatable(false);
        this.setLayout(borderLayout1);
        loginB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));
        loginB.setEnabled(true);
        loginB.setMaximumSize(new Dimension(24, 24));
        loginB.setMinimumSize(new Dimension(24, 24));
        loginB.setToolTipText(Local.getString("Log In"));
        loginB.setRequestFocusEnabled(false);
        loginB.setPreferredSize(new Dimension(24, 24));
        loginB.setFocusable(false);
        loginB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              loginB_actionPerformed(e);
            }
        });
        loginB.setBorderPainted(false);
        resourcesTable.setMaximumSize(new Dimension(32767, 32767));
        resourcesTable.setRowHeight(24);

        scrollPane.getViewport().setBackground(Color.white);
        toolBar.addSeparator(new Dimension(8, 24));
        toolBar.addSeparator(new Dimension(8, 24));

        toolBar.add(loginB, null);
        this.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getViewport().add(resourcesTable, null);
        this.add(toolBar, BorderLayout.NORTH);

    }

    void loginB_actionPerformed(ActionEvent e) {
        LoginDialog dlg = new LoginDialog(App.getFrame(), Local.getString("Log In"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
    }

}