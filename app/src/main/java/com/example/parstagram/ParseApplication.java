package com.example.parstagram;

import android.app.Application;

import com.example.parstagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        // If this isn't done before initialize, won't be able to query or set data on this model (Post)
        ParseObject.registerSubclass(Post.class);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("parstagramId") // should correspond to APP_ID env variable
                .clientKey("mastahkehnodeleh")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://lofton-parstagram.herokuapp.com/parse").build());
        /*
        * if server has http instead of https, then put android:usesCleartextTraffic="true" in
        * the manifest to declare that this app is using an insecure connection.
        * Will give I/O issue if not doing that.
        */
    }

}
