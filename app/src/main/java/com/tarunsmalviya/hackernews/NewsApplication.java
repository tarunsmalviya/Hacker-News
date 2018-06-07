package com.tarunsmalviya.hackernews;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("news.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
