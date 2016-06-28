package nl.bartlangelaan.offerspot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private String TAG = "MAIN";
    private ListView list;
    private ProgressBar progress;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshList();
            }
        });

        list = (ListView) findViewById(R.id.listView);
        progress = (ProgressBar) findViewById(R.id.progressbar_loading);

        refreshList();
    }

    private void refreshList() {
        API.getInstance(this).GetAllOffers(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.setVisibility(View.GONE);

                Offer[] offers = new Offer[0];
                try {
                    offers = gson.fromJson(response.getJSONArray("products").toString(), Offer[].class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                OffersAdapter adapter = new OffersAdapter(getBaseContext(), offers);
                list.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.coordinatorLayout), "No internet connection available", Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
