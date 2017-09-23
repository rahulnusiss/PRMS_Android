package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

public class ScheduledProgramScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = ScheduledProgramScreen.class.getName();

    private EditText radioPSName;
    private EditText radioPSDateofPr;
    private EditText radioPSDuration;
    private EditText radioPSStartTime;
    private ProgramSlot ps2edit = null;
    KeyListener mRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_program_screen);
        // Find all relevant views that we will need to read user input from
        radioPSName = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        radioPSDateofPr = (EditText) findViewById(R.id.maintain_schedule_dateOfPr_text_view);
        radioPSDuration = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        radioPSStartTime = (EditText) findViewById(R.id.maintain_schedule_starttime_text_view);
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mRPNameEditTextKeyListener = radioPSName.getKeyListener();

    }

    public void createSchedule() {
        this.ps2edit = null;
        radioPSName.setText("", TextView.BufferType.EDITABLE);
        radioPSDateofPr.setText("", TextView.BufferType.EDITABLE);
        radioPSDuration.setText("", TextView.BufferType.EDITABLE);
        radioPSStartTime.setText("", TextView.BufferType.EDITABLE);
        radioPSName.setKeyListener(mRPNameEditTextKeyListener);
    }
}
