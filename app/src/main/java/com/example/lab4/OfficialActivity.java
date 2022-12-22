package com.example.lab4;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        Intent intent = getIntent();
        Representative representative = new Representative(intent);

        View officialView = findViewById(R.id.official_layout);
        TextView stateText = findViewById(R.id.state_info_official);
        TextView officialOffice = findViewById(R.id.official_office);
        TextView officialName = findViewById(R.id.official_name);
        TextView officialParty = findViewById(R.id.official_party);
        ImageView officialImg = findViewById(R.id.official_img);
        TextView officialAddress = findViewById(R.id.official_address);
        TextView officialPhone = findViewById(R.id.official_phone);
        TextView officialEmail = findViewById(R.id.official_email);
        TextView officialWebsite = findViewById(R.id.official_website);
        ImageView youtube = findViewById(R.id.youtube_link);
        ImageView googlePlus = findViewById(R.id.google_plus_link);
        ImageView facebook = findViewById(R.id.facebook_link);
        ImageView twitter = findViewById(R.id.twitter_link);

        stateText.setText(representative.getState());

        if (representative.getParty().equals("Democratic Party")) {
            officialView.setBackgroundColor(Color.parseColor("#FF0000"));
        } else if (representative.getParty().equals("Republican Party")) {
            officialView.setBackgroundColor(Color.parseColor("#002AFF"));
        } else {
            officialView.setBackgroundColor(Color.parseColor("#000000"));
        }

        officialOffice.setText(representative.getOffice());
        officialName.setText(representative.getName());
        officialParty.setText(representative.getParty());
        officialAddress.setText(representative.getAddress());
        officialPhone.setText(representative.getPhoneNumber());
        officialEmail.setText(representative.getEmailAddress());
        officialWebsite.setText(representative.getWebsite());


        String imgURI = representative.getImgUri();
        Picasso.get().load(imgURI).centerCrop().resize(400, 600)
                .placeholder(R.drawable.loading)
                .error(R.drawable.default_user_img)
                .into(officialImg);
        officialImg.setClickable(true);
        officialImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(OfficialActivity.this, PhotoActivity.class);
                photoIntent.putExtra("state", representative.getState());
                photoIntent.putExtra("office", representative.getOffice());
                photoIntent.putExtra("name", representative.getName());
                photoIntent.putExtra("party", representative.getParty());
                photoIntent.putExtra("imgUri", representative.getImgUri());
                startActivity(photoIntent);
            }
        });

        if (representative.getGooglePlus().equalsIgnoreCase("No data provided")) {
            googlePlus.setVisibility(View.INVISIBLE);
        } else {
            googlePlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/" + representative.getGooglePlus()));
                    startActivity(baseIntent);
                }
            });
        }

        if (representative.getYoutubeChannel().equalsIgnoreCase("No data provided")) {
            youtube.setVisibility(View.INVISIBLE);
        } else {
            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/" + representative.getYoutubeChannel()));
                    startActivity(baseIntent);
                }
            });
        }

        if (representative.getFacebook().equalsIgnoreCase("No data provided")) {
            facebook.setVisibility(View.INVISIBLE);
        } else {
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/" + representative.getFacebook()));
                    startActivity(baseIntent);
                }
            });
        }

        if (representative.getTwitter().equalsIgnoreCase("No data provided")) {
            twitter.setVisibility(View.INVISIBLE);
        } else {
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + representative.getTwitter()));
                    startActivity(baseIntent);
                }
            });
        }


    }
}
