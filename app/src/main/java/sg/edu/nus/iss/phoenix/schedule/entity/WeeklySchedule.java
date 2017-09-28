package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class WeeklySchedule {

    private List<ProgramSlot> mProgramSlots;

    private int mWeek = 0000;

    public void setWeek(int iWeek){
        mWeek = iWeek;
    }

    public int getWeek(){
        return mWeek;
    }

    public WeeklySchedule(){
        mProgramSlots.add(new ProgramSlot("Cheap Thrills"));
        mWeek = 0;
    }

    public WeeklySchedule(int iWeek){
        mProgramSlots = new ArrayList<ProgramSlot>();
        mWeek = iWeek;
    }

    public WeeklySchedule(List<ProgramSlot> iListPrSlots, int iWeek){
        mProgramSlots = iListPrSlots;
        mWeek = iWeek;

    }

    public void addProgramSlot(ProgramSlot iPrSlot){
        mProgramSlots.add(iPrSlot);
    }

    public List<ProgramSlot> retrieveAllProgramSlot(){
        return mProgramSlots;
    }
}
