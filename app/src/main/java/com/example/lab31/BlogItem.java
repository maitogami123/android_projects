package com.example.lab31;

public class BlogItem {
    private String title;
    private String description;
    private String link;
    private String imgUri;


    public BlogItem() {
        title = "";
        description= "";
        link = "";
        imgUri = "";
    }

    public BlogItem( String _title, String _description, String _link, String _imgUri) {
        title = _title;
        description= _description;
        link = _link;
        imgUri = _imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUri() {
        return imgUri;
    }
}
