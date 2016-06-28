package nl.bartlangelaan.offerspot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import nl.bartlangelaan.offerspot.R;
import nl.bartlangelaan.offerspot.objects.Offer;

public class OffersAdapter extends ArrayAdapter<Offer> {
    public OffersAdapter(Context context, Offer[] offers) {
        super(context, 0, offers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Offer offer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_offer, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        tvName.setText(offer.name);
        tvHome.setText(String.format("%.2f", offer.price));
        // Return the completed view to render on screen
        return convertView;
    }
}
