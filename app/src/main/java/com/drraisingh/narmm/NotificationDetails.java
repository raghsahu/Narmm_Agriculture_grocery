package com.drraisingh.narmm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class NotificationDetails extends AppCompatActivity {
TextView tv_title,tv_des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_des=(TextView)findViewById(R.id.tv_des);
        Intent intent=getIntent();
        tv_title.setText(""+ intent.getStringExtra("title"));
        tv_des.setText(""+Html.fromHtml(intent.getStringExtra("des")));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;
            default:
                return false;
        }


    }
}
