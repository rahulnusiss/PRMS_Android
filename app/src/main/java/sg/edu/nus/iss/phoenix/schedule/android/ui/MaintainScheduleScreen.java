package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

import static sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility.formatTime;

public class MaintainScheduleScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    private EditText radioPSDateofPr;
    private EditText radioPSDuration;
    private EditText radioPSStartTime;
    private EditText radioPSEndTime;
    private Button btnSelectProducer;
    private Button btnSelectPresenter;
    private Button btnRadioProgram;
    private ProgramSlot ps2edit = null;
    private User selectedUser = null;
    private boolean isCreate = false;
    private KeyListener mRPNameEditTextKeyListener = null;
    private Calendar dateCalendar = Calendar.getInstance();
    private Calendar startTimeCalendar = Calendar.getInstance();
    private Calendar endTimeCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_program_screen);

        // Get modify schedule
        //Bundle extras = getIntent().getExtras();
        ps2edit = ControlFactory.getScheduleController().getProgramSlot();

        // Find all relevant views that we will need to read user input from
        //radioPSName = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        radioPSDateofPr = (EditText) findViewById(R.id.maintain_schedule_dateOfPr_text_field);
        radioPSDuration = (EditText) findViewById(R.id.maintain_schedule_duration_text_field);
        radioPSStartTime = (EditText) findViewById(R.id.maintain_schedule_starttime_text_field);
        radioPSEndTime = (EditText) findViewById(R.id.maintain_schedule_endtime_text_field);
        btnSelectProducer = (Button) findViewById(R.id.maintain_schedule_producer_button);
        btnSelectPresenter = (Button) findViewById(R.id.maintain_schedule_presenter_button);
        btnRadioProgram = (Button) findViewById(R.id.maintain_schedule_radioProgram_button);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                dateCalendar.set(Calendar.YEAR, year);
                dateCalendar.set(Calendar.MONTH, monthOfYear);
                dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                radioPSDateofPr.setText(ScheduleUtility.formatDate(year, monthOfYear, dayOfMonth), TextView.BufferType.EDITABLE);
            }

        };

        radioPSDateofPr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(MaintainScheduleScreen.this, date, dateCalendar
                        .get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH),
                        dateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startTimeCalendar.set(Calendar.MINUTE, minute);
                radioPSStartTime.setText(ScheduleUtility.formatTime(hourOfDay, minute), TextView.BufferType.EDITABLE);
            }
        };

        radioPSStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new TimePickerDialog(MaintainScheduleScreen.this, time, startTimeCalendar
                        .get(Calendar.HOUR_OF_DAY), startTimeCalendar.get(Calendar.MINUTE), false).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time1 = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endTimeCalendar.set(Calendar.MINUTE, minute);
                radioPSEndTime.setText(ScheduleUtility.formatTime(hourOfDay, minute), TextView.BufferType.EDITABLE);

                long diff = endTimeCalendar.getTimeInMillis() - startTimeCalendar.getTimeInMillis();
                long diffMinutes = diff / (60 * 1000);

                radioPSDuration.setText(String.format("%d", diffMinutes), TextView.BufferType.EDITABLE);
            }
        };

        radioPSEndTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new TimePickerDialog(MaintainScheduleScreen.this, time1, endTimeCalendar
                        .get(Calendar.HOUR_OF_DAY), endTimeCalendar.get(Calendar.MINUTE), false).show();
            }
        });

        btnSelectProducer.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase("producer", (MaintainScheduleScreen)view.getContext());

            }
        });
        btnSelectPresenter.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase("presenter", (MaintainScheduleScreen)view.getContext());
            }
        });

        btnRadioProgram.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectProgramController().startUseCase((MaintainScheduleScreen)view.getContext());
            }
        });
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        // mRPNameEditTextKeyListener = radioPSDateofPr.getKeyListener();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getScheduleController().onDisplaySchedule(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        if (this.ps2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);

            menuItem = menu.findItem(R.id.action_copy);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    public void programRetrieved(String iPrName) {
        btnRadioProgram.setText(iPrName + "(program)");
        ps2edit.setName(iPrName);
        ControlFactory.getScheduleController().setRadioProgram(ps2edit.getName());
    }

    public void presenterRetrieved(User user) {
        selectedUser = user;
        ps2edit.setPresenter(user.getUserId());
        btnSelectPresenter.setText(user.getUserName() + "(presenter)");
    }

    public void producerRetrieved(User user) {
        selectedUser = user;
        ps2edit.setProducer(user.getUserId());
        btnSelectProducer.setText(user.getUserName() + "(producer)");
    }

    private boolean validateFormat(){
        String regexp = "^\\d{2}:\\d{2}:\\d{2}$";
        if( 0 == btnRadioProgram.getText().length() || 0 == radioPSDateofPr.length() || 0 == radioPSDuration.length() || 0 == radioPSStartTime.length()){
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Invalid one or more values", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        // Check valid duration input
//        else if(!String.valueOf(radioPSDuration.getText()).matches(regexp) || !ScheduleUtility.validateDuration(radioPSDuration.getText().toString())){
//            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Invalid Duration value", Toast.LENGTH_SHORT);
//            toast.show();
//            return false;
//        }

        else if(this.endTimeCalendar.getTimeInMillis() - this.startTimeCalendar.getTimeInMillis() <= 0){
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Invalid Start Time value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

//        else if(!ScheduleUtility.validateTime(radioPSDateofPr.getText().toString(),ScheduleUtility.parseDuration(radioPSStartTime.getText().toString()))){
//            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Date and time entered is invalid, should be after current time.", Toast.LENGTH_SHORT);
//            toast.show();
//            return false;
//        }

        return true;
    }

    // Function to check overlap while modifying. It should not check with itself while modifying program slot.
    private boolean checkProgramSlotOverlap(ProgramSlot iProgramSlot){
        boolean status = false;
        List<AnnualSchedule> annualSchedules = ControlFactory.getScheduleController().getListAnnualSchedule().retrieveAllAnnualSchedules();
        for (int i = 0; i < annualSchedules.size(); i++) {
            AnnualSchedule annSch = annualSchedules.get(i);
            List<WeeklySchedule> weeks = annSch.retrieveAllWeeklySchedules();
            int size2 = weeks.size();
            for (int j = 0; j < size2; ++j) {
                WeeklySchedule week = weeks.get(j);
                List<ProgramSlot> slots = week.retrieveAllProgramSlot();
                int size3 = slots.size();
                for (int k = 0; k < size3; ++k) {
                    if (ps2edit == slots.get(k)) {
                        continue;
                    }
                    else{
                        status = ScheduleUtility.isProgramSlotOverlap(iProgramSlot,slots.get(k));
                        if(status){ return status;}
                    }
                }
            }
        }
        return status;
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing radio program...");
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                selectSaveSchedule();

                return true;
            case R.id.action_delete:
                selectDeleteSchedule();

                return true;
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing schedule...");
                this.finish();
                return true;
            case R.id.action_copy:
                selectCopySchedule();

        }

        return true;
    }

    private void selectCopySchedule() {
        ControlFactory.getScheduleController().selectCopySchedule(ps2edit);
    }

    private void selectDeleteSchedule() {
        ControlFactory.getScheduleController().selectDeleteSchedule(this.ps2edit);
    }

    private void selectSaveSchedule() {
        boolean isValidValues = validateFormat();
        if ( !isValidValues ){
            return;
        }

        ProgramSlot tempPS = new ProgramSlot("");
        String psName = btnRadioProgram.getText().toString();
        tempPS.setName(psName);

        String psDateofPr = radioPSDateofPr.getText().toString();
        tempPS.setDateOfProgram(new java.sql.Date(dateCalendar.getTimeInMillis()));

        String psDuration = radioPSDuration.getText().toString();
        tempPS.setDuration(psDuration);

        String psStartTime = radioPSStartTime.getText().toString();
        tempPS.setStartTime(new java.sql.Time(startTimeCalendar.getTimeInMillis()));

        String psPresenter = btnSelectPresenter.getText().toString();
        tempPS.setPresenter(psPresenter);

        String psProducer = btnSelectProducer.getText().toString();
        tempPS.setProducer(psProducer);

        // Check if slots overlap for current modifying schedule.
        if (checkProgramSlotOverlap(tempPS)) {
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Program slot already assigned. Please change timings", Toast.LENGTH_SHORT);
            toast.show();
        }

        if ( tempPS.isProgramSlotAssigned(ControlFactory.getScheduleController().getListAnnualSchedule())){
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Program slot already assigned. Please change timings", Toast.LENGTH_SHORT);
            toast.show();
            isValidValues = false;
        }
        else {
            // If intent Modify recieved null then create
            if( this.ps2edit == null ) {
                tempPS.setName(ps2edit.getName());
                tempPS.setPresenter(ps2edit.getPresenter());
                tempPS.setProducer(ps2edit.getProducer());
                ControlFactory.getScheduleController().selectCreateSchedule(tempPS);
                isCreate = false;
            }
            else{ // If ps2EDIT RETRIEVED then modify
                tempPS.setID(ps2edit.getID());
                ControlFactory.getScheduleController().selectModifySchedule(tempPS);
            }
        }
    }

    public void createSchedule() {
        ps2edit = new ProgramSlot("");
        isCreate = true;
        radioPSDateofPr.setText("", TextView.BufferType.EDITABLE);
        radioPSDuration.setText("", TextView.BufferType.EDITABLE);
        radioPSStartTime.setText("", TextView.BufferType.EDITABLE);
        radioPSDateofPr.setKeyListener(mRPNameEditTextKeyListener);
    }

    public void editSchedule(ProgramSlot slot) {
        //radioPSName.setText(ps2edit.getName());
        radioPSDateofPr.setText(slot.getDateOfProgram().toString());
        radioPSStartTime.setText(formatTime(slot.getStartTime().getHours(), slot.getStartTime().getMinutes()), TextView.BufferType.EDITABLE);
        this.startTimeCalendar.set(1, 1, 1, slot.getStartTime().getHours(), slot.getStartTime().getMinutes());

        endTimeCalendar.set(Calendar.HOUR_OF_DAY, slot.getStartTime().getHours());
        endTimeCalendar.set(Calendar.MINUTE, slot.getStartTime().getMinutes());
        endTimeCalendar.add(Calendar.MINUTE, slot.getDuration().intValue());
        radioPSEndTime.setText(formatTime(endTimeCalendar.getTime().getHours(), endTimeCalendar.getTime().getMinutes()), TextView.BufferType.EDITABLE);


        radioPSDuration.setText(slot.getDuration().toString(), TextView.BufferType.EDITABLE);
        btnSelectProducer.setText(slot.getProducer());
        btnSelectPresenter.setText(slot.getPresenter());
        btnRadioProgram.setText(slot.getName());
        btnRadioProgram.setEnabled(false);
        // Reset controllers program slot
        ControlFactory.getScheduleController().setProgramSlot(null);
    }
}
