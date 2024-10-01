/**
 * EventsTable.java
 * Created on 09.03.2003, 9:52:02 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import main.java.memoranda.Event;
import main.java.memoranda.EventsManager;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.AccountUtils;
/**
 *
 */
/*$Id: EventsTable.java,v 1.6 2004/10/11 08:48:20 alexeya Exp $*/
public class EventsTable extends JTable {

    public static final int EVENT = 100;
    public static final int EVENT_ID = 101;

    Vector events = new Vector();
    /**
     * Constructor for EventsTable.
     */
    public EventsTable() {
        super();
        setModel(new EventsTableModel());
        AccountUtils.Rank currentUserRank = AccountUtils.readRank(AccountUtils.defaultCurrUserFile); // Fetch the current user rank
        initTable(CurrentDate.get(), currentUserRank);
        this.setShowGrid(false);
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                AccountUtils.Rank updatedUserRank = AccountUtils.readRank(AccountUtils.defaultCurrUserFile); // Fetch the updated user rank
                initTable(d, updatedUserRank);
            }
        });
    }

    public void initTable(CalendarDate d, AccountUtils.Rank currentUserRank) {
        events = (Vector)EventsManager.getEventsForDate(d, currentUserRank);
        getColumnModel().getColumn(0).setPreferredWidth(60);  // column is for time
        getColumnModel().getColumn(0).setMaxWidth(60);
        getColumnModel().getColumn(1).setPreferredWidth(200);  // column for text
        getColumnModel().getColumn(1).setMaxWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(200);  // column for topic
        getColumnModel().getColumn(2).setMaxWidth(200);
        getColumnModel().getColumn(3).setPreferredWidth(100);  // column for visibility
        getColumnModel().getColumn(3).setMaxWidth(100);
	clearSelection();
        updateUI();
    }

    public void refresh() {
        AccountUtils.Rank currentUserRank = AccountUtils.readRank(AccountUtils.defaultCurrUserFile); // Fetch the current user rank
        initTable(CurrentDate.get(), currentUserRank);
    }

     public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                Component comp;
                comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Event ev = (Event)getModel().getValueAt(row, EVENT);
                comp.setForeground(java.awt.Color.gray);
                if (ev.isRepeatable())
                    comp.setFont(comp.getFont().deriveFont(Font.ITALIC));
                if (CurrentDate.get().after(CalendarDate.today())) {
                  comp.setForeground(java.awt.Color.black);
                }                
                else if (CurrentDate.get().equals(CalendarDate.today())) {
                  if (ev.getTime().after(new Date())) {
                    comp.setForeground(java.awt.Color.black);
                    //comp.setFont(new java.awt.Font("Dialog", 1, 12));
                    comp.setFont(comp.getFont().deriveFont(Font.BOLD));
                  }
                }
                return comp;
            }
        };

    }

    class EventsTableModel extends AbstractTableModel {

        String[] columnNames = {
            //Local.getString("Task name"),
            Local.getString("Time"),
                Local.getString("Text"),
                Local.getString("Topic"),
                Local.getString("Visibility"),
                Local.getString("Resource")
        };

        EventsTableModel() {
            super();
        }

        public int getColumnCount() {
            return 5;
        }

        public int getRowCount() {
			int i;
			try {
				i = events.size();
			}
			catch(NullPointerException e) {
				i = 1;
			}
			return i;
        }

        public Object getValueAt(int row, int col) {
            Event ev = (Event)events.get(row);
            AccountUtils.Rank evVisibility = ev.getVisibility();
            AccountUtils.Rank userRank = AccountUtils.readRank(AccountUtils.defaultCurrUserFile);
            if (!AccountUtils.hasPermission(userRank, evVisibility)) {
                if (col == 0 || col == 1 || col == 2) {
                    return "";
                } else if (col == 3) {
                    return evVisibility;
                } else if (col == 4) {
                    return null;
                } else if (col == EVENT_ID) {
                    return ev.getId();
                } else {
                    return ev;
                }
            }
            if (col == 0)
                //return ev.getHour()+":"+ev.getMinute();
                return ev.getTimeString();
            else if (col == 1)
                return ev.getText();
                // If the current column is 2 then this conditional will return
                // the topic of the event using ev.getTopic()
            else if (col == 2)
                return ev.getTopic();

            else if (col == 3)
                return ev.getVisibility();
            else if (col == 4){

                File file = ev.getFile();
                return (file != null) ? file.getName() : null;
            }
            else if (col == EVENT_ID)
                return ev.getId();
            else return ev;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }
    }

}
