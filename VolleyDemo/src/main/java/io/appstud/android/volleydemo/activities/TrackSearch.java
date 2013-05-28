package io.appstud.android.volleydemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.appstud.android.volleydemo.R;
import io.appstud.android.volleydemo.adapters.TrackAdapter;
import io.appstud.android.volleydemo.models.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrackSearch extends Activity {

    List<Track> mTracks;
    RequestQueue mRequestQueue;
    ListView mListView;
    TrackAdapter mTrackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        mListView = (ListView) findViewById(R.id.lv_track_search);

        mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://itunes.apple.com/search?term=jack+johnson&limit=25&entity=song";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    mTracks = new Gson().fromJson(results.toString(), new TypeToken<List<Track>>() {
                    }.getType());
                    mTrackAdapter = new TrackAdapter(TrackSearch.this, mTracks);
                    mListView.setAdapter(mTrackAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Something went wrong
                Log.e("Volley Error : ", volleyError.getMessage());
            }
        }
        );

        mRequestQueue.add(jsonRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.track_search, menu);
        return true;
    }

}
