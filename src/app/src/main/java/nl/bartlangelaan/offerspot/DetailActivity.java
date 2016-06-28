package nl.bartlangelaan.offerspot;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import nl.bartlangelaan.offerspot.objects.Offer;

public class DetailActivity extends AppCompatActivity {

    /**
     * Holds the offer, recieved from the MainActivity
     */
    Offer offer;

    /**
     * The gson object that is used to decode the JSON offer sent by the MainActivity
     */
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Show the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get offer passed from MainActivity
        offer = gson.fromJson(getIntent().getExtras().getString("offer"), Offer.class);

        // Find all elements
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailMap);
        TextView content = (TextView) findViewById(R.id.detailContent);

        // Show actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set contents
        getSupportActionBar().setTitle(offer.name + " voor " + offer.price);
        content.setText(offer.description);

        // Set fab onclick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This button does nothing, but it looks very fancy!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // When Google Maps is loaded
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                // Create the location
                LatLng location = new LatLng(
                        offer.shop.location.latitude,
                        offer.shop.location.longitude
                );

                // Move to that location
                googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(location, 13)
                );

                // Add marker
                googleMap.addMarker(
                        new MarkerOptions().title(offer.shop.name).position(location)
                ).showInfoWindow();
            }
        });

    }
}
