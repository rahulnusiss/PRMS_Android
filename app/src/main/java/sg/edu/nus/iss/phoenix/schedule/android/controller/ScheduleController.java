package sg.edu.nus.iss.phoenix.schedule.android.controller;


import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
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

    public void onDisplayProgramList(/*Scheduled Program Screen*/){

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

    }

    public void scheduleDeleted(boolean iStatus){

    }

    public void scheduledProgram(){

    }

    public void scheduleModified(boolean iStatus){

    }

    public void scheduleRetrieved(ProgramSlot iProgramSlot){

    }

    public void selectCancelCreateEditSchedule(){

    }

    public void selectCopySchedule(ProgramSlot iProgramSlot){

    }

    public void selectCreateSchedule(ProgramSlot iProgramSlot){

    }

    public void selectDeleteSchedule(ProgramSlot iProgramSlot){

    }

    public void selectModifySchedule(ProgramSlot iProgramSlot){

    }

    public void startCreateSchedule(ProgramSlot iProgramSlot){

    }

}
