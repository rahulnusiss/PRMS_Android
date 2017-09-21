package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.os.AsyncTask;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;

/**
 * Created by rahul on 9/20/2017.
 */

public class RetrieveScheduleDelegate extends AsyncTask<String, Void, String> {

    // Tag for logging
    private static final String TAG = RetrieveScheduleDelegate.class.getName();

    private final ScheduleController scheduleController;

    public RetrieveScheduleDelegate(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
    }

    @Override
    protected String doInBackground(String... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        // From list of schedule craete List<ProgramSlot>
        //scheduleController.scheduleRetrieved();

    }

}
