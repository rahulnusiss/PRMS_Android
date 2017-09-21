package sg.edu.nus.iss.phoenix.schedule.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * Created by rahul on 9/19/2017.
 */

public class ProgramSlot {
    private String mName;
    private RadioProgram mRadioProgram;

    public ProgramSlot(String iName){
        this.mName = iName;
        mRadioProgram = new RadioProgram("Empty", "Empty", "Empty");
    }

    public void setRadioProgram(RadioProgram iRadioPr){
        mRadioProgram = iRadioPr;
    }
    public void setName(String iName){
        mName = iName;
    }
    public String getName(){
        return mName;
    }
    public RadioProgram getRadioProgram(){
        return mRadioProgram;
    }
}
