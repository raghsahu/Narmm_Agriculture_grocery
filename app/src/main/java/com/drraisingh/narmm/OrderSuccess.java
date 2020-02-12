package com.drraisingh.narmm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drraisingh.narmm.activity.MainActivity;

public class OrderSuccess extends AppCompatActivity implements View.OnClickListener{
    TextView tv_info;
    Button btn_home, btn_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);


        // handle the touch event if true


        tv_info = (TextView)findViewById(R.id.tv_thank_info);
        btn_home = (Button) findViewById(R.id.btn_thank_home);
        btn_order = (Button) findViewById(R.id.btn_thank_order);

        tv_info.setText(Html.fromHtml(getIntent().getStringExtra("msg")));

        btn_home.setOnClickListener(this);
        btn_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_thank_home) {

            Intent intent=new Intent(OrderSuccess.this, MainActivity.class);
            startActivity(intent);
            finish();


        } else if (id == R.id.btn_thank_order) {

            Intent intent=new Intent(OrderSuccess.this, MyOrder.class);
           startActivity(intent);
           finish();

        }

    }


}
