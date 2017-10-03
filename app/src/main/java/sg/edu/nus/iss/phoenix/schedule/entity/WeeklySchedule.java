package sg.edu.nus.iss.phoenix.schedule.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 9/19/2017.
 */

public class WeeklySchedule {

    /**
     * List of program slots
     */
    private List<ProgramSlot> mProgramSlots;

    /**
     * Week representing this weekly schedule
     */
    private int mWeek = 0000;

    /**
     * Setter
     * @param iWeek
     */
    public void setWeek(int iWeek){
        mWeek = iWeek;
    }

    /**
     * Getter
     * @return week value
     */
    public int getWeek(){
        return mWeek;
    }

    /**
     * Constructor
     */
    public WeeklySchedule(){
        mProgramSlots.add(new ProgramSlot("Cheap Thrills"));
        mWeek = 0;
    }

    /**
     * Constructor
     * @param iWeek
     */
    public WeeklySchedule(int iWeek){
        mProgramSlots = new ArrayList<ProgramSlot>();
        mWeek = iWeek;
    }

    /**
     * Constructor
     * @param iListPrSlots
     * @param iWeek
     */
    public WeeklySchedule(List<ProgramSlot> iListPrSlots, int iWeek){
        mProgramSlots = iListPrSlots;
        mWeek = iWeek;

    }

    /**
     * To add a new program slot
     * @param iPrSlot The program slot to add
     */
    public void addProgramSlot(ProgramSlot iPrSlot){
        mProgramSlots.add(iPrSlot);
    }

    /**
     * To get all program slots
     * @return list of program slots
     */
    public List<ProgramSlot> retrieveAllProgramSlot(){
        return mProgramSlots;
    }
}
