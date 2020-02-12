package com.drraisingh.narmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drraisingh.narmm.R;

public class Thankyou extends AppCompatActivity {

    private String Happy_code;
    TextView happycode;
    Button btn_thank_home,btn_trak_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        happycode=findViewById(R.id.happycode);
        btn_thank_home=findViewById(R.id.btn_thank_home);
        btn_trak_order=findViewById(R.id.btn_trak_order);

        try {
            Happy_code=getIntent().getStringExtra("Happy_code");
            happycode.setText("Your Happy Code: "+Happy_code);

        }catch (Exception e){

        }


        btn_thank_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_trak_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wallet=new Intent(Thankyou.this, MainActivity.class);
                wallet.putExtra("Track","Track");
                startActivity(wallet);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();

        Intent wallet=new Intent(Thankyou.this, MainActivity.class);
        startActivity(wallet);
        finish();
    }
}
