package com.drraisingh.narmm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.UseFragmnet.My_order_fragment;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.util.IOnBackPressed;
import com.drraisingh.narmm.util.Session_management;

public class MyOrder extends AppCompatActivity {
    private Session_management sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        sessionManagement = new Session_management(MyOrder.this);
        if (savedInstanceState == null) {
            Fragment fm = new My_order_fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contentPanel, fm, "Home_fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }


    public void updateHeader(){
        if(sessionManagement.isLoggedIn()) {
            String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
            String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
            String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentPanel);
                if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
                    super.onBackPressed();
                }
                return true;
            default:
                return false;
        }


    }


    @Override public void onBackPressed() {
        Intent intent=new Intent(MyOrder.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
