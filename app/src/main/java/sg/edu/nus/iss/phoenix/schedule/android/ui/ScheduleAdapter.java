package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
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

        /*EditText radioPSName = (EditText)listItemView.findViewById(R.id.maintain_schedule_name_text_view);
        radioPSName.setText(currentPS.getName(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSName);*/

        EditText radioPSDateofPr = (EditText)listItemView.findViewById(R.id.maintain_schedule_dateOfPr_text_field);
        radioPSDateofPr.setText(currentPS.getDateOfProgram().toString(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSDateofPr);

//        EditText radioPSDuration = (EditText)listItemView.findViewById(R.id.maintain_schedule_duration_text_field);
//        radioPSDuration.setText(currentPS.getDuration(), TextView.BufferType.NORMAL);
//        setEditTextAttr(radioPSDuration);

        EditText radioPSStartTime = (EditText)listItemView.findViewById(R.id.maintain_schedule_starttime_text_field);
        radioPSStartTime.setText(currentPS.getStartTime().toString(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSStartTime);

        listItemView.findViewById(R.id.maintain_schedule_endtime_text_field).setVisibility(View.GONE);
        listItemView.findViewById(R.id.maintain_schedule_endtime_text_view).setVisibility(View.GONE);

        Button btnRadioProgram = (Button) listItemView.findViewById(R.id.maintain_schedule_radioProgram_button);
        Button btnSelectProducer = (Button) listItemView.findViewById(R.id.maintain_schedule_producer_button);
        Button btnSelectPresenter = (Button) listItemView.findViewById(R.id.maintain_schedule_presenter_button);
        btnRadioProgram.setText(currentPS.getName());
        btnSelectPresenter.setText(currentPS.getPresenter());
        btnSelectProducer.setText(currentPS.getProducer());
        setButtonsAttr(btnRadioProgram);
        setButtonsAttr(btnSelectPresenter);
        setButtonsAttr(btnSelectProducer);

        return listItemView;
    }

    private void setEditTextAttr( EditText iEditText ){
        iEditText.setKeyListener(null);
        iEditText.setClickable(false);
        iEditText.setFocusable(false);
        iEditText.setFocusableInTouchMode(false);
    }

    private void setButtonsAttr( Button iButton ){
        iButton.setEnabled(false);
        iButton.setKeyListener(null);
        iButton.setClickable(false);
        iButton.setFocusable(false);
        iButton.setFocusableInTouchMode(false);
    }
}
