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

import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE;

/**
 * Created by rahul on 9/21/2017.
 */

public class UpdateScheduleDelegate extends AsyncTask<ProgramSlot, Void, Boolean> {

    // Tag for logging
    private static final String TAG = UpdateScheduleDelegate.class.getName();

    private final ScheduleController scheduleController;

    public UpdateScheduleDelegate(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
    }

    @Override
    protected Boolean doInBackground(ProgramSlot... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
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
            // Old
            json.put("name", params[0].getName());
            json.put("dateofProgram", params[0].getDateOfProgram().toString());
            json.put("duration", params[0].getDuration().intValue());
            json.put("startTime", params[0].getStartTime().toString());
            json.put("radioProgram", params[0].getRadioProgram());
            json.put("presenter", params[0].getPresenter());
            json.put("producer", params[0].getProducer());

            //Modified
            json.put("modifiedName", params[1].getName());
            json.put("modifiedDateofProgram", params[1].getDateOfProgram().toString());
            json.put("modifiedDuration", params[1].getDuration().intValue());
            json.put("modifiedStartTime", params[1].getStartTime().toString());
            json.put("modifiedRadioProgram", params[1].getRadioProgram());
            json.put("modifiedPresenter", params[1].getPresenter());
            json.put("modifiedProducer", params[1].getProducer());
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(512);
            Log.v(TAG, "Http POST response " + httpURLConnection.getResponseCode());
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
        scheduleController.scheduleModified(result);
    }
}
