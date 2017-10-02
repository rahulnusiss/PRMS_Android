package sg.edu.nus.iss.phoenix.schedule.entity;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.sql.*;

import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

/**
 * Created by rahul on 9/19/2017.
 */

public class ProgramSlot {

    private int mID;
    private String mName;
    private SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private Date mDateOfProgram;
    private Integer mDuration;
    private String mPresenter;
    private String mProducer;

    private Time mStartTimeofDay; // in seconds

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
        mDateOfProgram = null;
        mDuration = 00;
        mStartTimeofDay = null;
        mPresenter = "";
        mProducer = "";
    }

    public ProgramSlot(String iName, Date iDateofProgram, Integer iDuration, Time iStartTime){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
        mPresenter = "";
        mProducer = "";
    }

    public ProgramSlot(String iName, Date iDateofProgram, Integer iDuration, Time iStartTime,
                       String iRadioProgram, String iPresenter, String iProducer){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
        mPresenter = iPresenter;
        mProducer = iProducer;
    }

    public ProgramSlot(int iID, String iName, Date iDateofProgram, Integer iDuration, Time iStartTime,
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

    public Date getDateOfProgram(){
        return mDateOfProgram;
    }
    public void setDateOfProgram(Date iDateofProgram) {
        mDateOfProgram = iDateofProgram;
    }

    public Integer getDuration(){
        return mDuration;
    }
    public void setDuration(String iDuration){
        mDuration = ScheduleUtility.parseDuration(iDuration);
    }


    public Time getStartTime(){
        return mStartTimeofDay;
    }
    public void setStartTime( Time iStartTime){
        mStartTimeofDay = iStartTime;
    }

    public boolean isProgramSlotAssigned(AnnualScheduleList iAnnualScheduleList){
        return ScheduleUtility.isProgramSlotOverlap(iAnnualScheduleList,this);
    }
}
