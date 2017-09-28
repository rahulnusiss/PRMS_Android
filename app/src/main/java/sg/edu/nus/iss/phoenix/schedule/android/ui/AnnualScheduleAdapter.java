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

/**
 * Created by rahul on 9/19/2017.
 */

public class AnnualScheduleAdapter extends ArrayAdapter<AnnualSchedule> {

    public AnnualScheduleAdapter(@NonNull Context context, ArrayList<AnnualSchedule> annualSchedules) {
        super(context, 0, annualSchedules);
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
        AnnualSchedule currentAnnSch = getItem(position);

        TextView radioPSName = (TextView) listItemView.findViewById(R.id.maintain_schedule_spinner_year);
        Integer year = new Integer(currentAnnSch.getYear());
        radioPSName.setText(year.toString(), TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSName);

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
        AnnualSchedule currentAnnSch = getItem(position);

        TextView radioPSName = (TextView) listItemView.findViewById(R.id.maintain_schedule_spinner_year);
        //Integer year = new Integer(currentAnnSch.getYear());
        radioPSName.setText("Year", TextView.BufferType.NORMAL);
        setEditTextAttr(radioPSName);

        return listItemView;
    }

    private void setEditTextAttr( TextView iText ){
        iText.setKeyListener(null);
        iText.setClickable(false);
        iText.setFocusable(false);
        iText.setFocusableInTouchMode(false);
    }
}
