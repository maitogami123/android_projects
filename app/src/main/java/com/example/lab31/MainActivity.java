package com.example.lab31;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    String[] topicArray = {"Proteins","Amino Acids","Grains and Starches","Fibers and Legumes", "Vitamins", "Minerals"};
    ArrayList<BlogItem> blogArray = new ArrayList<BlogItem>();
    ArrayList<String> blogTitle = new ArrayList<>();
    private String url1 = "https://www.petfoodindustry.com/rss/topic/";
    private int url2 = 292;
    private HandleXml obj;
    private String selectedTitle = "";
    private static boolean isFetched = false;
    ArrayAdapter adapter;
    ArrayAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topicArray);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, blogTitle);

        ListView lv = (ListView) findViewById(R.id.list_view);
        if (blogTitle.size() > 0) {
            lv.setAdapter(adapter1);
        } else
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (blogTitle.size() == 0) {
                    selectedTitle = (String) lv.getItemAtPosition(position).toString();
                    String finalURL = url1 + (url2 + position);
                    new MyAsyncTask().execute(finalURL);

                } else {
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.custom_dialog);

                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                    TextView title = (TextView) dialog.findViewById(R.id.blog_title);
                    ImageView ivBasicImage = (ImageView) dialog.findViewById(R.id.image2);

                    title.setText(selectedTitle);
                    text.setText(blogArray.get(position).getDescription().replaceAll("<[^>]*>", ""));

                    String imgUri = blogArray.get(position).getImgUri();
                    if (imgUri.length() != 0)
                        Picasso.get().load(imgUri).placeholder(R.drawable.loading).error(R.drawable.default_user_img).into(ivBasicImage);
                    Button confirmBtn = (Button) dialog.findViewById(R.id.btn_dialog);
                    confirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(blogArray.get(position).getLink()));
                            startActivity(baseIntent);
                        }
                    });

                    Button closeBtn = (Button) dialog.findViewById(R.id.btn_dialog2);
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topicArray);
        ListView lv = (ListView) findViewById(R.id.list_view);
        lv.setAdapter(adapter);
        blogTitle.clear();
        isFetched = false;
        selectedTitle = "";
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, ArrayList<BlogItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String appName = "Loading...";
            getSupportActionBar().setTitle(appName);
        }

        @Override
        protected void onPostExecute(ArrayList<BlogItem>  blogItemList) {
            super.onPostExecute(blogItemList);
            String appName = "RSS Feeds" + " - " + selectedTitle;
            getSupportActionBar().setTitle(appName);
            for (BlogItem item: blogItemList) {
                blogTitle.add(item.getTitle());
            }
            ListView lv = (ListView) findViewById(R.id.list_view);
            adapter1.notifyDataSetChanged();
            lv.setAdapter(adapter1);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<BlogItem> doInBackground(String... strings) {
            obj = new HandleXml(strings[0]);
            blogArray = obj.fetchXML();
            return blogArray;
        }
    }

}