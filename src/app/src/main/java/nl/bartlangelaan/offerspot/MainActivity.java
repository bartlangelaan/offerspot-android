package nl.bartlangelaan.offerspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import nl.bartlangelaan.offerspot.adapters.OffersAdapter;
import nl.bartlangelaan.offerspot.objects.Offer;
import nl.bartlangelaan.offerspot.utils.API;

public class MainActivity extends AppCompatActivity {

    Gson gson = new Gson();
    private String TAG = "MAIN";
    private ListView list;
    private OffersAdapter adapter;
    private Offer[] offers;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Show the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all elements
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        list = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Show the toolbar
        setSupportActionBar(toolbar);

        // Refresh on click on fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("offers", gson.toJson(offers));
                startActivity(intent);
            }
        });

        // When a list item is clicked, open new activity
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Offer offer = adapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("offer", gson.toJson(offer));
                startActivity(intent);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    public void onResume() {
        super.onResume();

        // Refresh now
        refreshList();
    }

    private void refreshList() {
        // Show progress bar
        swipe.setRefreshing(true);

        API.getInstance(this).GetOffers(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Remove the progress bar
                swipe.setRefreshing(false);

                // Transform all offers to Offer objects
                offers = new Offer[0];
                try {
                    offers = gson.fromJson(response.getJSONArray("products").toString(), Offer[].class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create the OffersAdapter and use it on the list
                adapter = new OffersAdapter(getBaseContext(), offers);
                list.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.coordinatorLayout), "Connection error! Do you have interwebz?", Snackbar.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get the id of the selected item
        int id = item.getItemId();

        // Check which item was selected
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
