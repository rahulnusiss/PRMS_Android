package sg.edu.nus.iss.phoenix.schedule.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by GONG MENGNAN on 2/10/17.
 */
public class WeeklyScheduleTest {
    private WeeklySchedule weeklySchedule;
    private int week = 10;


    @Before
    public void setUp() throws Exception {
        weeklySchedule = new WeeklySchedule(week);
    }

    @After
    public void tearDown() throws Exception {
        weeklySchedule = null;
    }

    @Test
    public void getWeek() {
        assertEquals(weeklySchedule.getWeek(),week);
    }

    @Test
    public void setWeek() {
        weeklySchedule.setWeek(11);
        assertEquals(weeklySchedule.getWeek(),11);
    }

    @Test
    public void TestProgramSlot() {
        weeklySchedule.addProgramSlot(new ProgramSlot("Slot2"));
        int size = weeklySchedule.retrieveAllProgramSlot().size();
        assertEquals(weeklySchedule.retrieveAllProgramSlot().get(size-1).getName(),"Slot2");
    }
}