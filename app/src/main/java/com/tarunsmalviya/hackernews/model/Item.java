package com.tarunsmalviya.hackernews.model;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Item extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private int parent;

    private String type, by, time, text;
    private ArrayList<Integer> kids;
}
