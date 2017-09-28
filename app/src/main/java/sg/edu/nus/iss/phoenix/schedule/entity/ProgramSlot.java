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

    private Date mDateOfProgram;
    private Integer mDuration;
    private Date mStartTime;

    public ProgramSlot(String iName){
        this.mName = iName;
        mRadioProgram = new RadioProgram("Empty", "Empty", "Empty");
        mDateOfProgram = new Date();
        mDuration = 00;
        mStartTime = new Date();
    }

    public ProgramSlot(String iName, Date iDateofProgram, Integer iDuration, Date iStartTime){
        this.mName = iName;
        mRadioProgram = new RadioProgram("Empty", "Empty", "Empty");
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTime = iStartTime;
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

    public Date getDateOfProgram(){
        return mDateOfProgram;
    }
    public void setDateOfProgram( String iDateofProgram) {
        try {
            mDateOfProgram = mSDF.parse(iDateofProgram);
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


    public Date getStartTime(){
        return mStartTime;
    }
    public void setStartTime( String iStartTime){
        try {
            mStartTime = mSDF.parse(iStartTime);
        } catch (Exception e) {
            //print e.getMessage
        }
    }

    public boolean isProgramSlotAssigned(AnnualScheduleList iAnnualScheduleList){
        return ScheduleUtility.isProgramSlotOverlap(iAnnualScheduleList,this);
    }
}
