package com.example.lab31;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HandleXml {
    private String urlString = null;
    private ArrayList<BlogItem> blogList;

    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXml(String url) {
        this.urlString=url;
    }

    public ArrayList<BlogItem> parseXMLAndStore(XmlPullParser myParser) {
        int event;
        String text = null;
        ArrayList<BlogItem> blogList = null;
        BlogItem blogItem = null;
        try {
            String imgUri = "";
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        blogList = new ArrayList<BlogItem>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = myParser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            blogItem = new BlogItem();
                        } else if (name.equals("media:content") && blogItem != null) {
                            imgUri = myParser.getAttributeValue(null, "url");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        name = myParser.getName();
                        if (blogItem != null) {
                            if (name.equals("title")) {
                                blogItem.setTitle(text);
                            } else if (name.equals("link")) {
                                blogItem.setLink(text);
                            } else if (name.equals("description")) {
                                blogItem.setDescription(text);
                            } else if (name.equals("media:content")) {
                                blogItem.setImgUri(imgUri);
                            }
                            else if (name.equals("item") && blogItem != null) {
                                blogList.add(blogItem);
                            }
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogList;
    }

    public ArrayList<BlogItem> fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myParser = xmlFactoryObject.newPullParser();
                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser.setInput(stream, null);
                    blogList =  parseXMLAndStore(myParser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  blogList;
    }



}
