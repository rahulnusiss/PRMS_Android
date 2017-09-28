package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.utilities.ScheduleUtility;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE;

/**
 * Created by rahul on 9/21/2017.
 */

public class CreateScheduleDelegate extends AsyncTask<ProgramSlot,Void,Boolean> {

    // Tag for logging
    private static final String TAG = CreateScheduleDelegate.class.getName();

    private final ScheduleController scheduleController;
    private boolean isCopy = false;

    public CreateScheduleDelegate(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
    }

    public CreateScheduleDelegate(ScheduleController scheduleController, boolean isCopy) {
        this.scheduleController = scheduleController;
        this.isCopy = isCopy;
    }

    @Override
    protected Boolean doInBackground(ProgramSlot... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE).buildUpon().build();
        if(isCopy){
            builtUri = Uri.withAppendedPath(builtUri,"copy").buildUpon().build();
        }
        // Create schedule
        else {
            builtUri = Uri.withAppendedPath(builtUri, "create").buildUpon().build();
        }
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("name", params[0].getName());
            json.put("dateofProgram", params[0].getDateOfProgram().toString());
            json.put("duration", ScheduleUtility.parseDuration(params[0].getDuration()));
            json.put("startTime", ScheduleUtility.parseDuration(params[0].getStartTime()));
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(256);
            Log.v(TAG, "Http PUT response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        scheduleController.scheduleCreated(result);
    }
}
