import static org.junit.Assert.*;

import main.java.memoranda.util.AccountUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class AccountUtilsTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    /**
     * Test the conversion of strings to enum values in the AccountUtils.Rank enum.
     */
    @Test
    public void testToEnum() {
        // Test for STUDENT
        assertEquals(AccountUtils.Rank.STUDENT, AccountUtils.toEnum("STUDENT"));
        assertEquals(AccountUtils.Rank.STUDENT, AccountUtils.toEnum("student"));
        assertEquals(AccountUtils.Rank.STUDENT, AccountUtils.toEnum("Student"));

        // Test for GRADER
        assertEquals(AccountUtils.Rank.GRADER, AccountUtils.toEnum("GRADER"));
        assertEquals(AccountUtils.Rank.GRADER, AccountUtils.toEnum("grader"));
        assertEquals(AccountUtils.Rank.GRADER, AccountUtils.toEnum("Grader"));

        // Test for TA
        assertEquals(AccountUtils.Rank.TA, AccountUtils.toEnum("TA"));
        assertEquals(AccountUtils.Rank.TA, AccountUtils.toEnum("ta"));
        assertEquals(AccountUtils.Rank.TA, AccountUtils.toEnum("Ta"));

        // Test for INSTRUCTOR
        assertEquals(AccountUtils.Rank.INSTRUCTOR, AccountUtils.toEnum("INSTRUCTOR"));
        assertEquals(AccountUtils.Rank.INSTRUCTOR, AccountUtils.toEnum("instructor"));
        assertEquals(AccountUtils.Rank.INSTRUCTOR, AccountUtils.toEnum("Instructor"));

        // Test for default (STUDENT)
        assertEquals(AccountUtils.Rank.STUDENT, AccountUtils.toEnum("BadInput"));
    }
}