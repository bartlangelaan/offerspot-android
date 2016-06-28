package nl.bartlangelaan.offerspot.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * With this class, requests are made to the Bootb products service
 */
public class API {
    /**
     * Create one API instance
     */
    private static API ourInstance = new API();
    /**
     * All requests are send to the queue
     */
    private static RequestQueue queue;
    private static SharedPreferences sharedPreferences;
    /**
     * All requests begin with this url
     */
    private String baseUrl = "http://docent.cmi.hro.nl/bootb/service/products";

    /**
     * Make a singleton for easy access. Context is required
     */
    public static API getInstance(Context context) {
        queue = Volley.newRequestQueue(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return ourInstance;
    }

    public void GetOffers(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        if (sharedPreferences.getBoolean("show_food", true)) {
            GetAllOffers(successListener, errorListener);
        } else {
            GetAllOffers("/nonfood", successListener, errorListener);
        }
    }
    /**
     * Gets all offers
     *
     * @param successListener Is called when the request was successful
     * @param errorListener   Is called when the request was unsuccessful
     */
    public void GetAllOffers(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        queue.add(new JsonObjectRequest(Request.Method.GET, baseUrl, null, successListener, errorListener));
    }

    /**
     * Gets all offers from a specific category
     *
     * @param category        The category to get results of
     * @param successListener Is called when the request was successful
     * @param errorListener   Is called when the request was unsuccessful
     */
    public void GetAllOffers(String category, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        queue.add(new JsonObjectRequest(Request.Method.GET, baseUrl + "/" + category, null, successListener, errorListener));
    }
}
