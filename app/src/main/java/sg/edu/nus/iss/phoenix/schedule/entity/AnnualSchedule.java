package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class AnnualSchedule {
    private List<WeeklySchedule> mListWeeklySchedule;
    private int mYear = 0000;

    public void setYear(int iYear){
        mYear = iYear;
    }

    public int getYear(){
        return mYear;
    }

    public AnnualSchedule(){
        //mListWeeklySchedule.add(new WeeklySchedule());
        //mYear = 000;
    }

    public AnnualSchedule(int iYear){
        mListWeeklySchedule = new ArrayList<WeeklySchedule>();
        mYear = iYear;
    }

    public AnnualSchedule(List<WeeklySchedule> iListWeeklySch, int iYear){
        mListWeeklySchedule = iListWeeklySch;
        mYear = iYear;
    }

    public void addWeeklySchedule(WeeklySchedule iWeeklySch){
        mListWeeklySchedule.add(iWeeklySch);
    }

    public WeeklySchedule getWeeklySchedule(int iWeek){
        int wIndex = doesWeekExistAlready(iWeek);
        if ( -1 == wIndex ){
            WeeklySchedule ws = new WeeklySchedule(iWeek);
            mListWeeklySchedule.add(ws);
            return ws;
        }
        return mListWeeklySchedule.get(wIndex);
    }

    public List<WeeklySchedule> retrieveAllWeeklySchedules(){
        return mListWeeklySchedule;
    }

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
