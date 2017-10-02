package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduledProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE;

/**
 * Created by rahul on 9/20/2017.
 */

public class RetrieveScheduleDelegate extends AsyncTask<String, Void, String> {

    // Tag for logging
    private static final String TAG = RetrieveScheduleDelegate.class.getName();
    private SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private SimpleDateFormat tSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    private ScheduleController mScheduleController;
    private ReviewSelectScheduledProgramController mReviewSelectScheduledProgramController;

    public RetrieveScheduleDelegate(ScheduleController scheduleController) {
        this.mReviewSelectScheduledProgramController = null;
        this.mScheduleController = scheduleController;
    }

    public RetrieveScheduleDelegate(ReviewSelectScheduledProgramController reviewSelectScheduledProgramController) {
        this.mScheduleController = null;
        this.mReviewSelectScheduledProgramController = reviewSelectScheduledProgramController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_SCHEDULE).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        String uriString = builtUri.toString();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext())
            {
                try {
                    jsonResp = scanner.next();
                }
                finally{
                    // Do Nothing
                    return jsonResp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
            return jsonResp;
        }
        finally {
            if (urlConnection != null) urlConnection.disconnect();
            return jsonResp;
        }
        //return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        // From list of schedule craete List<ProgramSlot>
        //scheduleController.scheduleRetrieved();

        List<ProgramSlot> programSlots = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray psArray = reader.getJSONArray("psList");

                for (int i = 0; i < psArray.length(); i++) {
                    JSONObject psJson = psArray.getJSONObject(i);

                    // Convert to Date in try catch

                    int id = psJson.getInt("id");
                    String dateofProgram = psJson.getString("dateofProgram");
                    String duration = psJson.getString("duration");
                    Integer prDuration = getDuration(duration);
                    String programName = psJson.getString("programName");
                    String startTime = psJson.getString("startTime");
                    //String radioProgram = psJson.getString("radioProgram");
                    String presenter = psJson.getString("presenterId");
                    String producer = psJson.getString("producerId");
                    //Converting to Date format
                    Date dateOfPrTime = null;
                    Date sTime = null;
                    Integer startTimePr = getDuration(startTime);

                    try {
                        dateOfPrTime = mSDF.parse(dateofProgram);
                    }
                    catch(Exception e){
                        Log.v(TAG, "Date Parsing exception: " + e.getMessage());
                    }
                    java.sql.Date sqlDateofProgram = new java.sql.Date(dateOfPrTime.getTime());

                    try {
                        sTime = tSDF.parse(startTime);
                    }
                    catch(Exception e){
                        Log.v(TAG, "Time Parsing exception: " + e.getMessage());
                    }
                    java.sql.Time sqlTimeofProgram = new java.sql.Time(sTime.getTime());

                    programSlots.add(new ProgramSlot(id, programName, sqlDateofProgram, prDuration, sqlTimeofProgram,presenter,producer));//radioProgram,presenter,producer));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (mScheduleController != null)
            mScheduleController.scheduleRetrieved(programSlots);
        else if (mReviewSelectScheduledProgramController != null)
            mReviewSelectScheduledProgramController.programSlotsRetrieved(programSlots);
    }

    private Integer getDuration(String iDurationString){
        String[] data = iDurationString.split(":|\\+");


        int hours  = Integer.parseInt(data[0]);
        int minutes = Integer.parseInt(data[1]);
        int seconds = Integer.parseInt(data[2]);

        return (seconds + 60 * minutes + 3600 * hours);
    }

}
