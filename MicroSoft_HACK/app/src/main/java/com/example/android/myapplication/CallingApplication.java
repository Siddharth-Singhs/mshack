package com.example.android.myapplication;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.mmi.services.account.MapmyIndiaAccountManager;

public class CallingApplication extends Application {
    private String getAtlasGrantType() {
        return "client_credentials";
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MapmyIndiaAccountManager.getInstance().setRestAPIKey(getRestAPIKey());
        MapmyIndiaAccountManager.getInstance().setMapSDKKey(getMapSDKKey());
        MapmyIndiaAccountManager.getInstance().setAtlasClientId(getAtlasClientId());
        MapmyIndiaAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());
        MapmyIndiaAccountManager.getInstance().setAtlasGrantType(getAtlasGrantType());



    }

    public String getRestAPIKey() {
        return getApplicationContext().getResources().getString(R.string.rest_api_key);
    }

    public String getMapSDKKey() {
        return getApplicationContext().getResources().getString(R.string.map_sdk_key);
    }

    public String getAtlasClientId() {
        return getApplicationContext().getResources().getString(R.string.client_id_key);
    }

    public String getAtlasClientSecret() {
        return getApplicationContext().getResources().getString(R.string.client_secret_key);
    }
}
