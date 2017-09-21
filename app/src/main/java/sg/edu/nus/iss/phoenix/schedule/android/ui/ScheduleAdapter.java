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
        radioPSName.setKeyListener(null); // This disables editing.
        radioPSName.setClickable(false);
        radioPSName.setFocusable(false);
        radioPSName.setFocusableInTouchMode(false);

        /*EditText radioPMDesc = (EditText)listItemView.findViewById(R.id.maintain_schedule_desc_text_view);
        radioPMDesc.setText(*//*currentRP.getRadioProgramDescription()*//* "Tuesday 16:40", TextView.BufferType.NORMAL);
        radioPMDesc.setKeyListener(null);

        EditText radioPMDuration = (EditText)listItemView.findViewById(R.id.maintain_schedule_duration_text_view);
        radioPMDuration.setText(*//*currentRP.getRadioProgramDuration()*//* "Wednesday 11:30", TextView.BufferType.NORMAL);
        radioPMDuration.setKeyListener(null);*/

        return listItemView;
    }
}
