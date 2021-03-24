package com.italianappsw.regionicolorate.screens.country_map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.italianappsw.regionicolorate.screens.dpcm.DpcmActivity;
import com.italianappsw.regionicolorate.models.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JavaScriptInterface {
    private Context mContext;
    private Activity mActivity;
    private WebView mWebView;
    private ArrayList<Region> regions;


    JavaScriptInterface(Context c, Activity activity, WebView webView, ArrayList<Region> regions) {
        this.mContext = c;
        this.mActivity = activity;
        this.mWebView = webView;
        this.regions = regions;
    }

    /**
     * HTML prende dati da Android
     * Utilizzato per inviare le regioni ad HTML
     */
    @JavascriptInterface
    public void getValueJson() throws JSONException
    {
        final JSONArray jArray = new JSONArray();

        Log.d("regions", String.valueOf(regions.size()));
        Log.d("HERE", "I'M HERE");

        for(Region region : regions) {
            JSONObject jObject = new JSONObject();
            jObject.put("Code", region.code);
            jObject.put("Region",region.label);
            jObject.put("Level",region.value);
            jArray.put(jObject);
        }

        System.out.println(jArray.toString());
        // send value from java class to html javascript function
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:setJson(" + jArray + ")");
            }
        });

    }

    /**
     * Prende dati dall'HTML
     * In questo caso prende il colore della regione
     */
    @JavascriptInterface
    public void sendData(String data) {
        Intent intent= DpcmActivity.getIntentInstance(mContext, data);
        mActivity.startActivity(intent);
    }



}
