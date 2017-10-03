package sg.edu.nus.iss.phoenix.schedule.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import static org.junit.Assert.*;

/**
 * Created by GONG MENGNAN on 2/10/17.
 */
public class ProgramSlotTest {

    private ProgramSlot programSlot;

    @Before
    public void setUp() throws Exception {
        //String iName, java.sql.Date iDateofProgram, Integer iDuration, Integer iStartTime,
        //String iRadioProgram, String iPresenter, String iProducer
        programSlot = new ProgramSlot("name",new Date(1), 2, 3, "program4", "presenter5", "producer6");
    }

    @After
    public void tearDown() throws Exception {
        programSlot = null;
    }

    @Test
    public void TestName() {
        assertEquals(programSlot.getName(),"name1");
    }

    @Test
    public void TestDateOfProgram() {
        assertEquals(programSlot.getDateOfProgram(),new Date(1));
    }

    @Test
    public void TestDuration() {
        assertEquals(programSlot.getDuration(),new Integer(2));
    }

    @Test
    public void TestStartTime() {
        assertEquals(programSlot.getStartTime(),new Integer(3));
    }

    @Test
    public void TestRadioProgram() {
        assertEquals(programSlot.getRadioProgram(),"program4");
    }

    @Test
    public void TestPresenter() {
        assertEquals(programSlot.getPresenter(),"presenter5");
    }


}