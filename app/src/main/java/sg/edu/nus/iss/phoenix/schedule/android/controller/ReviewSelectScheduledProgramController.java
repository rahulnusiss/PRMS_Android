package sg.edu.nus.iss.phoenix.schedule.android.controller;

import java.util.List;

import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleList;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

/**
 * Created by rahul on 9/19/2017.
 */

public class ReviewSelectScheduledProgramController {

    private MaintainScheduleScreen mMaintainScheduleScreen;
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
        boolean status = (iListProgramSlots == null)?false:true;
        if ( status ){
            mAnnualScheduleList = ScheduleUtility.prepareLists(iListProgramSlots);
        }
    }

    public void reviewSelectScheduledProgram(){

    }

    public void selectScheduledProgram(){

    }

    public void show(MaintainScheduleScreen scheduleProgramScreen){
    // TBD
        this.mMaintainScheduleScreen = scheduleProgramScreen;
        new RetrieveScheduleDelegate(this).execute("all");
    }

}
