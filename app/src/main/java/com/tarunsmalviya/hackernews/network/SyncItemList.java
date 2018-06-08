package com.tarunsmalviya.hackernews.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tarunsmalviya.hackernews.model.Item;
import com.tarunsmalviya.hackernews.util.ListType;

import org.json.JSONArray;

import io.realm.Realm;
import io.realm.Sort;

public class SyncItemList extends IntentService {

    public SyncItemList() {
        super("SyncItemList");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getExtras() != null && intent.getExtras().containsKey("type")) {
            final ListType type = (ListType) intent.getExtras().getSerializable("type");
            final String field = (type == ListType.TOP_STORIES ? "isTopItem" : (type == ListType.NEW_STORIES ? "isNewItem" : "isBestItem"));

            String url = Url.getUrl(type);
            if (url != null) {
                JsonArrayRequest request = new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(final JSONArray response) {
                                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {

                                    @Override
                                    public void execute(Realm realm) {
                                        Item item = Realm.getDefaultInstance().where(Item.class).equalTo(field, true).sort("id", Sort.DESCENDING).findFirst();

                                        int recentId = 0;
                                        int newCount = 0, updateCount = 0;

                                        if (item != null) recentId = item.getId();

                                        for (int i = 0; i < response.length(); i++) {
                                            if (response.optInt(i) <= recentId)
                                                break;

                                            item = Realm.getDefaultInstance().where(Item.class).equalTo("id", response.optInt(i)).findFirst();
                                            if (item == null) {
                                                item = realm.createObject(Item.class, response.optInt(i));
                                                item.setSynced(0);
                                                item.setTopItem(type == ListType.TOP_STORIES ? true : false);
                                                item.setNewItem(type == ListType.NEW_STORIES ? true : false);
                                                item.setBestItem(type == ListType.BEST_STORIES ? true : false);

                                                newCount++;
                                            } else {
                                                if (type == ListType.TOP_STORIES)
                                                    item.setTopItem(true);
                                                else if (type == ListType.NEW_STORIES)
                                                    item.setNewItem(true);
                                                else if (type == ListType.BEST_STORIES)
                                                    item.setBestItem(true);

                                                updateCount++;
                                            }
                                        }

                                        Log.d("recentId", String.valueOf(recentId));
                                        Log.d("newCount", String.valueOf(newCount));
                                        Log.d("updateCount", String.valueOf(updateCount));
                                    }
                                });
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                NetworkSingleton.getInstance(this).addToRequestQueue(request);
            }
        }
    }
}
