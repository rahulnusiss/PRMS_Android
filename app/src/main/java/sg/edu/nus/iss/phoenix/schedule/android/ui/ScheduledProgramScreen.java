package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ReviewSelectProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

public class ScheduledProgramScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = ScheduledProgramScreen.class.getName();

    private EditText radioPSName;
    private EditText radioPSDateofPr;
    private EditText radioPSDuration;
    private EditText radioPSStartTime;
    private Button btnSelectProducer;
    private Button btnSelectPresenter;
    private Button btnRadioProgram;
    private ProgramSlot ps2edit = null;
    private User selectedUser = null;
    KeyListener mRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_program_screen);

        // Get modify schedule
        //Bundle extras = getIntent().getExtras();
        ps2edit = ControlFactory.getScheduleController().getProgramSlot();
        if( ps2edit != null)
             ControlFactory.getScheduleController().setRadioProgram(ps2edit.getRadioProgram());

        // Find all relevant views that we will need to read user input from
        radioPSName = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        radioPSDateofPr = (EditText) findViewById(R.id.maintain_schedule_dateOfPr_text_view);
        radioPSDuration = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        radioPSStartTime = (EditText) findViewById(R.id.maintain_schedule_starttime_text_view);
        btnSelectProducer = (Button) findViewById(R.id.maintain_schedule_producer_button);
        btnSelectPresenter = (Button) findViewById(R.id.maintain_schedule_presenter_button);
        btnRadioProgram = (Button) findViewById(R.id.maintain_schedule_radioProgram_button);

        if ( null != ps2edit){
            radioPSName.setText(ps2edit.getName());
            radioPSDateofPr.setText(ps2edit.getDateOfProgram().toString());
            radioPSStartTime.setText(ScheduleUtility.parseDuration(ps2edit.getStartTime()));
            radioPSDuration.setText(ScheduleUtility.parseDuration(ps2edit.getDuration()));
            // Reset controllers program slot
            ControlFactory.getScheduleController().setProgramSlot(null);
        }

        btnSelectProducer.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase("producer", (ScheduledProgramScreen)view.getContext());

            }
        });
        btnSelectPresenter.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase("presenter", (ScheduledProgramScreen)view.getContext());
            }
        });

        btnRadioProgram.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectProgramController().startUseCase((ScheduledProgramScreen)view.getContext());
            }
        });
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mRPNameEditTextKeyListener = radioPSName.getKeyListener();

    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_delete);
        menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                boolean isSaveOk = saveProgramSlot();
                //Log.v(TAG, "No schedule selected"  + "...");
                return isSaveOk;
            // Respond to a click on the "Delete" menu option
            case R.id.action_schedule_delete:
                this.finish();
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_schedule_cancel:
                Log.v(TAG, "Canceling creating/editing schedule...");
                ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
                return true;
        }

        return true;
    }

    private boolean saveProgramSlot(){

        boolean isValidValues = validateFormat();
        if ( !isValidValues ){
            return false;
        }

        ProgramSlot tempPS = new ProgramSlot("");
        String psName = radioPSName.getText().toString();
        tempPS.setName(psName);

        String psDateofPr = radioPSDateofPr.getText().toString();
        tempPS.setDateOfProgram(psDateofPr);

        String psDuration = radioPSDuration.getText().toString();
        tempPS.setDuration(psDuration);

        String psStartTime = radioPSStartTime.getText().toString();
        tempPS.setStartTime(psStartTime);

        if ( tempPS.isProgramSlotAssigned(ControlFactory.getScheduleController().getListAnnualSchedule())){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Program slot already assigned. Please change timings", Toast.LENGTH_SHORT);
            toast.show();
            isValidValues = false;
        }
        else {
            // If intent Modify recieved null then create
            if( ps2edit == null) {
                ControlFactory.getScheduleController().selectCreateSchedule(tempPS);
            }
            else{ // If ps2EDIT RETRIEVED then modify
                ControlFactory.getScheduleController().selectModifySchedule(ps2edit, tempPS);
            }
        }

        return isValidValues;
    }

    public void createSchedule() {
        this.ps2edit = null;
        radioPSName.setText("", TextView.BufferType.EDITABLE);
        radioPSDateofPr.setText("", TextView.BufferType.EDITABLE);
        radioPSDuration.setText("", TextView.BufferType.EDITABLE);
        radioPSStartTime.setText("", TextView.BufferType.EDITABLE);
        radioPSName.setKeyListener(mRPNameEditTextKeyListener);
    }

    public void programRetrieved(String iPrName) {
        btnRadioProgram.setText(iPrName + "(presenter)");
        ControlFactory.getScheduleController().setRadioProgram(ps2edit.getRadioProgram());
        ps2edit.setRadioProgram(iPrName);
    }

    public void presenterRetrieved(User user) {
        selectedUser = user;
        btnSelectPresenter.setText(user.getUserName() + "(presenter)");
    }

    public void producerRetrieved(User user) {
        selectedUser = user;
        btnSelectProducer.setText(user.getUserName() + "(producer)");
    }

    private boolean validateFormat(){
        String regexp = "^\\d{2}:\\d{2}:\\d{2}$";
        if( 0 == radioPSName.length() || 0 == radioPSDateofPr.length() || 0 == radioPSDuration.length() || 0 == radioPSStartTime.length()){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Invalid one or more values", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        // Check valid duration input
        else if(!String.valueOf(radioPSDuration.getText()).matches(regexp) || !ScheduleUtility.validateDuration(radioPSDuration.getText().toString())){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Invalid Duration value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        else if(!String.valueOf(radioPSStartTime.getText()).matches(regexp) || !ScheduleUtility.validateDuration(radioPSStartTime.getText().toString())){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Invalid Start Time value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        else if(!ScheduleUtility.validateTime(radioPSDateofPr.getText().toString(),ScheduleUtility.parseDuration(radioPSStartTime.getText().toString()))){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Date and time entered is invalid, should be after current time.", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        return true;
    }
}
