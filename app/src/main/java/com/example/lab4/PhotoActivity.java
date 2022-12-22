package com.example.lab4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();

        View officialView = findViewById(R.id.photo_layout);
        TextView stateText = findViewById(R.id.photo_state);
        TextView officialOffice = findViewById(R.id.photo_office);
        TextView officialName = findViewById(R.id.photo_name);
        ImageView officialImg = findViewById(R.id.photo_img);

        stateText.setText(intent.getStringExtra("state"));

        if (intent.getStringExtra("party").equals("Democratic Party")) {
            officialView.setBackgroundColor(Color.parseColor("#FF0000"));
        } else if (intent.getStringExtra("party").equals("Republican Party")) {
            officialView.setBackgroundColor(Color.parseColor("#002AFF"));
        } else {
            officialView.setBackgroundColor(Color.parseColor("#000000"));
        }

        officialOffice.setText(intent.getStringExtra("office"));
        officialName.setText(intent.getStringExtra("name"));

        String imgURI =intent.getStringExtra("imgUri");
        Picasso.get().load(imgURI).centerCrop().fit()
                .placeholder(R.drawable.loading)
                .error(R.drawable.default_user_img)
                .into(officialImg);


    }
}
