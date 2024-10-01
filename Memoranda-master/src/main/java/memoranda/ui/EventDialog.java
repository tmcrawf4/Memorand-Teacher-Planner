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
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Component;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Local;
import main.java.memoranda.Resource;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.ResourcesListImpl;
import main.java.memoranda.ui.ResourcesTable;
import main.java.memoranda.util.AccountUtils;

/*$Id: EventDialog.java,v 1.28 2005/02/19 10:06:25 rawsushi Exp $*/
public class EventDialog extends JDialog implements WindowListener {
    public boolean CANCELLED = false;
    boolean ignoreStartChanged = false;
    boolean ignoreEndChanged = false;
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    public JLabel header = new JLabel();
    JPanel eventPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    JLabel lblTime = new JLabel();
    public JSpinner timeSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    JLabel lblText = new JLabel();
    JLabel lblOr = new JLabel();

    JLabel lblTopic = new JLabel(); // Addition of a new label to take input for a topic.
    private JLabel resourceLabel;
    private ResourcesList resourcesList;
    private JLabel selectedResourceLabel = new JLabel("");

    public JTextField textField = new JTextField();
    public JTextField topicField = new JTextField();

    String[] visibility = {Local.getString("Student"), Local.getString("Grader"),

        Local.getString("TA"), Local.getString("Instructor")};

    JPanel jPanelVisibility = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JComboBox<String> visibilityCB = new JComboBox<>(visibility);

    String[] quickFill = {Local.getString("Other"), Local.getString("Holiday"),

        Local.getString("Free Day"), Local.getString("Lecture"), Local.getString("Exam")};

    JPanel jPanelQuickFill = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JComboBox<String> quickFillCB = new JComboBox<>(quickFill);
    JLabel jLabelQuickFill = new JLabel(Local.getString("Topic Quick Options"));
    private AccountUtils.Rank visibilityChoice = AccountUtils.Rank.STUDENT;
    private JFileChooser fileChooser; // Declare JFileChooser
    private File selectedFile; // To hold the selected file
    JLabel selectedFileLabel = new JLabel();
    TitledBorder repeatBorder;
    JPanel repeatPanel = new JPanel(new GridBagLayout());
    public JRadioButton noRepeatRB = new JRadioButton();
    public JRadioButton dailyRepeatRB = new JRadioButton();
    public JSpinner daySpin = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
    JLabel lblDays = new JLabel();
    JLabel lblSince = new JLabel();
    public JSpinner startDate = new JSpinner(new SpinnerDateModel());
    JButton setStartDateB = new JButton();
    public JRadioButton weeklyRepeatRB = new JRadioButton();
    public JComboBox weekdaysCB = new JComboBox(Local.getWeekdayNames());
    public JCheckBox enableEndDateCB = new JCheckBox();
    public JCheckBox workingDaysOnlyCB = new JCheckBox();
    public JSpinner endDate = new JSpinner(new SpinnerDateModel());
    JButton setEndDateB = new JButton();
    public JRadioButton monthlyRepeatRB = new JRadioButton();
    public JSpinner dayOfMonthSpin = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    JLabel lblDoM = new JLabel();
    public JRadioButton yearlyRepeatRB = new JRadioButton();
    ButtonGroup repeatRBGroup = new ButtonGroup();
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    JButton okB = new JButton();
    JButton cancelB = new JButton();

    CalendarFrame endCalFrame = new CalendarFrame();
    CalendarFrame startCalFrame = new CalendarFrame();
    private Date eventDate;

    public EventDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            resourceLabel = new JLabel();
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
        super.addWindowListener(this);
    }

    public EventDialog(Frame frame, String title, ResourcesList resourcesList) {
        this(frame, title);
        this.resourcesList = resourcesList;
    }

    void jbInit() throws Exception {
        this.setResizable(false);
        // Build headerPanel
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Event"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.EventDialog.class.getResource(
                "/ui/icons/event48.png")));
        headerPanel.add(header);

        // Build eventPanel
        lblTime.setText(Local.getString("Time"));
        lblTime.setMinimumSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblTime, gbc);
        timeSpin.setPreferredSize(new Dimension(60, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(timeSpin, gbc);


        lblText.setText(Local.getString("Text"));
        lblText.setMinimumSize(new Dimension(120, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblText, gbc);
        textField.setMinimumSize(new Dimension(375, 24));
        textField.setPreferredSize(new Dimension(375, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(textField, gbc);
        //added implementation to quickly add holidays, lectures, free days, exams as events
        JPanel jPanelQuickFill = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanelQuickFill.add(jLabelQuickFill);
        jPanelQuickFill.add(quickFillCB);
        gbc = new GridBagConstraints();
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(jPanelQuickFill, gbc);
        quickFillCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) quickFillCB.getSelectedItem();
                if (selectedOption != null) {
                    if (selectedOption.equals("Other")) {
                        topicField.setText("");
                    } else if (!selectedOption.equals("None")) {
                        topicField.setText(selectedOption);
                    }
                }
            }
        });

        lblTopic = new JLabel(Local.getString("Topic"));
        lblTopic.setMinimumSize(new Dimension(120, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(lblTopic, gbc);
        // Added implementation of a text field into the event dialog window
        // and set the correct dimension for it to fit in the window
        topicField.setMinimumSize(new Dimension(375, 24));
        topicField.setPreferredSize(new Dimension(375, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        // inset field for the gbc object
        gbc.insets = new Insets(5, 10, 10, 10);
        // anchor that sets where the topicField is displayed
        gbc.anchor = GridBagConstraints.WEST;
        // The field will then resize itself to fit the window horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(topicField, gbc);

        JLabel jLabelVisibility = new JLabel("Visibility");
        jPanelVisibility.add(jLabelVisibility);
        visibilityCB.setSelectedItem(Local.getString("Student"));

        visibilityCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String visChoice = (String)visibilityCB.getSelectedItem();
                visibilityChoice = AccountUtils.toEnum(visChoice);
            }
        });

        jPanelVisibility.add(visibilityCB);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(jPanelVisibility, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(resourceLabel, gbc);
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 9;

        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(selectedFileLabel, gbc);

        //resource panel builder
        ResourcesTable resourcesTable = new ResourcesTable(); // Initialization
        JTextField selectedFileField = new JTextField(30);
        selectedFileField.setEditable(false);
        JButton resourceFilesButton = new JButton("List Resource Files");
        resourceFilesButton.addActionListener(e -> {
            Vector<Resource> resources = resourcesTable.getResourceFiles();
            JList<Resource> resourceList = new JList<>(resources);
            resourceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            resourceList.setVisibleRowCount(5); // set the number of visible rows
            resourceList.setCellRenderer(new DefaultListCellRenderer() {
                //Overrides this method to display only the file names for the list
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (c instanceof JLabel && value instanceof Resource) {
                        // Set the text of the JLabel to the file name
                        ((JLabel) c).setText(((Resource) value).getPath());
                    }
                    return c;
                }
            });

            // Create a new scroll pane object
            JScrollPane scrollPane = new JScrollPane(resourceList);
            // Createe the message dialog to show the contents of Resources
            JOptionPane.showMessageDialog(null, scrollPane);

            // Acquires the file selected by the user
            Resource selectedResource = resourceList.getSelectedValue();

            // If a file is selected display the file path name
            if (selectedResource != null) {
                selectedFileField.setText(selectedResource.getPath());
                // if not return a message saying there was no file
            } else {
                selectedFileField.setText("No resource selected");
            }
        });

// Add button and selected file JTextField to your panel
        eventPanel.add(resourceFilesButton);
        eventPanel.add(selectedFileField);

        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 4;

        gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        eventPanel.add(resourceFilesButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 7;

        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        eventPanel.add(resourceFilesButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 8;

        gbc.gridwidth = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        eventPanel.add(selectedResourceLabel, gbc);

        // Build RepeatPanel
        repeatBorder = new TitledBorder(BorderFactory.createLineBorder(

            Color.gray, 1), Local.getString("Repeat"));

        repeatPanel.setBorder(repeatBorder);
        noRepeatRB.setMaximumSize(new Dimension(80, 35));
        noRepeatRB.setSelected(true);
        noRepeatRB.setText(Local.getString("No repeat"));
        noRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        repeatPanel.add(noRepeatRB, gbc);
        dailyRepeatRB.setActionCommand("daily");
        dailyRepeatRB.setText(Local.getString("Every"));
        dailyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dailyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(dailyRepeatRB, gbc);
        daySpin.setPreferredSize(new Dimension(50, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(daySpin, gbc);
        lblDays.setText(Local.getString("day(s)"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 40);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(lblDays, gbc);
        lblSince.setText(Local.getString("Since"));
        lblSince.setMinimumSize(new Dimension(70, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(lblSince, gbc);
        startDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreStartChanged)
                    return;
                ignoreStartChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                // Commented out, value was resetted to endDate !!!
                if (sd.after(ed)) {
                    endDate.getModel().setValue(sd);
                    ed = sd;
                }
                startCalFrame.cal.set(new CalendarDate(sd));
                ignoreStartChanged = false;
            }
        });
        startDate.setPreferredSize(new Dimension(80, 24));

        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
        startDate.setEditor(new JSpinner.DateEditor(startDate,

            sdf.toPattern()));

        //---------------------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(startDate, gbc);
        setStartDateB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartDateB_actionPerformed(e);
            }
        });
        setStartDateB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/calendar.png")));
        setStartDateB.setText("");
        setStartDateB.setPreferredSize(new Dimension(24, 24));

        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(setStartDateB, gbc);
        weeklyRepeatRB.setActionCommand("weekly");
        weeklyRepeatRB.setText(Local.getString("Every"));
        weeklyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                weeklyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(weeklyRepeatRB, gbc);
        weekdaysCB.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 40);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(weekdaysCB, gbc);
        enableEndDateCB.setHorizontalAlignment(SwingConstants.RIGHT);
        enableEndDateCB.setText(Local.getString("Till"));
        enableEndDateCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableEndDateCB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(enableEndDateCB, gbc);
        endDate.setPreferredSize(new Dimension(80, 24));
        //Added by (jcscoobyrs) on 12-Nov-2003 at 15:34:27 PM
        //---------------------------------------------------
        endDate.setEditor(new JSpinner.DateEditor(endDate, sdf.toPattern()));
        //---------------------------------------------------
        endDate.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ignoreEndChanged)
                    return;
                ignoreEndChanged = true;
                Date sd = (Date) startDate.getModel().getValue();
                Date ed = (Date) endDate.getModel().getValue();
                if (sd.after(ed)) {
                    endDate.getModel().setValue(sd);
                    ed = sd;
                }
                endCalFrame.cal.set(new CalendarDate(ed));
                ignoreEndChanged = false;
            }
        });
        // working days
        workingDaysOnlyCB.setText(Local.getString("Working days only"));
        workingDaysOnlyCB.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        repeatPanel.add(workingDaysOnlyCB, gbc);
        // -------------------------------------
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(endDate, gbc);
        setEndDateB.setMinimumSize(new Dimension(24, 24));
        setEndDateB.setPreferredSize(new Dimension(24, 24));
        setEndDateB.setText("");
        setEndDateB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/calendar.png")));
        setEndDateB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEndDateB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(setEndDateB, gbc);
        monthlyRepeatRB.setActionCommand("daily");
        monthlyRepeatRB.setText(Local.getString("Every"));
        monthlyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monthlyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(monthlyRepeatRB, gbc);
        dayOfMonthSpin.setPreferredSize(new Dimension(50, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(dayOfMonthSpin, gbc);
        lblDoM.setText(Local.getString("day of month"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(lblDoM, gbc);
        yearlyRepeatRB.setActionCommand("yearly");
        yearlyRepeatRB.setText(Local.getString("Yearly"));
        yearlyRepeatRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yearlyRepeatRB_actionPerformed(e);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        repeatPanel.add(yearlyRepeatRB, gbc);

        repeatRBGroup.add(noRepeatRB);
        repeatRBGroup.add(dailyRepeatRB);
        repeatRBGroup.add(weeklyRepeatRB);
        repeatRBGroup.add(monthlyRepeatRB);
        repeatRBGroup.add(yearlyRepeatRB);

        // Build ButtonsPanel
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
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);

        // Finally build the Dialog
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(eventPanel, BorderLayout.SOUTH);
        bottomPanel.add(repeatPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Do final things...
        startCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreStartChanged) return;
                startDate.getModel().setValue(startCalFrame.cal.get().getCalendar().getTime());
            }
        });
        endCalFrame.cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ignoreEndChanged)
                    return;
                endDate.getModel().setValue(endCalFrame.cal.get().getCalendar().getTime());
            }
        });
        disableElements();
        ((JSpinner.DateEditor) timeSpin.getEditor()).getFormat().applyPattern("HH:mm");
        enableEndDateCB_actionPerformed(null);

    }

    void disableElements() {
        dayOfMonthSpin.setEnabled(false);
        daySpin.setEnabled(false);
        weekdaysCB.setEnabled(false);
        startDate.setEnabled(false);
        setStartDateB.setEnabled(false);
        lblSince.setEnabled(false);
        endDate.setEnabled(false);
        setEndDateB.setEnabled(false);
        enableEndDateCB.setEnabled(false);
        enableEndDateCB.setSelected(false);
        workingDaysOnlyCB.setEnabled(false);
        workingDaysOnlyCB.setSelected(false);
    }

    public void yearlyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
        workingDaysOnlyCB.setEnabled(true);
        startDate.getModel().setValue(

            startCalFrame.cal.get().getCalendar().getTime());

    }

    public void monthlyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        dayOfMonthSpin.setEnabled(true);
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
        workingDaysOnlyCB.setEnabled(true);
        startDate.getModel().setValue(

            startCalFrame.cal.get().getCalendar().getTime());

    }

    public void dailyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        daySpin.setEnabled(true);
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
        workingDaysOnlyCB.setEnabled(true);
        startDate.getModel().setValue(

            startCalFrame.cal.get().getCalendar().getTime());

    }

    public void weeklyRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
        weekdaysCB.setEnabled(true);
        startDate.setEnabled(true);
        setStartDateB.setEnabled(true);
        lblSince.setEnabled(true);
        enableEndDateCB.setEnabled(true);
        startDate.getModel().setValue(

            startCalFrame.cal.get().getCalendar().getTime());

    }

    public void noRepeatRB_actionPerformed(ActionEvent e) {
        disableElements();
    }

    void okB_actionPerformed(ActionEvent e) {
        this.dispose();
    }


    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    void setStartDateB_actionPerformed(ActionEvent e) {
        //startCalFrame.setLocation(setStartDateB.getLocation());
        startCalFrame.setSize(200, 190);
        startCalFrame.setTitle(Local.getString("Start date"));
        this.getLayeredPane().add(startCalFrame);
        startCalFrame.show();
    }

    void setEndDateB_actionPerformed(ActionEvent e) {
        //endCalFrame.setLocation(setEndDateB.getLocation());
        endCalFrame.setSize(200, 190);
        endCalFrame.setTitle(Local.getString("End date"));
        this.getLayeredPane().add(endCalFrame);
        endCalFrame.show();
    }

    public void enableEndDateCB_actionPerformed(ActionEvent e) {
        endDate.setEnabled(enableEndDateCB.isSelected());
        setEndDateB.setEnabled(enableEndDateCB.isSelected());
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    public void setEventDate(Date d) {
        eventDate = d;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public File getFile() {
        return selectedFile;
    }

    public AccountUtils.Rank getVisibilityChoice() {
        return this.visibilityChoice;
    }
    public void setSelectedVisibility(AccountUtils.Rank rank){
        switch (rank){
            default:
            case STUDENT:
                visibilityCB.setSelectedItem(Local.getString("Student"));
                break;
            case GRADER:
                visibilityCB.setSelectedItem(Local.getString("Grader"));
                break;
            case TA:
                visibilityCB.setSelectedItem(Local.getString("TA"));
                break;
            case INSTRUCTOR:
                visibilityCB.setSelectedItem(Local.getString("Instructor"));
                break;
        }
    }
}

