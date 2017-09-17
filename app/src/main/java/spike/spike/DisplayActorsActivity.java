package spike.spike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class DisplayActorsActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> actorsList;

    // url to get all products list
    private static final String URL = "http://www.cs.duke.edu/courses/compsci408/fall17/assign/warmup_db/?table=actor";

    // tags for ListView
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    private ListView actorsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items);

        actorsList = new ArrayList<>();

        // Loading products in background thread
        new LoadAllProducts().execute();

        actorsListView = (ListView) findViewById(R.id.list);

//        // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//        // Capture the layout's TextView and set the string as its text
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(message);
    }


    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    private class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DisplayActorsActivity.this);
            pDialog.setMessage("Loading actors from database. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting all actors from url
         */
        protected String doInBackground(String... args) {

            HttpURLConnection con = null;
            try {
                URL u = new URL(URL);
                con = (HttpURLConnection) u.openConnection();
                con.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                JSONArray actors = new JSONArray(sb.toString());

                for (int i = 0; i < actors.length(); i++) {
                    final JSONObject person = actors.getJSONObject(i);

                    String id = Integer.toString(person.getInt("actor_id"));
                    String name = person.getString("first_name") + " " + person.getString("last_name");
                    HashMap<String, String> map = new HashMap<>();
                    map.put(TAG_ID, id);
                    map.put(TAG_NAME, name);
                    actorsList.add(map);

//                    Log.d("ID", ": " + person.getInt("actor_id"));
//                    Log.d("FIRST: ", person.getString("first_name"));
//                    Log.d("LAST: ", person.getString("last_name"));
                }

            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.disconnect();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // update UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Update parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(
                            DisplayActorsActivity.this,
                            actorsList,
                            R.layout.list_item,
                            new String[]{TAG_ID, TAG_NAME},
                            new int[]{R.id.id, R.id.name});
                    // update ListView
                    actorsListView.setAdapter(adapter);
                }
            });
        }
    }
}
