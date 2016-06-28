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

    Offer offer;

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
        getSupportActionBar().setTitle(offer.name + " voor " + offer.price);

        content.setText(offer.description);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng location = new LatLng(
                        offer.shop.location.latitude,
                        offer.shop.location.longitude
                );

                googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(location, 13)
                );

                googleMap.addMarker(
                        new MarkerOptions().title(offer.shop.name).position(location)
                ).showInfoWindow();
            }
        });

    }
}
