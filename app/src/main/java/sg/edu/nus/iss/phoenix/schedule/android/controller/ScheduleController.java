package sg.edu.nus.iss.phoenix.schedule.android.controller;


import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.UpdateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleList;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 * Created by rahul on 9/19/2017.
 */

public class ScheduleController {
    // Tag for logging.
    private static final String TAG = ScheduleController.class.getName();

    private MaintainScheduleScreen maintainScheduleScreen;

    private ProgramSlot programSlot = null;
    private WeeklySchedule weeklySchedule = null;
    private AnnualSchedule annualSchedule = null;
    private AnnualScheduleList listAnnualSchedule = null;

    public void startUseCase(){
        programSlot = null;
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayScheduleList(MaintainScheduleScreen maintainScheduleScreen){
        this.maintainScheduleScreen = maintainScheduleScreen;
        new RetrieveScheduleDelegate(this).execute("all");

    }

    public void onDisplayScheduleProgram(/*MaintainScheduleScreen*/){

    }

    public void presenterSelected(){

    }

    public void processModifyProgramSlot(){

    }

    public void processModifyTime(){

    }

    public void processPresenter(){

    }

    public void processProducer(){

    }

    public void producerSelected(){

    }

    public void programSelected(){

    }

    public void programSlotSelected(){

    }

    public void reviewSelectPresenterProducer(){

    }

    public void reviewSelectProgram(){

    }

    public void scheduleCopied(boolean iStatus){

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

    public void scheduledProgram(){

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
        maintainScheduleScreen.displaySchedule(iProgramSlots);
    }

    public void selectCancelCreateEditSchedule(){


    }

    public void selectCopySchedule(ProgramSlot iProgramSlot){

    }

    public void selectCreateSchedule(ProgramSlot iProgramSlot){
        new CreateScheduleDelegate(this).execute(iProgramSlot);
    }

    public void selectDeleteSchedule(ProgramSlot iProgramSlot){
        new DeleteScheduleDelegate(this).execute(iProgramSlot);

    }

    public void selectModifySchedule(ProgramSlot iProgramSlot){
        new UpdateScheduleDelegate(this).execute(iProgramSlot);
    }

    public void startCreateSchedule(ProgramSlot iProgramSlot){

    }

}
