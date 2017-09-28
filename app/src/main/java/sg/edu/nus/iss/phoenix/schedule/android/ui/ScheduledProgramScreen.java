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
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.maintainuser.entity.User;

public class ScheduledProgramScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = ScheduledProgramScreen.class.getName();

    private EditText radioPSName;
    private EditText radioPSDateofPr;
    private EditText radioPSDuration;
    private EditText radioPSStartTime;
    private Button btnSelectProducer;
    private Button btnSelectPresenter;
    private ProgramSlot ps2edit = null;
    private User selectedUser = null;
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
        btnSelectProducer = (Button) findViewById(R.id.maintain_schedule_producer_button);
        btnSelectPresenter = (Button) findViewById(R.id.maintain_schedule_presenter_button);

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
                //Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Please select a schedule", Toast.LENGTH_SHORT);
                //toast.show();
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

        boolean isValidValues = validateInput();
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
            ps2edit = tempPS;
            ControlFactory.getScheduleController().selectCreateSchedule(ps2edit);
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

    public void presenterRetrieved(User user) {
        selectedUser = user;
        btnSelectPresenter.setText(user.getUserName() + "(presenter)");
    }

    public void producerRetrieved(User user) {
        selectedUser = user;
        btnSelectProducer.setText(user.getUserName() + "(producer)");
    }

    private boolean validateInput(){
        String regexp = "^\\d{2}:\\d{2}:\\d{2}$";
        if( 0 == radioPSName.length() || 0 == radioPSDateofPr.length() || 0 == radioPSDuration.length() || 0 == radioPSStartTime.length()){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Invalid one or more values", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        // Check valid duration input
        else if(!String.valueOf(radioPSDuration.getText()).matches(regexp)){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Invalid Duration value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        else if(!String.valueOf(radioPSStartTime.getText()).matches(regexp)){
            Toast toast = Toast.makeText(ScheduledProgramScreen.this, "Invalid Start Time value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        return true;
    }
}
