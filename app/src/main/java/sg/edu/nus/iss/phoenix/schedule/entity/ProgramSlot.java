package sg.edu.nus.iss.phoenix.schedule.entity;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.sql.*;

import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

/**
 * Created by rahul on 9/19/2017.
 */

public class ProgramSlot {

    /**
     * ID promary key
     */
    private int mID;
    /**
     * Name of program
     */
    private String mName;
    /**
     * To format date
     */
    private SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    /**
     * Date of program
     */
    private Date mDateOfProgram;
    /**
     * Duration of program
     */
    private Integer mDuration;
    /**
     * Presenter name
     */
    private String mPresenter;
    /**
     * Producer name
     */
    private String mProducer;

    /**
     * Start time of program
     */
    private Time mStartTimeofDay; // in seconds

    /**
     * Setter
     * @param iID
     */
    public void setID(int iID){
        mID = iID;
    }

    /**
     * Getter
     * @return
     */
    public int getID(){
        return mID;
    }

    /**
     * Setter
     * @param iPresenter
     */
    public void setPresenter(String iPresenter){
        mPresenter = iPresenter;
    }

    /**
     * Getter
     * @return
     */
    public String getPresenter(){
        return mPresenter;
    }

    /**
     * Setter
     * @param iProducer
     */
    public void setProducer(String iProducer){
        mProducer = iProducer;
    }

    /**
     * Getter
     * @return
     */
    public String getProducer(){
        return mProducer;
    }

    /**
     * Constructor
     * @param iName
     */
    public ProgramSlot(String iName){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = null;
        mDuration = 00;
        mStartTimeofDay = null;
        mPresenter = "";
        mProducer = "";
    }

    /**
     * Constructor
     * @param iName
     * @param iDateofProgram
     * @param iDuration
     * @param iStartTime
     */
    public ProgramSlot(String iName, Date iDateofProgram, Integer iDuration, Time iStartTime){
        this.mName = iName;
        mID = 0;
        mDateOfProgram = iDateofProgram;
        mDuration = iDuration;
        mStartTimeofDay = iStartTime;
        mPresenter = "";
        mProducer = "";
    }

    /**
     * Constructor
     * @param iName
     * @param iDateofProgram
     * @param iDuration
     * @param iStartTime
     * @param iRadioProgram
     * @param iPresenter
     * @param iProducer
     */
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

    /**
     * Constructor
     * @param iID
     * @param iName
     * @param iDateofProgram
     * @param iDuration
     * @param iStartTime
     * @param iPresenter
     * @param iProducer
     */
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

    /**
     * Setter
     * @param iName
     */
    public void setName(String iName){
        mName = iName;
    }

    /**
     * Getter
     * @return
     */
    public String getName(){
        return mName;
    }

    /**
     * Getter
     * @return
     */
    public Date getDateOfProgram(){
        return mDateOfProgram;
    }

    /**
     * Setter
     * @param iDateofProgram
     */
    public void setDateOfProgram(Date iDateofProgram) {
        mDateOfProgram = iDateofProgram;
    }

    /**
     * Getter
     * @return
     */
    public Integer getDuration(){
        return mDuration;
    }

    /**
     * Setter
     * @param iDuration
     */
    public void setDuration(String iDuration){
        mDuration = Integer.parseInt(iDuration);
        //mDuration = ScheduleUtility.parseDurationFromMin(iDuration);
    }


    /**
     * Getter
     * @return
     */
    public Time getStartTime(){
        return mStartTimeofDay;
    }

    /**
     * Setter
     * @param iStartTime
     */
    public void setStartTime( Time iStartTime){
        mStartTimeofDay = iStartTime;
    }

    /**
     * To check if program slot is assigned
     * @param iAnnualScheduleList List of all annual schedules
     * @return true if assigned else false
     */
    public boolean isProgramSlotAssigned(AnnualScheduleList iAnnualScheduleList){
        return ScheduleUtility.isProgramSlotOverlap(iAnnualScheduleList,this);
    }
}
