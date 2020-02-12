package com.drraisingh.narmm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.drraisingh.narmm.activity.MainActivity;

public class ShopRegistration extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_registration);
    }
    public void go_Shop(View view) {
        /*if(sessionManagement.isLoggedIn()) {*/
        Intent startmain = new Intent(ShopRegistration.this, MainActivity.class);
        startActivity(startmain);
        /*}else{
            Intent startmain = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(startmain);
        }*/
        finish();
    }
}
