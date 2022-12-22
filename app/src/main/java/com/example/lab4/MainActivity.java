package com.example.lab4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Representative> mRepresentativeList = new ArrayList<Representative>();
    private RecyclerView mRecyclerRepresentative;
    private RepresentativeAdapter mRepresentativeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerRepresentative = findViewById(R.id.recyclerRepresentative);

        mRepresentativeAdapter = new RepresentativeAdapter(this, mRepresentativeList);
        mRecyclerRepresentative.setAdapter(mRepresentativeAdapter);
        mRecyclerRepresentative.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.search) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);
            EditText searchText = (EditText) dialog.findViewById(R.id.search_text);

            Button confirmBtn = (Button) dialog.findViewById(R.id.confirm_btn);
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRepresentativeList.clear();
                    getFromWebAPI(searchText.getText().toString());
                    dialog.dismiss();
                }
            });

            Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void getFromWebAPI(String address) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyBxBmEuwxQyDn8pgrBsX5jN_-1wB_nBUYQ&address=" + address;
        TextView stateInfoView = (TextView) findViewById(R.id.state_info);
        stateInfoView.setText("Loading...");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // JSON
                        JSONObject jObj = null;
                        try {
                            jObj = new JSONObject(response);
                            JSONObject stateObject = jObj.getJSONObject("normalizedInput");
                            // Get current searching state and display it
                            String stateInfo = stateObject.getString("city") + ", " + stateObject.getString("state") +
                                    " " + stateObject.getString("zip");
                            stateInfoView.setText(stateInfo);

                            // Add representative to representativeList
                            JSONArray officeArray = jObj.getJSONArray("offices");
                            for (int i = 0; i < officeArray.length(); i++) {
                                JSONObject obj = officeArray.getJSONObject(i);
                                JSONArray officialIndicesJSON = obj.getJSONArray("officialIndices");
                                for (int j = 0; j < officialIndicesJSON.length(); j++){
                                    JSONArray representativeArray = jObj.getJSONArray("officials");
                                    Representative representative = new Representative();
                                    JSONObject officialObj = representativeArray.getJSONObject(officialIndicesJSON.getInt(j));

                                    // Set state
                                    representative.setState(stateInfo);

                                    // Set office name
                                    representative.setOffice(obj.getString("name"));

                                    // Set name
                                    representative.setName(officialObj.getString("name"));

                                    // Set address
                                    String address;
                                    try {
                                        JSONArray addressArray = officialObj.getJSONArray("address");
                                        JSONObject addressObj = addressArray.getJSONObject(0);
                                        address = addressObj.getString("line1") + " " + addressObj.getString("city") + " " + addressObj.getString("state");
                                    } catch (Exception e) {
                                        address = "No data provided";
                                    }
                                    representative.setAddress(address);

                                    // Set party
                                    representative.setParty(officialObj.getString("party"));

                                    // Set phone number
                                    String phoneNumber;
                                    try {
                                        JSONArray phoneArray = officialObj.getJSONArray("phones");
                                        phoneNumber = phoneArray.getString(0);
                                    } catch (Exception e) {
                                        phoneNumber = "No data provided";
                                    }
                                    representative.setPhoneNumber(phoneNumber);

                                    // Set website address
                                    String websiteURL;
                                    try{
                                        JSONArray urlArray = officialObj.getJSONArray("urls");
                                        websiteURL = urlArray.getString(0);
                                    } catch (Exception e) {
                                        websiteURL = "No data provided";
                                    }
                                    representative.setWebsite(websiteURL);

                                    // Set Image URI
                                    String imgUri;
                                    try {
                                        imgUri = officialObj.getString("photoUrl").replace("http:", "https:");
                                    } catch (Exception e) {
                                        imgUri = "No data provided";
                                        e.printStackTrace();
                                    }
                                    representative.setImgUri(imgUri);

                                    String email;
                                    try {
                                        JSONArray emailArray = officialObj.getJSONArray("emails");
                                        email = emailArray.getString(0);
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                        email = "No data provided";
                                    }
                                    representative.setEmailAddress(email);

                                    // Set personal social media
                                    try {
                                        JSONArray socialMediaArray = officialObj.getJSONArray("channels");
                                        for (int socialMediaIndex = 0; socialMediaIndex < socialMediaArray.length(); socialMediaIndex++) {
                                            JSONObject socialMediaObj = socialMediaArray.getJSONObject(socialMediaIndex);
                                            String type = socialMediaObj.getString("type");
                                            if (type.equalsIgnoreCase("facebook")) {
                                                representative.setFacebook(socialMediaObj.getString("id"));
                                            } else if (type.equalsIgnoreCase("twitter")) {
                                                representative.setTwitter(socialMediaObj.getString("id"));
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mRepresentativeList.add(representative);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mRepresentativeAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error
                        mRepresentativeList.clear();
                        TextView stateInfoView = (TextView) findViewById(R.id.state_info);
                        stateInfoView.setText("Invalid value!");
                        mRepresentativeAdapter.notifyDataSetChanged();
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
        }
}