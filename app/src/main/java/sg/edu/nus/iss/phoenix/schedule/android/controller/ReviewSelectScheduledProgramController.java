package sg.edu.nus.iss.phoenix.schedule.android.controller;

import java.util.List;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduledProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleList;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * Created by rahul on 9/19/2017.
 */

public class ReviewSelectScheduledProgramController {

    private ScheduledProgramScreen mScheduledProgramScreen;
    private ProgramSlot mProgramSlot;
    private AnnualScheduleList mAnnualScheduleList;

    public ReviewSelectScheduledProgramController(){

    }

    public void onOk(){

    }

    public void onCancel(){

    }

    public void programSlotsRetrieved(List<ProgramSlot> iListProgramSlots){
        // TBD
        //displaySchedule() for ScheduledProgramScreen
    }

    public void reviewSelectScheduledProgram(){

    }

    public void selectScheduledProgram(){

    }

    public void show(ScheduledProgramScreen scheduleProgramScreen){
    // TBD
        this.mScheduledProgramScreen = scheduleProgramScreen;
        new RetrieveScheduleDelegate(this).execute("all");
    }

}
