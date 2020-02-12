package com.drraisingh.narmm.Fragment.ItemDetailsFragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drraisingh.narmm.R;

import static com.drraisingh.narmm.Fragment.ItemDetailsFragment.ProductDetailsFragment.pro_others;

/**
 * Created by Raghvendra Sahu on 27-Dec-19.
 */
public class OthersFragment extends Fragment {
    TextView tv_details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.details_fragment, container, false);


        tv_details=root.findViewById(R.id.tv_details);

        try {
            // tv_details.setText(pro_insect);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_details.setText(Html.fromHtml(pro_others, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tv_details.setText(Html.fromHtml(pro_others));
            }

        }catch (Exception e){

        }


        return root;
    }



}
