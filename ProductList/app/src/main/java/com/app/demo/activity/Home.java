package com.app.demo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.app.demo.R;
import com.app.demo.utilities.DialogCallBack;
import com.app.demo.utilities.VolleyHelper;
import com.app.demo.utilities.VolleyResponseCallBack;

import org.json.JSONObject;

import static com.app.demo.utilities.Constant.CLOTH_URL;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getClothsListFromServer();
    }

    private void getClothsListFromServer() {

        new VolleyHelper().doJsonObjectRequest(this,
                Request.Method.GET,
                CLOTH_URL,
                new VolleyResponseCallBack() {

                    @Override
                    public void onResponse(JSONObject response) {
                        super.onResponse(response);
                        Log.d("RESPONSE", " "+response);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {

                    }
                }, true, new DialogCallBack(){
                    @Override
                    protected void onPositiveBtnClick() {
                        super.onPositiveBtnClick();
                    }

                    @Override
                    protected void onNegativeBtnClick() {
                        super.onNegativeBtnClick();
                    }

                    @Override
                    protected void onNeutralBtnClick() {
                        super.onNeutralBtnClick();
                    }
                });
    }
}
