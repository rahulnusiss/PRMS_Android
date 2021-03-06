package sg.edu.nus.iss.phoenix.schedule.android.controller;


import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.UpdateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleList;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

/**
 * Created by rahul on 9/19/2017.
 */

public class ScheduleController {
    // Tag for logging.
    private static final String TAG = ScheduleController.class.getName();

    private ScheduleProgramScreen ScheduleProgramScreen;
    private MaintainScheduleScreen scheduleScreen;

    private ProgramSlot programSlot = null;
    private AnnualScheduleList listAnnualSchedule = null;
    private String radioProgram = null;

    public void setProgramSlot(ProgramSlot iProgramSlot){
        programSlot = iProgramSlot;
    }
    public ProgramSlot getProgramSlot(){
        return programSlot;
    }

    public void setRadioProgram(String iProgramSlot){
        radioProgram = iProgramSlot;
    }
    public String getRadioProgram(){
        return radioProgram;
    }

    public void startUseCase(){
        programSlot = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayScheduleList(ScheduleProgramScreen ScheduleProgramScreen){
        this.ScheduleProgramScreen = ScheduleProgramScreen;
        new RetrieveScheduleDelegate(this).execute("all");

    }

    public void onDisplayScheduleProgram(MaintainScheduleScreen scheduleScreen){
        this.scheduleScreen = scheduleScreen;
        if (programSlot == null)
            scheduleScreen.createSchedule();
        else
            scheduleScreen.editSchedule(programSlot);

    }

    public AnnualScheduleList getListAnnualSchedule(){
        return listAnnualSchedule;
    }

    public void scheduleCreated(boolean iStatus){
        // Go back to Maintain Program screen with refreshed Schedules.
        String toastString = "";
        if ( true == iStatus){
            toastString = "Schedule Created";
        }
        else{
            toastString = "Failed to create schedule";
        }
        Toast toast = Toast.makeText(MainController.getApp(), toastString, Toast.LENGTH_SHORT);
        toast.show();
        startUseCase();
    }

    public void scheduleDeleted(boolean iStatus){
        // Go back to Maintain Program screen with refreshed Schedules.
        String toastString = "";
        if ( true == iStatus){
            toastString = "Schedule Deleted";
        }
        else{
            toastString = "Failed to delete schedule";
        }
        Toast toast = Toast.makeText(MainController.getApp(), toastString, Toast.LENGTH_SHORT);
        toast.show();
        startUseCase();
    }

    public void scheduleModified(boolean iStatus){
        // Go back to Maintain Program screen with refreshed Schedules.
        String toastString = "";
        if ( true == iStatus){
            toastString = "Schedule Modified";
        }
        else{
            toastString = "Failed to modify schedule";
        }
        Toast toast = Toast.makeText(MainController.getApp(), toastString, Toast.LENGTH_SHORT);
        toast.show();
        startUseCase();
    }

    public void scheduleRetrieved(List<ProgramSlot> iProgramSlots){
        boolean status = (iProgramSlots == null)?false:true;
        if ( status ){
            listAnnualSchedule = ScheduleUtility.prepareLists(iProgramSlots);
            //ScheduleProgramScreen.setAnnualScheduleList(listAnnualSchedule);
        }
        ScheduleProgramScreen.showProgramSlots(iProgramSlots);
    }

    public void selectCancelCreateEditSchedule(){
        startUseCase();
    }

    public void selectCopySchedule(ProgramSlot iProgramSlot){
        new CreateScheduleDelegate(this, true).execute(iProgramSlot);
    }

    public void selectCreateSchedule(ProgramSlot iProgramSlot){
        new CreateScheduleDelegate(this).execute(iProgramSlot);
    }

    public void selectDeleteSchedule(ProgramSlot iProgramSlot){
        new DeleteScheduleDelegate(this).execute(iProgramSlot);

    }

    public void selectModifySchedule(ProgramSlot iNewProgramSlot){
        new UpdateScheduleDelegate(this).execute(iNewProgramSlot);
    }

}
