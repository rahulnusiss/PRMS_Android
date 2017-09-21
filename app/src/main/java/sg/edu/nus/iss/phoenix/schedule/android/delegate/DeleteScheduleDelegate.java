package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.os.AsyncTask;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;

/**
 * Created by rahul on 9/21/2017.
 */

public class DeleteScheduleDelegate extends AsyncTask<String, Void, Boolean> {

    // Tag for logging
    private static final String TAG = DeleteScheduleDelegate.class.getName();

    private final ScheduleController scheduleController;

    public DeleteScheduleDelegate(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        scheduleController.scheduleDeleted(result);
    }
}
