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
import java.util.Locale;

import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 * Created by rahul on 9/19/2017.
 */

public class WeeklyScheduleAdapter extends ArrayAdapter<WeeklySchedule> {

    public WeeklyScheduleAdapter(@NonNull Context context, ArrayList<WeeklySchedule> weeklySchedules) {
        super(context, 0, weeklySchedules);
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_year, parent, false);
        }
        //    Word currentWord = getItem(position);
        WeeklySchedule currentWeeklySch = getItem(position);

        TextView radioPSName = (TextView) listItemView.findViewById(R.id.maintain_schedule_spinner_year);
        Integer week = new Integer(currentWeeklySch.getWeek());
        radioPSName.setText(week.toString(), TextView.BufferType.NORMAL);
        setTextViewAttr(radioPSName);

        return listItemView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_year, parent, false);
        }
        //    Word currentWord = getItem(position);
        WeeklySchedule currentWeeklySch = getItem(position);

        TextView radioPSName = (TextView) listItemView.findViewById(R.id.maintain_schedule_spinner_year);
        //Integer year = new Integer(currentAnnSch.getYear());
        radioPSName.setText("Week", TextView.BufferType.NORMAL);
        setTextViewAttr(radioPSName);

        return listItemView;
    }

    private void setTextViewAttr( TextView iText ){
        iText.setKeyListener(null);
        iText.setClickable(false);
        iText.setFocusable(false);
        iText.setFocusableInTouchMode(false);
    }
}
