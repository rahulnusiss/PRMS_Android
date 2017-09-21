package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class AnnualScheduleList {
    private List<AnnualSchedule> mListAnnualSchedule;

    public AnnualScheduleList(){
        mListAnnualSchedule.add(new AnnualSchedule());
    }

    public AnnualScheduleList(List<AnnualSchedule> iListAnnSch){
        mListAnnualSchedule = iListAnnSch;
    }

    public void addAnnualSchedule(AnnualSchedule iAnnSch){
        mListAnnualSchedule.add(iAnnSch);
    }

    public List<AnnualSchedule> retrieveAllAnnualSchedules(){
        return mListAnnualSchedule;
    }
}
