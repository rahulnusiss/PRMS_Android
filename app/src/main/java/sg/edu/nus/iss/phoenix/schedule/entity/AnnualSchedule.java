package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class AnnualSchedule {
    /**
     * List of weekly schedules
     */
    private List<WeeklySchedule> mListWeeklySchedule;

    /**
     *  The year representing this annual schedule
     */
    private int mYear = 0000;

    /**
     * To set year
     * @param iYear input year
     */
    public void setYear(int iYear){
        mYear = iYear;
    }

    /**
     * To get year
     * @return
     */
    public int getYear(){
        return mYear;
    }

    /**
     * Constructor parameterized
     * @param iYear
     */
    public AnnualSchedule(int iYear){
        mListWeeklySchedule = new ArrayList<WeeklySchedule>();
        mYear = iYear;
    }

    /**
     * Constructor with list and year input
     * @param iListWeeklySch
     * @param iYear
     */
    public AnnualSchedule(List<WeeklySchedule> iListWeeklySch, int iYear){
        mListWeeklySchedule = iListWeeklySch;
        mYear = iYear;
    }

    /**
     * To add weekly schedule in this year
     * @param iWeeklySch to add weekly schedule
     */
    public void addWeeklySchedule(WeeklySchedule iWeeklySch){
        mListWeeklySchedule.add(iWeeklySch);
    }

    /**
     * Returns the weekly schedule
     * @param iWeek The week number of the year
     * @return wekly schedule of input
     */
    public WeeklySchedule getWeeklySchedule(int iWeek){
        int wIndex = doesWeekExistAlready(iWeek);
        if ( -1 == wIndex ){
            WeeklySchedule ws = new WeeklySchedule(iWeek);
            mListWeeklySchedule.add(ws);
            return ws;
        }
        return mListWeeklySchedule.get(wIndex);
    }

    /**
     * Gets all weekly schedule
     * @return All weekly schedule in this year
     */
    public List<WeeklySchedule> retrieveAllWeeklySchedules(){
        return mListWeeklySchedule;
    }

    /**
     * Check existing weekly schedule
     * @param iWeek to check
     * @return -1 if not exist else return value greater than equal to 0.
     */
    private int doesWeekExistAlready(int iWeek){
        int size = mListWeeklySchedule.size();
        for (int i = 0; i < size; ++i){
            int currWeek = mListWeeklySchedule.get(i).getWeek();
            if (iWeek == currWeek){
                return i;
            }
        }
        return -1;
    }
}
