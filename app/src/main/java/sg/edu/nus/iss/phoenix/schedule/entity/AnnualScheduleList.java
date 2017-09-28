package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class AnnualScheduleList {
    private List<AnnualSchedule> mListAnnualSchedule;

    public AnnualScheduleList(){
        mListAnnualSchedule = new ArrayList<AnnualSchedule>();
    }

    public void setListAnnualSchedule(List<AnnualSchedule> iAnnSchedules){
        mListAnnualSchedule = iAnnSchedules;
    }
    public List<AnnualSchedule> getAnnualScheduleList(){
        return mListAnnualSchedule;
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

    public AnnualSchedule getYearlySchedule(int iYear) {
        int yIndex = doesYearExistAlready(iYear);
        if (-1 == yIndex) {
            AnnualSchedule ys = new AnnualSchedule(iYear);
            mListAnnualSchedule.add(ys);
            return ys;
        }
        return mListAnnualSchedule.get(yIndex);
    }

    // return index
    public int doesYearExistAlready(int iYear){
        int size = mListAnnualSchedule.size();
        for (int i = 0; i < size; ++i){
            int currYear = mListAnnualSchedule.get(i).getYear();
            if (iYear == currYear){
                return i;
            }
        }
        return -1; // Not found
    }
}
