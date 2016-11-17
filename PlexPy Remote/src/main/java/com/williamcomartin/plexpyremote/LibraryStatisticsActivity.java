package com.williamcomartin.plexpyremote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.williamcomartin.plexpyremote.Adapters.LibraryStatisticsAdapter;
import com.williamcomartin.plexpyremote.Helpers.GsonRequest;
import com.williamcomartin.plexpyremote.Helpers.UrlHelpers;
import com.williamcomartin.plexpyremote.Models.LibraryStatisticsModels;

public class LibraryStatisticsActivity extends NavBaseActivity {

    private SharedPreferences SP;
    private RecyclerView rvLibStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_statistics);
        setupActionBar();

        rvLibStats = (RecyclerView) findViewById(R.id.rvLibStats);

        SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String url = UrlHelpers.getHostPlusAPIKey() + "&cmd=get_libraries";

        GsonRequest<LibraryStatisticsModels> request = new GsonRequest<>(
                url,
                LibraryStatisticsModels.class,
                null,
                requestListener(),
                errorListener()
        );

        ApplicationController.getInstance().addToRequestQueue(request);

        rvLibStats.setLayoutManager(new LinearLayoutManager(this));
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }

    private Response.Listener<LibraryStatisticsModels> requestListener() {
        return new Response.Listener<LibraryStatisticsModels>() {
            @Override
            public void onResponse(LibraryStatisticsModels response) {
                LibraryStatisticsAdapter adapter = new LibraryStatisticsAdapter(response.response.data);
                rvLibStats.setAdapter(adapter);
            }
        };
    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.library_statistics);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}