package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

public class MaintainScheduleScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    private ScheduleAdapter mScheduleAdapter = null;
    private ListView mListView;
    private ProgramSlot mProgramSlot2Edit = null;
    private ProgramSlot mSelectedPS = null;
    private EditText mPSNameEditText;
    private boolean mIsEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_schedule_screen);

        ArrayList<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        mScheduleAdapter = new ScheduleAdapter(this, programSlots);

        mListView = (ListView) findViewById(R.id.schedule_list);
        mListView.setAdapter(mScheduleAdapter);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_schedule);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MaintainScheduleScreen.this);

                // Setting Dialog Title
                alertDialog.setTitle("Create Schedule");
                //
                final EditText input = new EditText(MaintainScheduleScreen.this);
                input.setText("Enter Schedule Name",TextView.BufferType.NORMAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line

                // Setting Positive "Save" Button
                alertDialog.setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                // Write your code here to execute after dialog
                                String psName = input.getText().toString();
                                ControlFactory.getScheduleController().selectCreateSchedule(new ProgramSlot(psName));
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
        });

        // Set list listeners
        setListViewListeners();

        // Hard coded to be removed
        //mScheduleAdapter.add(new ProgramSlot( "New1",new Date(),0,new Date()) );
        //mScheduleAdapter.add(new ProgramSlot( "New2",new Date(),0,new Date()));
        //mScheduleAdapter.add(new ProgramSlot( "New3",new Date(),0,new Date()) );
        //mScheduleAdapter.add(new ProgramSlot( "New4",new Date(),0,new Date()) );

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

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
        getMenuInflater().inflate(R.menu.menu_maintain_schedule, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        if (mProgramSlot2Edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_schedule_delete);
            menuItem.setVisible(false);
        }
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
                    alertDialogForModify();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_schedule_delete:
                Log.v(TAG, "Deleting schedule " + mProgramSlot2Edit.getName() + "...");
                ControlFactory.getScheduleController().selectDeleteSchedule(mProgramSlot2Edit);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_schedule_cancel:
                Log.v(TAG, "Canceling creating/editing schedule...");
                ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
                return true;
        }

        return true;
    }

    private void alertDialogForModify(){

        Context context = this.getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MaintainScheduleScreen.this);

        // Setting Dialog Title
        alertDialog.setTitle("Modify Schedule: " + mSelectedPS.getName());

        final EditText input1 = new EditText(MaintainScheduleScreen.this);
        input1.setText("Enter New Schedule Name",TextView.BufferType.NORMAL);
        final EditText input2 = new EditText(MaintainScheduleScreen.this);
        input2.setText("DateOfProgram: 2011-07-14T02:00:00+08:00 Format",TextView.BufferType.NORMAL);
        final EditText input3 = new EditText(MaintainScheduleScreen.this);
        input3.setText("Duration-00:00:00 format",TextView.BufferType.NORMAL);
        final EditText input4 = new EditText(MaintainScheduleScreen.this);
        input4.setText("StartTime: 2011-07-14T03:00:00+08:00 format",TextView.BufferType.NORMAL);
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
        alertDialog.setView(layout); // uncomment this line


        // Setting Positive "Save" Button
        alertDialog.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        // Write your code here to execute after dialog
                        String psName = input1.getText().toString();
                        mSelectedPS.setName(psName);
                        String psDateofPr = input2.getText().toString();
                        mSelectedPS.setDateOfProgram(psDateofPr);
                        String psDuration = input3.getText().toString();
                        mSelectedPS.setDuration(psDuration);
                        String psStartTime = input4.getText().toString();
                        mSelectedPS.setStartTime(psStartTime);
                        ControlFactory.getScheduleController().selectModifySchedule(mSelectedPS);
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

    public void displaySchedule(List<ProgramSlot> iProgramSlots){
        mScheduleAdapter.clear();
        for (int i = 0; i < iProgramSlots.size(); i++) {
            mScheduleAdapter.add(iProgramSlots.get(i));
        }
    }

    public void createSchedule() {
        this.mProgramSlot2Edit = null;
        mScheduleAdapter.add(new ProgramSlot("",new Date(),0,new Date()));
    }
}
