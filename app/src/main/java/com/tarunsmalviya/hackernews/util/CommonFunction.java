package com.tarunsmalviya.hackernews.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tarunsmalviya.hackernews.model.Item;
import com.tarunsmalviya.hackernews.network.NetworkSingleton;
import com.tarunsmalviya.hackernews.network.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;

public class CommonFunction {

    private CommonFunction() {
    }

    public static void fetchItem(Context context, int id) {
        String url = Url.getItemUrl(id);
        if (url != null) {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.e("response", response.toString());
                            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {

                                @Override
                                public void execute(Realm realm) {
                                    Item item = Realm.getDefaultInstance().where(Item.class).equalTo("id", response.optInt("id")).findFirst();
                                    if (item != null) {
                                        item.setParent(response.optInt("parent"));
                                        item.setScore(response.optInt("score"));
                                        item.setTimestamp(response.optLong("time"));
                                        item.setType(response.optString("type"));
                                        item.setBy(response.optString("by"));
                                        item.setTitle(response.optString("title"));
                                        item.setText(response.optString("text"));
                                        item.setUrl(response.optString("url"));

                                        JSONArray array = response.optJSONArray("kids");
                                        if (array != null) {
                                            RealmList<Integer> kids = new RealmList<>(array.length());
                                            for (int i = 0; i < array.length(); i++)
                                                kids.add(array.optInt(i));
                                            item.setKids(kids);
                                        }
                                        item.setSynced(1);
                                    }
                                }
                            });
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            NetworkSingleton.getInstance(context).addToRequestQueue(request);
        }
    }
}
