package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleList;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

public class MaintainScheduleScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    private ScheduleAdapter mScheduleAdapter = null;
    private AnnualScheduleAdapter mAnnualScheduleAdapter = null;
    private WeeklyScheduleAdapter mWeeklyScheduleAdapter = null;
    //private AnnualScheduleList mAnnualScheduleList;
    private ListView mListView;
    private ProgramSlot mSelectedPS = null;
    private boolean mWeekSelected = false;
    private boolean mYearSelected = false;

    /*public void setAnnualScheduleList(AnnualScheduleList iAnnScList){
        mAnnualScheduleList = iAnnScList;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_schedule_screen);

        ArrayList<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        mScheduleAdapter = new ScheduleAdapter(this, programSlots);

        mListView = (ListView) findViewById(R.id.schedule_list);
        mListView.setAdapter(mScheduleAdapter);

        // Annual schedule adapter
        ArrayList<AnnualSchedule> annSchedules = new ArrayList<AnnualSchedule>();
        mAnnualScheduleAdapter = new AnnualScheduleAdapter(this, annSchedules);
        mAnnualScheduleAdapter.setDropDownViewResource(R.layout.spinner_year);

        //Weekly Schedule adapter
        ArrayList<WeeklySchedule> weeklySchedules = new ArrayList<WeeklySchedule>();
        mWeeklyScheduleAdapter = new WeeklyScheduleAdapter(this, weeklySchedules);
        mAnnualScheduleAdapter.setDropDownViewResource(R.layout.spinner_year);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_schedule);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alertDialogDisplay("Create Schedule", true);
		        Intent intent = new Intent(MainController.getApp(), ScheduledProgramScreen.class);
		        MainController.displayScreen(intent);
            }
        });

        // Show all button
        Button clickButton = (Button) findViewById(R.id.button_showall);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekSelected = false;
                mYearSelected = false;
                displayAllProgramSlots();
            }
        });

        // Set list listeners
        setListViewListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Automatically gets all the program slots from backend using retrieve delegate
        ControlFactory.getScheduleController().onDisplayScheduleList(this);
    }

    private void setListViewListeners(){
        // Setup the item click selection listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                ProgramSlot ps = (ProgramSlot) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                mSelectedPS = ps;
            }

        });

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                ProgramSlot ps = (ProgramSlot) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                mSelectedPS = ps;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        final Menu menu2 = menu;
        getMenuInflater().inflate(R.menu.menu_maintain_schedule, menu);
        MenuItem item = menu.findItem(R.id.spinner_year);
        Spinner spinner_year = (Spinner) MenuItemCompat.getActionView(item);
        spinner_year.setAdapter(mAnnualScheduleAdapter); // set the adapter to provide layout of rows and content
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // If year not selected then show all
                mWeekSelected = false;
                if (!mYearSelected){
                    displayAllProgramSlots();
                    mYearSelected = true;
                }
                // If year selected show selection
                else {
                    AnnualSchedule as = (AnnualSchedule) parentView.getItemAtPosition(position);
                    setWeeklyScheduleSpinner(as, menu2);
                    showAllWeeklySchedule(as);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                displayAllProgramSlots();
            }

        });


        return true;
    }

    private void setWeeklyScheduleSpinner(final AnnualSchedule iAnnSchedule, Menu menu){
        mWeeklyScheduleAdapter.clear();
        for ( int i = 0; i < iAnnSchedule.retrieveAllWeeklySchedules().size() ; ++i){
            WeeklySchedule week = iAnnSchedule.retrieveAllWeeklySchedules().get(i);
            mWeeklyScheduleAdapter.add(week);
        }
        // Week Spinner
        MenuItem itemWeek = menu.findItem(R.id.spinner_week);
        Spinner spinner_week = (Spinner) MenuItemCompat.getActionView(itemWeek);
        spinner_week.setAdapter(mWeeklyScheduleAdapter); // set the adapter to provide layout of rows and content
        spinner_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (!mWeekSelected){
                    mWeekSelected = true;
                    showAllWeeklySchedule(iAnnSchedule);
                }
                else {
                    WeeklySchedule ws = (WeeklySchedule) parentView.getItemAtPosition(position);
                    List<ProgramSlot> psList = ws.retrieveAllProgramSlot();
                    displaySelectiveSchedule(psList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                showAllWeeklySchedule(iAnnSchedule);
            }

        });

    }

    // Display all weeks schedule within this input year
    private void showAllWeeklySchedule(AnnualSchedule iAnnSchedule){
        List<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        for ( int i = 0; i < iAnnSchedule.retrieveAllWeeklySchedules().size() ; ++i){
            WeeklySchedule week = iAnnSchedule.retrieveAllWeeklySchedules().get(i);
                programSlots.addAll(week.retrieveAllProgramSlot());
        }
        displaySelectiveSchedule(programSlots);
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        /*if (mProgramSlot2Edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_schedule_delete);
            menuItem.setVisible(false);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_schedule_modify:
                // Save radio program.
                if (mSelectedPS == null) { // Nothing selected from list.
                    Log.v(TAG, "No schedule selected"  + "...");
                    Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Please select a schedule", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else { // Edited.
                    Log.v(TAG, "Modifying schedule " + mSelectedPS.getName()+ "...");
                    // Modifying in alert dialog
                    Intent intent = new Intent(MainController.getApp(), ScheduledProgramScreen.class);
                    //intent.putExtra("ModifySchedule", (Serializable)mSelectedPS );
                    ControlFactory.getScheduleController().setProgramSlot(mSelectedPS);
                    //Bundle bundle = new Bundle();
                    //bundle.putSerializable("ModifySchedule", mSelectedPS);
                    //intent.putExtras(bundle);
                    MainController.displayScreen(intent);
                    //alertDialogDisplay("Modify Schedule");
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_schedule_delete:
                if (mSelectedPS == null) { // Nothing selected from list.
                    Log.v(TAG, "No schedule selected"  + "...");
                    Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Please select a schedule to delete", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Log.v(TAG, "Deleting schedule " + mSelectedPS.getName() + "...");
                    ControlFactory.getScheduleController().selectDeleteSchedule(mSelectedPS);
                }
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_schedule_cancel:
                Log.v(TAG, "Canceling creating/editing schedule...");
                ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
                return true;
            case R.id.action_schedule_copy:
                Log.v(TAG, "Copying schedule...");
                if (mSelectedPS == null) { // Nothing selected from list.
                    Log.v(TAG, "No schedule selected"  + "...");
                    Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Please select a schedule to copy", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    ControlFactory.getScheduleController().selectCopySchedule(mSelectedPS);
                }
                return true;
        }

        return true;
    }

    private void alertDialogDisplay(String iTitle){

        Context context = this.getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MaintainScheduleScreen.this);
        String s1 = "", s2 ="", s3 = "", s4 ="";

        s1 = mSelectedPS.getName();
        s2 = mSelectedPS.getDateOfProgram().toString();
        s3 = mSelectedPS.getDuration().toString();
        s4 = mSelectedPS.getStartTime().toString();

        // Setting Dialog Title
        alertDialog.setTitle(iTitle);

        final EditText input1 = new EditText(MaintainScheduleScreen.this);
        input1.setText(s1,TextView.BufferType.NORMAL);
        final EditText input2 = new EditText(MaintainScheduleScreen.this);
        input2.setText(s2,TextView.BufferType.NORMAL);
        final EditText input3 = new EditText(MaintainScheduleScreen.this);
        input3.setText(s3,TextView.BufferType.NORMAL);
        final EditText input4 = new EditText(MaintainScheduleScreen.this);
        input4.setText(s4,TextView.BufferType.NORMAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // Set edit text parameters
        input1.setLayoutParams(lp);
        input2.setLayoutParams(lp);
        input3.setLayoutParams(lp);
        input4.setLayoutParams(lp);

        // Add edit texts to layout
        layout.addView(input1);
        layout.addView(input2);
        layout.addView(input3);
        layout.addView(input4);

        // Set alert dialog view to layout
        alertDialog.setView(layout);

        // Setting Positive "Save" Button
        alertDialog.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        // Write your code here to execute after dialog
                        boolean isValid = validateModifyInput(input1, input2, input3, input4);
                        if (isValid) {
                            ProgramSlot tempPS = new ProgramSlot("");
                            String psName = input1.getText().toString();
                            tempPS.setName(psName);
                            //mSelectedPS.setName(psName);
                            String psDateofPr = input2.getText().toString();
                            tempPS.setDateOfProgram(psDateofPr);
                            //mSelectedPS.setDateOfProgram(psDateofPr);
                            String psDuration = input3.getText().toString();
                            tempPS.setDuration(psDuration);
                            //mSelectedPS.setDuration(psDuration);
                            String psStartTime = input4.getText().toString();
                            tempPS.setStartTime(psStartTime);
                            //mSelectedPS.setStartTime(psStartTime);

                            // Check if slots overlap for current modifying schedule.
                            if (checkProgramSlotOverlap(tempPS)) {
                                Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Program slot already assigned. Please change timings", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                mSelectedPS = tempPS;
                                ControlFactory.getScheduleController().selectModifySchedule(mSelectedPS,null) ; // null TBD
                            }
                        }
                        else{
                            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Unable to modify program slot", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

        // Setting Negative "CANCEL" Button
        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        alertDialog.show();
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
                    if (mSelectedPS == slots.get(k)) {
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

    private boolean validateModifyInput(EditText inputName, EditText inputDateOfPr, EditText inputDuration, EditText inputStartTime){
        String regexp = "^\\d{2}:\\d{2}:\\d{2}$";
        if( 0 == inputName.length() || 0 == inputDateOfPr.length() || 0 == inputDuration.length() || 0 == inputStartTime.length()){
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Invalid one or more values", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        // Check valid duration input
        else if(!String.valueOf(inputDuration.getText()).matches(regexp) || !ScheduleUtility.validateDuration(inputDuration.getText().toString())){
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Invalid Duration value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        else if(!String.valueOf(inputStartTime.getText()).matches(regexp) || !ScheduleUtility.validateDuration(inputStartTime.getText().toString())){
            Toast toast = Toast.makeText(MaintainScheduleScreen.this, "Invalid Start Time value", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        return true;
    }

    public void displaySchedule(List<ProgramSlot> iProgramSlots){
        mScheduleAdapter.clear();
        for (int i = 0; i < iProgramSlots.size(); i++) {
            mScheduleAdapter.add(iProgramSlots.get(i));
        }

        List<AnnualSchedule> annualSchedules = ControlFactory.getScheduleController().getListAnnualSchedule().retrieveAllAnnualSchedules();
        for (int i = 0; i < annualSchedules.size(); i++) {
            mAnnualScheduleAdapter.add(annualSchedules.get(i));
        }
    }

    private void displayAllProgramSlots(){
        mScheduleAdapter.clear();
        List<AnnualSchedule> annualSchedules = ControlFactory.getScheduleController().getListAnnualSchedule().retrieveAllAnnualSchedules();
        for (int i = 0; i < annualSchedules.size(); i++) {
            AnnualSchedule annSch = annualSchedules.get(i);
            List<WeeklySchedule> weeks = annSch.retrieveAllWeeklySchedules();
            int size2 = weeks.size();
            for (int j = 0; j < size2; ++j){
                WeeklySchedule week = weeks.get(j);
                List<ProgramSlot> slots = week.retrieveAllProgramSlot();
                int size3 = slots.size();
                for (int k = 0; k < size3; ++k){
                    mScheduleAdapter.add(slots.get(k));
                }
            }
        }
    }

    private void displaySelectiveSchedule(List<ProgramSlot> iProgramSlots){
        mScheduleAdapter.clear();
        for (int i = 0; i < iProgramSlots.size(); i++) {
            mScheduleAdapter.add(iProgramSlots.get(i));
        }
    }

   /* public void createSchedule() {
        mScheduleAdapter.add(new ProgramSlot("",new Date(),0,new Date()));
    }*/
}
