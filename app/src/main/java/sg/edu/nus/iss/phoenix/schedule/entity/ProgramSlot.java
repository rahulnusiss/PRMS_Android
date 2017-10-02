package sg.edu.nus.iss.phoenix.schedule.entity;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

/**
 * Created by rahul on 9/19/2017.
 */

public class ProgramSlot {

    private int mID;
    private String mName;
    private SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private java.sql.Date mDateOfProgram;
    private Integer mDuration;
    private String mPresenter;
    private String mProducer;

    private Integer mStartTimeofDay; // in seconds

    public void setID(int iID){
        mID = iID;
    }
    public int getID(){
        return mID;
    }

    public void setPresenter(String iPresenter){
        mPresenter = iPresenter;
    }
    public String getPresenter(){
        return mPresenter;
    }
    public void setProducer(String iProducer){
        mProducer = iProducer;
    }
    public String getProducer(){
        return mProducer;
    }
    public ProgramSlot(String iName){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = new java.sql.Date(new Date().getTime());
        mDuration = 00;
        mStartTimeofDay = 00;
        mPresenter = "";
        mProducer = "";
    }

    public ProgramSlot(String iName, java.sql.Date iDateofProgram, Integer iDuration, Integer iStartTime){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
        mPresenter = "";
        mProducer = "";
    }

    public ProgramSlot(String iName, java.sql.Date iDateofProgram, Integer iDuration, Integer iStartTime,
                       String iRadioProgram, String iPresenter, String iProducer){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
        mPresenter = iPresenter;
        mProducer = iProducer;
    }

    public ProgramSlot(int iID, String iName, java.sql.Date iDateofProgram, Integer iDuration, Integer iStartTime,
                       String iPresenter, String iProducer){
        this.mName = iName;
        mID = iID;
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
        mPresenter = iPresenter;
        mProducer = iProducer;
    }

    public void setName(String iName){
        mName = iName;
    }
    public String getName(){
        return mName;
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
