package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class WeeklySchedule {

    private List<ProgramSlot> mProgramSlots;

    public WeeklySchedule(){
        mProgramSlots.add(new ProgramSlot("Cheap Thrills"));
    }

    public WeeklySchedule(List<ProgramSlot> iListPrSlots){
        mProgramSlots = iListPrSlots;
    }

    public void addProgramSlot(ProgramSlot iPrSlot){
        mProgramSlots.add(iPrSlot);
    }

    public List<ProgramSlot> retrieveAllProgramSlot(){
        return mProgramSlots;
    }
}
