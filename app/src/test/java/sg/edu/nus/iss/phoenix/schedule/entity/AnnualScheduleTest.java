package sg.edu.nus.iss.phoenix.schedule.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by GONG MENGNAN on 2/10/17.
 */
public class AnnualScheduleTest {

    private AnnualSchedule annualSchedule;
    private int year = 2000;

    @Before
    public void setUp() throws Exception {
        annualSchedule = new AnnualSchedule(year);
    }

    @After
    public void tearDown() throws Exception {
        annualSchedule = null;
    }

    @Test
    public void TestYear() {
        assertEquals(annualSchedule.getYear(),year);
    }

    @Test
    public void TestWeeklySchedule() {
        annualSchedule.addWeeklySchedule(new WeeklySchedule(0));
        assertEquals(annualSchedule.getWeeklySchedule(0).getWeek(), 0);
    }

    @Test
    public void TestAllWeeklySchedules() {
        annualSchedule.addWeeklySchedule(new WeeklySchedule(2));
        List<WeeklySchedule> schedules = annualSchedule.retrieveAllWeeklySchedules();
        assertEquals(schedules.get(0).getWeek(), 2);
    }
}