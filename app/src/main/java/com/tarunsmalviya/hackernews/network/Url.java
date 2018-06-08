package com.tarunsmalviya.hackernews.network;

import com.tarunsmalviya.hackernews.util.ListType;

public class Url {

    private static String DOMAIN = "https://hacker-news.firebaseio.com/";

    public static String getUrl(ListType type) {
        switch (type) {
            case TOP_STORIES:
                return DOMAIN + "v0/topstories.json?";
            case NEW_STORIES:
                return DOMAIN + "v0/newstories.json?";
            case BEST_STORIES:
                return DOMAIN + "v0/beststories.json?";
            default:
                return null;
        }
    }

    public static String getItemUrl(int id) {
        return DOMAIN + "v0/item/" + String.valueOf(id) + ".json?";
    }
}
