import static org.junit.Assert.*;

import main.java.memoranda.EventsManager;
import main.java.memoranda.Event;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectImpl;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;
import main.java.memoranda.Task;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.AccountUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class VisibilityTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    /**
     * Test the creation of events with different visibility levels.
     */
    @Test
    public void testCreateEvent() {
        CalendarDate date = new CalendarDate(2024, 2, 25);
        int hh = 12;
        int mm = 0;
        String text = "test text";
        String topic = "test topic";
        File filePath = null;

        // Test for STUDENT visibility
        Event eventStudent = EventsManager.createEvent(date, hh, mm, text, topic,
                AccountUtils.Rank.STUDENT, filePath);
        assertEquals(AccountUtils.Rank.STUDENT, eventStudent.getVisibility());

        // Test for GRADER visibility
        Event eventGrader = EventsManager.createEvent(date, hh, mm, text, topic,
                AccountUtils.Rank.GRADER, filePath);
        assertEquals(AccountUtils.Rank.GRADER, eventGrader.getVisibility());

        // Test for TA visibility
        Event eventTA = EventsManager.createEvent(date, hh, mm, text, topic,
                AccountUtils.Rank.TA, filePath);
        assertEquals(AccountUtils.Rank.TA, eventTA.getVisibility());

        // Test for INSTRUCTOR visibility
        Event eventInstructor = EventsManager.createEvent(date, hh, mm, text, topic,
                AccountUtils.Rank.INSTRUCTOR, filePath);
        assertEquals(AccountUtils.Rank.INSTRUCTOR, eventInstructor.getVisibility());
    }

    /**
     * Test the creation of tasks with different visibility levels.
     */
    @Test
    public void testCreateTask() {
        Project project = new ProjectImpl(null);
        TaskList taskList = new TaskListImpl(project);

        CalendarDate startDate = new CalendarDate(2024, 2, 25);
        CalendarDate endDate = new CalendarDate(2024, 2, 26);
        String text = "test text";
        int priority = 1;
        long effort = 1L;
        String description = "test desc";
        String parentTaskId = null;

        // Test for STUDENT visibility
        Task taskStudent = taskList.createTask(startDate, endDate, text, priority, effort,
                description, parentTaskId, AccountUtils.Rank.STUDENT);
        assertEquals(AccountUtils.Rank.STUDENT, taskStudent.getVisibility());

        // Test for GRADER visibility
        Task taskGrader = taskList.createTask(startDate, endDate, text, priority, effort, description,
                parentTaskId, AccountUtils.Rank.GRADER);
        assertEquals(AccountUtils.Rank.GRADER, taskGrader.getVisibility());

        // Test for TA visibility
        Task taskTA = taskList.createTask(startDate, endDate, text, priority, effort, description,
                parentTaskId, AccountUtils.Rank.TA);
        assertEquals(AccountUtils.Rank.TA, taskTA.getVisibility());

        // Test for INSTRUCTOR visibility
        Task taskInstructor = taskList.createTask(startDate, endDate, text, priority, effort, description,
                parentTaskId, AccountUtils.Rank.INSTRUCTOR);
        assertEquals(AccountUtils.Rank.INSTRUCTOR, taskInstructor.getVisibility());
    }

}