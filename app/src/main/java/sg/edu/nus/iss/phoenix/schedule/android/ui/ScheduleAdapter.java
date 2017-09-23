package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import sg.edu.nus.iss.phoenix.R;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * Created by rahul on 9/19/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<ProgramSlot> {

    public ScheduleAdapter(@NonNull Context context, ArrayList<ProgramSlot> programSlots) {
        super(context, 0, programSlots);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_scheduled_program_screen, parent, false);
        }
        //    Word currentWord = getItem(position);
        ProgramSlot currentPS = getItem(position);

        EditText radioPSName = (EditText)listItemView.findViewById(R.id.maintain_schedule_name_text_view);
        radioPSName.setText(currentPS.getName(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSName);

        EditText radioPSDateofPr = (EditText)listItemView.findViewById(R.id.maintain_schedule_dateOfPr_text_view);
        radioPSDateofPr.setText("Date of program " + currentPS.getDateOfProgram().toString(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSDateofPr);

        EditText radioPSDuration = (EditText)listItemView.findViewById(R.id.maintain_schedule_duration_text_view);
        radioPSDuration.setText("Duration is " + currentPS.getDuration().toString(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSDuration);

        EditText radioPSStartTime = (EditText)listItemView.findViewById(R.id.maintain_schedule_starttime_text_view);
        radioPSStartTime.setText("Start Time "+ currentPS.getStartTime().toString(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSStartTime);

        return listItemView;
    }

    private void setEditTextAttr( EditText iEditText ){
        iEditText.setKeyListener(null);
        iEditText.setClickable(false);
        iEditText.setFocusable(false);
        iEditText.setFocusableInTouchMode(false);
    }
}
