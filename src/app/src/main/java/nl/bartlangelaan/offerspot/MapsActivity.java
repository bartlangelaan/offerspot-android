package nl.bartlangelaan.offerspot;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import nl.bartlangelaan.offerspot.objects.Offer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * Holds the offers sent by the MainActivity
     */
    private Offer[] offers;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get offers passed from MainActivity
        offers = gson.fromJson(getIntent().getExtras().getString("offers"), Offer[].class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // For each offer..
        for (Offer offer : offers) {
            // Create the location
            LatLng location = new LatLng(
                    offer.shop.location.latitude,
                    offer.shop.location.longitude
            );

            // Add marker
            googleMap.addMarker(
                    new MarkerOptions().title(offer.shop.name).position(location)
            ).showInfoWindow();
        }
    }
}
