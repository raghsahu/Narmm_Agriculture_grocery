package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.drraisingh.narmm.Fragment.Search_fragment;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.CompanyProfile;
import com.drraisingh.narmm.activity.MainActivity;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class Dashboard_fragment extends Fragment implements View.OnClickListener {

    LinearLayout ll_dashboard,ll_profile,ll_trail_pop,ll_narmm_product,ll_whether,ll_ask_expert,
            ll_helpline,ll_local_retail,ll_complain,ll_my_order,ll_comp_profile,ll_diary;

    public Dashboard_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
       // setHasOptionsMenu(true);

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.app_name));
        ((MainActivity) getActivity()).updateHeader();

        ll_dashboard=view.findViewById(R.id.ll_dashboard);
        ll_profile=view.findViewById(R.id.ll_profile);
        ll_trail_pop=view.findViewById(R.id.ll_trail_pop);
        ll_narmm_product=view.findViewById(R.id.ll_narmm_product);
        ll_whether=view.findViewById(R.id.ll_whether);
        ll_ask_expert=view.findViewById(R.id.ll_ask_expert);
        ll_helpline=view.findViewById(R.id.ll_helpline);
        ll_local_retail=view.findViewById(R.id.ll_local_retail);
        ll_complain=view.findViewById(R.id.ll_complain);
        ll_my_order=view.findViewById(R.id.ll_my_order);
        ll_comp_profile=view.findViewById(R.id.ll_comp_profile);
        ll_diary=view.findViewById(R.id.ll_diary);

        ll_dashboard.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_trail_pop.setOnClickListener(this);
        ll_narmm_product.setOnClickListener(this);
        ll_whether.setOnClickListener(this);
        ll_ask_expert.setOnClickListener(this);
        ll_helpline.setOnClickListener(this);
        ll_local_retail.setOnClickListener(this);
        ll_complain.setOnClickListener(this);
        ll_my_order.setOnClickListener(this);
        ll_comp_profile.setOnClickListener(this);
        ll_diary.setOnClickListener(this);



        return view;
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//        super.onCreateOptionsMenu(menu, inflater);
//
//        MenuItem search = menu.findItem(R.id.action_search);
//        search.setVisible(true);
//        MenuItem check = menu.findItem(R.id.action_change_password);
//        check.setVisible(false);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.action_search:
//                Fragment fm = new Search_fragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                        .addToBackStack(null).commit();
//                return false;
//        }
//        return false;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_dashboard:
                Fragment fm_home = new Home_fragment();
                load_fragmnet(fm_home);
                break;
            case R.id.ll_profile:
                Fragment ll_profile = new Edit_profile_fragment();
                load_fragmnet(ll_profile);
                break;
            case R.id.ll_trail_pop:
                Fragment ll_trail_pop = new TrailPop_fragment();
                load_fragmnet(ll_trail_pop);
                break;
            case R.id.ll_narmm_product:
                Fragment ll_narmm_product = new Narmm_pro_fragment();
                load_fragmnet(ll_narmm_product);
                break;

            case R.id.ll_whether:
                Fragment ll_whether = new Weather_fragment();
                load_fragmnet(ll_whether);
                break;

            case R.id.ll_ask_expert:
                Fragment ll_ask_expert = new AskExpert_fragment();
                load_fragmnet(ll_ask_expert);
                break;

            case R.id.ll_helpline:
                Fragment ll_helpline = new Helpline_fragment();
                load_fragmnet(ll_helpline);
                break;
            case R.id.ll_local_retail:
                Fragment ll_local_retail = new Local_Retailer_fragment();
                load_fragmnet(ll_local_retail);
                break;

            case R.id.ll_complain:
                Fragment ll_complain = new Complain_fragment();
                load_fragmnet(ll_complain);
                break;

            case R.id.ll_my_order:
                Fragment ll_my_order = new My_order_fragment();
                load_fragmnet(ll_my_order);
                break;
            case R.id.ll_comp_profile:
                Intent intent=new Intent(getActivity(),CompanyProfile.class);
                intent.putExtra("profile","profile");
                startActivity(intent);
                break;
            case R.id.ll_diary:
                Fragment ll_diary = new My_Diary_fragment();
                load_fragmnet(ll_diary);

                break;


        }
    }

    private void load_fragmnet(Fragment fm_home) {
        if (fm_home != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm_home)
                    .addToBackStack(null).commit();
        }

    }
}
