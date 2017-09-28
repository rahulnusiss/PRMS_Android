package sg.edu.nus.iss.phoenix.schedule.entity;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

/**
 * Created by rahul on 9/19/2017.
 */

public class ProgramSlot {
    private String mName;
    private RadioProgram mRadioProgram;
    private SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH);

    private java.sql.Date mDateOfProgram;
    private Integer mDuration;

    private Integer mStartTimeofDay; // in seconds

    public ProgramSlot(String iName){
        this.mName = iName;
        mRadioProgram = new RadioProgram("Empty", "Empty", "Empty");
        mDateOfProgram = new java.sql.Date(new Date().getTime());
        mDuration = 00;
        mStartTimeofDay = 00;
    }

    public ProgramSlot(String iName, java.sql.Date iDateofProgram, Integer iDuration, Integer iStartTime){
        this.mName = iName;
        mRadioProgram = new RadioProgram("Empty", "Empty", "Empty");
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
    }

    public void setRadioProgram(RadioProgram iRadioPr){
        mRadioProgram = iRadioPr;
    }
    public void setName(String iName){
        mName = iName;
    }
    public String getName(){
        return mName;
    }
    public RadioProgram getRadioProgram(){
        return mRadioProgram;
    }

    public java.sql.Date getDateOfProgram(){
        return mDateOfProgram;
    }
    public void setDateOfProgram( String iDateofProgram) {
        try {
            Date date = mSDF.parse(iDateofProgram);
            mDateOfProgram = new java.sql.Date(date.getTime());
            //mDateOfProgram = mSDF.parse(iDateofProgram);
        } catch (Exception e) {
            //print e.getMessage
        }
    }

    public Integer getDuration(){
        return mDuration;
    }
    public void setDuration(String iDuration){
        mDuration = ScheduleUtility.parseDuration(iDuration);
    }


    public Integer getStartTime(){
        return mStartTimeofDay;
    }
    public void setStartTime( String iStartTime){
        mStartTimeofDay = ScheduleUtility.parseDuration(iStartTime);
    }

    public boolean isProgramSlotAssigned(AnnualScheduleList iAnnualScheduleList){
        return ScheduleUtility.isProgramSlotOverlap(iAnnualScheduleList,this);
    }
}
