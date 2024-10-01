package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.EventNotificationListener;
import main.java.memoranda.EventsManager;
import main.java.memoranda.EventsScheduler;
import main.java.memoranda.History;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.AgendaGenerator;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;

import nu.xom.Element;

/*$Id: AgendaPanel.java,v 1.11 2005/02/15 16:58:02 rawsushi Exp $*/
public class AgendaPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JButton historyBackB = new JButton();
	JToolBar toolBar = new JToolBar();
	JButton historyForwardB = new JButton();
	JButton export = new JButton();
	JEditorPane viewer = new JEditorPane("text/html", "");
	// Translations added to this file for the priorities muy alta, alta, media, baja and muy baja
	String[] priorities = {"Very High","High","Half","Low","Very Low"};
	JScrollPane scrollPane = new JScrollPane();
	JButton addAssignmentButton = new JButton("Add Assignment");
	JButton viewAssignmentsButton = new JButton("Assignments");
	private List<String> assignments = new ArrayList<>();

	DailyItemsPanel parentPanel = null;

	//	JPopupMenu agendaPPMenu = new JPopupMenu();
	//	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();

	Collection expandedTasks;
	String gotoTask = null;

	boolean isActive = true;

	public AgendaPanel(DailyItemsPanel _parentPanel) {
		try {
			parentPanel = _parentPanel;
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
			ex.printStackTrace();
		}
	}
	void jbInit() throws Exception {
		expandedTasks = new ArrayList();

		toolBar.setFloatable(false);
		viewer.setEditable(false);
		viewer.setOpaque(false);

		addAssignmentButton.addActionListener(e -> openAddAssignmentDialog());
		toolBar.add(addAssignmentButton);

		viewAssignmentsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAssignmentsView();
			}
		});
		toolBar.add(viewAssignmentsButton);

		viewer.addHyperlinkListener(new HyperlinkListener() {

			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					String d = e.getDescription();
					if (d.equalsIgnoreCase("memoranda:events"))
						parentPanel.alarmB_actionPerformed(null);
					else if (d.startsWith("memoranda:tasks")) {
						String id = d.split("#")[1];
						CurrentProject.set(ProjectManager.getProject(id));
						parentPanel.taskB_actionPerformed(null);
					} else if (d.startsWith("memoranda:course")) {
						String id = d.split("#")[1];
						CurrentProject.set(ProjectManager.getProject(id));
					} else if (d.startsWith("memoranda:removesticker")) {
                        String id = d.split("#")[1];
                        StickerConfirmation stc = new StickerConfirmation(App.getFrame());
                        Dimension frmSize = App.getFrame().getSize();
                        stc.setSize(new Dimension(300,180));
                        Point loc = App.getFrame().getLocation();
                        stc.setLocation(
                                (frmSize.width - stc.getSize().width) / 2 + loc.x,
                                (frmSize.height - stc.getSize().height) / 2
                                        + loc.y);
                        stc.setVisible(true);
                        if (!stc.CANCELLED) {
                        EventsManager.removeSticker(id);
                        CurrentStorage.get().storeEventsManager();}
                        refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:addsticker")) {
						StickerDialog dlg = new StickerDialog(App.getFrame());
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300,380));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation(
								(frmSize.width - dlg.getSize().width) / 2 + loc.x,
								(frmSize.height - dlg.getSize().height) / 2
								+ loc.y);
						dlg.setVisible(true);
						if (!dlg.CANCELLED) {
							String txt = dlg.getStickerText();
							int sP = dlg.getPriority();
							txt = txt.replaceAll("\\n", "<br>");
                            txt = "<div style=\"background-color:"+dlg.getStickerColor()+";font-size:"+dlg.getStickerTextSize()+";color:"+dlg.getStickerTextColor()+"; \">"+txt+"</div>";
							EventsManager.createSticker(txt, sP);
							CurrentStorage.get().storeEventsManager();
						}
						refresh(CurrentDate.get());
						//First change: changed agregué un sticker to it's english translation.
						System.out.println("I added a sticker");
					} else if (d.startsWith("memoranda:expandsubtasks")) {
						String id = d.split("#")[1];
						gotoTask = id;
						expandedTasks.add(id);
						refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:closesubtasks")) {
						String id = d.split("#")[1];
						gotoTask = id;
						expandedTasks.remove(id);
						refresh(CurrentDate.get());
					} else if (d.startsWith("memoranda:expandsticker")) {
						String id = d.split("#")[1];
						Element pre_sticker=(Element)((Map)EventsManager.getStickers()).get(id);
						String sticker = pre_sticker.getValue();
						int first=sticker.indexOf(">");
						int last=sticker.lastIndexOf("<");
						int backcolor=sticker.indexOf("#");
						int fontcolor=sticker.indexOf("#", backcolor+1);
						int sP=Integer.parseInt(pre_sticker.getAttributeValue("priority"));
						String backGroundColor=sticker.substring(backcolor, sticker.indexOf(';',backcolor));
						String foreGroundColor=sticker.substring(fontcolor, sticker.indexOf(';',fontcolor));
						sticker="<html>"+sticker.substring(first+1, last)+"</html>";
						StickerExpand dlg = new StickerExpand(App.getFrame(),sticker,backGroundColor,foreGroundColor,Local.getString("priority")+": "+Local.getString(priorities[sP]));
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300,200));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation(
								(frmSize.width - dlg.getSize().width) / 2 + loc.x,
								(frmSize.height - dlg.getSize().height) / 2
								+ loc.y);
						dlg.stickerText.setText(sticker);
						dlg.setVisible(true);
					}else if (d.startsWith("memoranda:editsticker")) {
						String id = d.split("#")[1];
						Element pre_sticker=(Element)((Map)EventsManager.getStickers()).get(id);
						String sticker = pre_sticker.getValue();
						sticker=sticker.replaceAll("<br>","\n");
						int first=sticker.indexOf(">");
						int last=sticker.lastIndexOf("<");
						int backcolor=sticker.indexOf("#");
						int fontcolor=sticker.indexOf("#", backcolor+1);
						int sizeposition=sticker.indexOf("font-size")+10;
						int size=Integer.parseInt(sticker.substring(sizeposition,sizeposition+2));
						System.out.println(size+" "+sizeposition);
						int sP=Integer.parseInt(pre_sticker.getAttributeValue("priority"));
						String backGroundColor=sticker.substring(backcolor, sticker.indexOf(';',backcolor));
						String foreGroundColor=sticker.substring(fontcolor, sticker.indexOf(';',fontcolor));
						StickerDialog dlg = new StickerDialog(App.getFrame(), sticker.substring(first+1, last), backGroundColor, foreGroundColor, sP, size);
						Dimension frmSize = App.getFrame().getSize();
						dlg.setSize(new Dimension(300,380));
						Point loc = App.getFrame().getLocation();
						dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
							 		(frmSize.height - dlg.getSize().height) / 2 + loc.y);
						dlg.setVisible(true);
						if (!dlg.CANCELLED) {
							String txt = dlg.getStickerText();
							sP = dlg.getPriority();
							txt = txt.replaceAll("\\n", "<br>");
							txt = "<div style=\"background-color:"+dlg.getStickerColor()+";font-size:"+dlg.getStickerTextSize()+";color:"+dlg.getStickerTextColor()+";\">"+txt+"</div>";
							EventsManager.removeSticker(id);
							EventsManager.createSticker(txt, sP);
							CurrentStorage.get().storeEventsManager();
						 }
						 refresh(CurrentDate.get());
					}else if (d.startsWith("memoranda:exportstickerst")) {
						 /*  Falta agregar el exportar sticker mientras tanto..*/
						 final JFrame parent = new JFrame();
						 String name = JOptionPane.showInputDialog(parent,Local.getString("Enter file name to export"),null);
						 new ExportSticker(name).export("txt");
						 //JOptionPane.showMessageDialog(null,name);
					}else if (d.startsWith("memoranda:exportstickersh")) {
						 /*  Falta agregar el exportar sticker mientras tanto..*/
						 final JFrame parent = new JFrame();
						 String name = JOptionPane.showInputDialog(parent,Local.getString("Enter file name to export"),null);
						 new ExportSticker(name).export("html");
						 //JOptionPane.showMessageDialog(null,name);
					}else if (d.startsWith("memoranda:importstickers")) {
						final JFrame parent = new JFrame();
						String name = JOptionPane.showInputDialog(parent,Local.getString("Enter file name to import"),null);
						new ImportSticker(name).import_file();
					}
				}
			}
		});
		historyBackB.setAction(History.historyBackAction);
		historyBackB.setFocusable(false);
		historyBackB.setBorderPainted(false);
		historyBackB.setToolTipText(Local.getString("History back"));
		historyBackB.setRequestFocusEnabled(false);
		historyBackB.setPreferredSize(new Dimension(24, 24));
		historyBackB.setMinimumSize(new Dimension(24, 24));
		historyBackB.setMaximumSize(new Dimension(24, 24));
		historyBackB.setText("");

		historyForwardB.setAction(History.historyForwardAction);
		historyForwardB.setBorderPainted(false);
		historyForwardB.setFocusable(false);
		historyForwardB.setPreferredSize(new Dimension(24, 24));
		historyForwardB.setRequestFocusEnabled(false);
		historyForwardB.setToolTipText(Local.getString("History forward"));
		historyForwardB.setMinimumSize(new Dimension(24, 24));
		historyForwardB.setMaximumSize(new Dimension(24, 24));
		historyForwardB.setText("");

		this.setLayout(borderLayout1);
		scrollPane.getViewport().setBackground(Color.white);

		scrollPane.getViewport().add(viewer, null);
		this.add(scrollPane, BorderLayout.CENTER);
		toolBar.add(historyBackB, null);
		toolBar.add(historyForwardB, null);
		toolBar.addSeparator(new Dimension(8, 24));

		this.add(toolBar, BorderLayout.NORTH);

		CurrentDate.addDateListener(new DateListener() {
			public void dateChange(CalendarDate d) {
				if (isActive)
					refresh(d);
			}
		});

		CurrentProject.addProjectListener(new ProjectListener() {

			public void projectChange(
					Project prj,
					NoteList nl,
					TaskList tl,
					ResourcesList rl) {
			}

			public void projectWasChanged() {
				if (isActive)
					refresh(CurrentDate.get());
			}});
		EventsScheduler.addListener(new EventNotificationListener() {
			public void eventIsOccured(main.java.memoranda.Event ev) {
				if (isActive)
					refresh(CurrentDate.get());
			}

			public void eventsChanged() {
				if (isActive)
					refresh(CurrentDate.get());
			}
		});
		refresh(CurrentDate.get());

		//        agendaPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
		//        agendaPPMenu.add(ppShowActiveOnlyChB);
		//        PopupListener ppListener = new PopupListener();
		//        viewer.addMouseListener(ppListener);
		//		ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		//		ppShowActiveOnlyChB.setText(
		//			Local.getString("Show Active only"));
		//		ppShowActiveOnlyChB.addActionListener(new java.awt.event.ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				toggleShowActiveOnly_actionPerformed(e);
		//			}
		//		});		
		//		boolean isShao =
		//			(Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
		//				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		//		ppShowActiveOnlyChB.setSelected(isShao);
		//		toggleShowActiveOnly_actionPerformed(null);		
	}

	public void refresh(CalendarDate date) {
		viewer.setText(AgendaGenerator.getAgenda(date,expandedTasks));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(gotoTask != null) {
					viewer.scrollToReference(gotoTask);
					scrollPane.setViewportView(viewer);
					Util.debug("Set view port to " + gotoTask);
				}
			}
		});

		Util.debug("Summary updated.");
	}
	private void openAddAssignmentDialog() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Add New Assignment");
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 4, 4, 4);

		JLabel nameLabel = new JLabel("Assignment Name:");
		JTextField nameField = new JTextField(20);
		dialog.add(nameLabel, gbc);
		dialog.add(nameField, gbc);

		JLabel courseLabel = new JLabel("Select Courses:");
		Vector<Project> courses = ProjectManager.getActiveProjects();
		CourseTableModel tableModel = new CourseTableModel(courses);
		JTable courseTable = new JTable(tableModel);
		JScrollPane courseScrollPane = new JScrollPane(courseTable);
		courseScrollPane.setPreferredSize(new Dimension(250, 80));
		dialog.add(courseLabel, gbc);
		dialog.add(courseScrollPane, gbc);

		JPanel buttonPanel = new JPanel();
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");

		saveButton.addActionListener(e -> {
			String assignmentName = nameField.getText().trim();
			if (!assignmentName.isEmpty()) {
				assignments.add(assignmentName);
				dialog.dispose();
				refreshAssignmentsView();
			} else {
				JOptionPane.showMessageDialog(dialog, "Please enter an assignment name.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelButton.addActionListener(e -> dialog.dispose());
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		dialog.add(buttonPanel, gbc);

		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	class CourseTableModel extends AbstractTableModel {
		private final String[] columnNames = {"Select", "Course Name"};
		private final Class[] columnClasses = {Boolean.class, String.class};
		private Vector<Project> courses;
		private Vector<Boolean> selections;

		public CourseTableModel(Vector<Project> courses) {
			this.courses = courses;
			selections = new Vector<>(courses.size());
			for (int i = 0; i < courses.size(); i++) {
				selections.add(Boolean.FALSE);
			}
		}

		@Override
		public int getRowCount() {
			return courses.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return selections.get(rowIndex);
			} else if (columnIndex == 1) {
				return courses.get(rowIndex).getTitle();
			}
			return null;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return columnClasses[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 0;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex == 0 && aValue instanceof Boolean) {
				selections.set(rowIndex, (Boolean) aValue);
				fireTableCellUpdated(rowIndex, columnIndex);
			}
		}

		public Vector<Project> getSelectedCourses() {
			Vector<Project> selectedCourses = new Vector<>();
			for (int i = 0; i < selections.size(); i++) {
				if (selections.get(i)) {
					selectedCourses.add(courses.get(i));
				}
			}
			return selectedCourses;
		}
	}
	private void openAssignmentsView() {
		JDialog assignmentsDialog = new JDialog();
		assignmentsDialog.setTitle("Assignments");
		assignmentsDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 4, 4, 4);

		DefaultListModel<String> model = new DefaultListModel<>();
		assignments.forEach(model::addElement);
		JList<String> assignmentsList = new JList<>(model);
		assignmentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane assignmentsScrollPane = new JScrollPane(assignmentsList);
		assignmentsScrollPane.setPreferredSize(new Dimension(250, 80));
		assignmentsDialog.add(new JLabel("Select Assignments:"), gbc);
		assignmentsDialog.add(assignmentsScrollPane, gbc);

		JLabel courseLabel = new JLabel("Select Courses to Assign:");
		Vector<Project> courses = ProjectManager.getActiveProjects();
		CourseTableModel tableModel = new CourseTableModel(courses);
		JTable courseTable = new JTable(tableModel);
		JScrollPane courseScrollPane = new JScrollPane(courseTable);
		courseScrollPane.setPreferredSize(new Dimension(250, 80));
		assignmentsDialog.add(courseLabel, gbc);
		assignmentsDialog.add(courseScrollPane, gbc);

		JPanel buttonPanel = new JPanel();
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");

		saveButton.addActionListener(e -> {
			List<String> selectedAssignments = assignmentsList.getSelectedValuesList();
			Vector<Project> selectedCourses = tableModel.getSelectedCourses();


			if (!selectedAssignments.isEmpty() && !selectedCourses.isEmpty()) {
				JOptionPane.showMessageDialog(assignmentsDialog, "Assignments added to selected courses.", "Success", JOptionPane.INFORMATION_MESSAGE);
				assignmentsDialog.dispose();
			} else {
				JOptionPane.showMessageDialog(assignmentsDialog, "Please select at least one assignment and one course.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelButton.addActionListener(e -> assignmentsDialog.dispose());
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		assignmentsDialog.add(buttonPanel, gbc);

		assignmentsDialog.pack();
		assignmentsDialog.setLocationRelativeTo(null);
		assignmentsDialog.setVisible(true);
	}

	private void refreshAssignmentsView() {
	}

		public void setActive(boolean isa) {
		isActive = isa;
	}

	//	void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
	//		Context.put(
	//			"SHOW_ACTIVE_TASKS_ONLY",
	//			new Boolean(ppShowActiveOnlyChB.isSelected()));
	//		/*if (taskTable.isShowActiveOnly()) {
	//			// is true, toggle to false
	//			taskTable.setShowActiveOnly(false);
	//			//showActiveOnly.setToolTipText(Local.getString("Show Active Only"));			
	//		}
	//		else {
	//			// is false, toggle to true
	//			taskTable.setShowActiveOnly(true);
	//			showActiveOnly.setToolTipText(Local.getString("Show All"));			
	//		}*/	    
	//		refresh(CurrentDate.get());
	////		parentPanel.updateIndicators();
	//		//taskTable.updateUI();
	//	}

	//    class PopupListener extends MouseAdapter {
	//
	//        public void mouseClicked(MouseEvent e) {
	//        	System.out.println("mouse clicked!");
	////			if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1))
	////				editTaskB_actionPerformed(null);
	//		}
	//
	//		public void mousePressed(MouseEvent e) {
	//        	System.out.println("mouse pressed!");
	//			maybeShowPopup(e);
	//		}
	//
	//		public void mouseReleased(MouseEvent e) {
	//        	System.out.println("mouse released!");
	//			maybeShowPopup(e);
	//		}
	//
	//		private void maybeShowPopup(MouseEvent e) {
	//			if (e.isPopupTrigger()) {
	//				agendaPPMenu.show(e.getComponent(), e.getX(), e.getY());
	//			}
	//		}
	//
	//    }
}
