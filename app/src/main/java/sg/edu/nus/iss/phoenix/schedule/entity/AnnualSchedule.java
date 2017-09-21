package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class AnnualSchedule {
    private List<WeeklySchedule> mListWeeklySchedule;

    public AnnualSchedule(){
        mListWeeklySchedule.add(new WeeklySchedule());
    }

    public AnnualSchedule(List<WeeklySchedule> iListWeeklySch){
        mListWeeklySchedule = iListWeeklySch;
    }

    public void addWeeklySchedule(WeeklySchedule iWeeklySch){
        mListWeeklySchedule.add(iWeeklySch);
    }

    public List<WeeklySchedule> retrieveAllWeeklySchedules(){
        return mListWeeklySchedule;
    }
}
