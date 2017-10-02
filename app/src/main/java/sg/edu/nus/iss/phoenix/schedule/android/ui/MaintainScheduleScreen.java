package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

public class MaintainScheduleScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    private ScheduleAdapter mScheduleAdapter = null;
    private AnnualScheduleAdapter mAnnualScheduleAdapter = null;
    private WeeklyScheduleAdapter mWeeklyScheduleAdapter = null;
    private ListView mListView;
    private ProgramSlot mSelectedPS = null;
    private boolean mWeekSelected = false;
    private boolean mYearSelected = false;

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_schedule_view:
                Intent intent = new Intent(MainController.getApp(), ScheduledProgramScreen.class);
                ControlFactory.getScheduleController().setProgramSlot(mSelectedPS);
                MainController.displayScreen(intent);
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
