package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.MainActivity;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class Helpline_fragment extends Fragment {

    TextView tv_dr_number;

    public Helpline_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_helpline, container, false);
        ((MainActivity) getActivity()).setTitle("Helpline");

        tv_dr_number=view.findViewById(R.id.tv_dr_number);

        tv_dr_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:18002582595"));

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},11);
                }
                else
                {
                    startActivity(callIntent);
                }

//                if (ActivityCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                startActivity(callIntent);
            }
        });



        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 11: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18002582595"));
                    startActivity(intent);
                }
                else
                {

                }
                return;
            }
        }
    }

}
