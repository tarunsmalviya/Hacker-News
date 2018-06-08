package com.tarunsmalviya.hackernews.model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Item extends RealmObject {

    @PrimaryKey
    private int id;

    private int parent, score,synced;
    private long timestamp;
    private String type, by, title, text, url;
    private Boolean isTopItem, isNewItem, isBestItem;
    private RealmList<Integer> kids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getTopItem() {
        return isTopItem;
    }

    public void setTopItem(Boolean topItem) {
        isTopItem = topItem;
    }

    public Boolean getNewItem() {
        return isNewItem;
    }

    public void setNewItem(Boolean newItem) {
        isNewItem = newItem;
    }

    public Boolean getBestItem() {
        return isBestItem;
    }

    public void setBestItem(Boolean bestItem) {
        isBestItem = bestItem;
    }

    public int getSynced() {
        return synced;
    }

    public void setSynced(int synced) {
        this.synced = synced;
    }

    public RealmList<Integer> getKids() {
        return kids;
    }

    public void setKids(RealmList<Integer> kids) {
        this.kids = kids;
    }
}
