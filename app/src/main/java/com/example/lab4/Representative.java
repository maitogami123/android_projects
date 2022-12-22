package com.example.lab4;

import android.content.Intent;

public class Representative {
    private String office;
    private String name;
    private String party;
    private String imgUri;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String website;
    private String facebook;
    private String twitter;
    private String googlePlus;
    private String youtubeChannel;
    private String state;

    public Representative() {
        this.office = "";
        this.name = "";
        this.party = "";
        this.imgUri = "No data provided";
        this.address = "";
        this.phoneNumber = "";
        this.emailAddress = "No data provided";
        this.website = "No data provided";
        this.facebook = "No data provided";
        this.twitter = "No data provided";
        this.googlePlus = "No data provided";
        this.youtubeChannel = "No data provided";
        this.state = "";
    }

    public Representative(String office, String name, String party, String imgUri,
                          String address, String phoneNumber, String emailAddress,
                          String website, String facebook, String twitter,
                          String googlePlus, String youtubeChannel, String state) {
        this.office = office;
        this.name = name;
        this.party = party;
        this.imgUri = imgUri;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.website = website;
        this.facebook = facebook;
        this.twitter = twitter;
        this.googlePlus = googlePlus;
        this.youtubeChannel = youtubeChannel;
        this.state = state;
    }

    public Representative(Intent intent) {
        this.office = intent.getStringExtra("office");
        this.name = intent.getStringExtra("name");
        this.party = intent.getStringExtra("party");
        this.imgUri = intent.getStringExtra("imgUri");
        this.address = intent.getStringExtra("address");
        this.phoneNumber = intent.getStringExtra("phoneNumber");
        this.emailAddress = intent.getStringExtra("emailAddress");
        this.website = intent.getStringExtra("website");
        this.facebook = intent.getStringExtra("facebook");
        this.twitter = intent.getStringExtra("twitter");
        this.googlePlus = intent.getStringExtra("googlePlus");
        this.youtubeChannel = intent.getStringExtra("youtubeChannel");
        this.state = intent.getStringExtra("state");
    }

    public static void packageIntent(Intent intent, String office, String name, String party, String imgUri,
                                     String address, String phoneNumber, String emailAddress, String website,
                                     String facebook, String twitter, String googlePlus, String youtubeChannel, String state
    ) {
        intent.putExtra("office", office);
        intent.putExtra("name", name);
        intent.putExtra("party", party);
        intent.putExtra("imgUri", imgUri);
        intent.putExtra("address", address);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("emailAddress", emailAddress);
        intent.putExtra("website", website);
        intent.putExtra("facebook", facebook);
        intent.putExtra("twitter", twitter);
        intent.putExtra("googlePlus", googlePlus);
        intent.putExtra("youtubeChannel", youtubeChannel);
        intent.putExtra("state", state);
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public void setYoutubeChannel(String youtubeChannel) {
        this.youtubeChannel = youtubeChannel;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getImgUri() {
        return imgUri;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
    }

    public String getParty() {
        return party;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getWebsite() {
        return website;
    }

    public String getYoutubeChannel() {
        return youtubeChannel;
    }
}
