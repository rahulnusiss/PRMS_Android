package sg.edu.nus.iss.phoenix.maintainuser.android.delegate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import sg.edu.nus.iss.phoenix.maintainuser.android.controller.*;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * @author jackle
 * @version 1.0
 * @created 01-Sep-2017 5:31:27 PM
 */
public class DeleteUserDelegate extends AsyncTask<String, Void, String> {

	// Tag for logging
	private static final String TAG = DeleteUserDelegate.class.getName();

	private final UserController userController;

	public DeleteUserDelegate(UserController userController) {
		this.userController = userController;
	}

	@Override
	protected String doInBackground(String... params) {
		// Encode the name of radio program in nullcase of the presence of special characters.
		String name = null;
		try {
			name = URLEncoder.encode(params[0], "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.v(TAG, e.getMessage());
			return null;
		}
		Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
		builtUri = Uri.withAppendedPath(builtUri,"delete").buildUpon().build();
		builtUri = Uri.withAppendedPath(builtUri, name).buildUpon().build();
		Log.v(TAG, builtUri.toString());
		URL url = null;
		try {
			url = new URL(builtUri.toString());
		} catch (MalformedURLException e) {
			Log.v(TAG, e.getMessage());
			return null;
		}

		boolean success = false;
		String jsonResp = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setInstanceFollowRedirects(false);
			httpURLConnection.setRequestMethod("DELETE");
			httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
			httpURLConnection.setUseCaches (false);
			System.out.println(httpURLConnection.getResponseCode());
			Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
			success = true;
			InputStream in = httpURLConnection.getInputStream();

			Scanner scanner = new Scanner(in);
			scanner.useDelimiter("\\A");
			if (scanner.hasNext()) jsonResp = scanner.next();
		} catch (IOException exception) {
			Log.v(TAG, exception.getMessage());
		} finally {
			if (httpURLConnection != null) httpURLConnection.disconnect();
		}
		if ( !success ){ jsonResp = null;}

		return jsonResp;
	}

	@Override
	protected void onPostExecute(String jsonResp) {
		Boolean result = false;
		try{
			JSONObject reader = new JSONObject(jsonResp);
			boolean status = reader.getBoolean("status");
			if (status){result = true;}
		}
		catch(Exception e){

		}
		userController.userDeleted(result.booleanValue());
	}
}//end DeleteUserDelegate