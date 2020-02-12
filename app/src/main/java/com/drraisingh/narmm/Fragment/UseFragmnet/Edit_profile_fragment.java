package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drraisingh.narmm.Adapter.Delivery_get_address_adapter;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.Profile_model.CompanyProfileModel;
import com.drraisingh.narmm.Model.Profile_model.UserProfileModel;
import com.drraisingh.narmm.Model.Profile_model.UserProfileModelData;
import com.drraisingh.narmm.Model.RegistraionModel.FarmerRegistrationModel;
import com.drraisingh.narmm.Model.UseableModel.Shop_address_model;
import com.drraisingh.narmm.Model.state_model.StateModel;
import com.drraisingh.narmm.Model.state_model.StateModelData;
import com.drraisingh.narmm.activity.CompanyProfile;
import com.drraisingh.narmm.activity.LoginActivity;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.Registrationpage;
import com.drraisingh.narmm.databinding.FragmentEditProfileBinding;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.JSONParser;
import com.drraisingh.narmm.util.NameValuePair;
import com.drraisingh.narmm.util.Session_management;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rxandroidnetworking.RxAndroidNetworking;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_block;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_district;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_state;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_tehsil;
import static com.drraisingh.narmm.Config.BaseURL.GET_getprofile;
import static com.drraisingh.narmm.Config.BaseURL.GET_update_profile;
import static com.drraisingh.narmm.Config.BaseURL.REGISTER_URL;
import static com.drraisingh.narmm.Config.BaseURL.company_profile;

/**
 * Created by Rajesh Dabhi on 28/6/2017.
 */

public class Edit_profile_fragment extends Fragment implements View.OnClickListener {
    FragmentEditProfileBinding binding;
    private static String TAG = Edit_profile_fragment.class.getSimpleName();
    private int PICK_IMAGE_REQUEST = 1;
    File filePath;
    private static final int GALLERY_REQUEST_CODE1 = 201;
    private Bitmap bitmap;
    private Uri imageuri;
    private Session_management sessionManagement;
    String user_id;
    private String Gender, imageStoragePath;
    private CircleImageView img_profile;
    private String state_id, distt_id, Tehseel_Id, Block_id;

    List<StateModelData> stateModelList = new ArrayList<>();
    List<StateModelData> disttModelList = new ArrayList<>();
    List<StateModelData> tehtModelList = new ArrayList<>();
    List<StateModelData> blockModelList = new ArrayList<>();
    List<String> stateLists_name = new ArrayList<>();
    List<String> disttlists_name = new ArrayList<>();
    List<String> tehlists_name = new ArrayList<>();
    List<String> blocklists_name = new ArrayList<>();

    UserProfileModelData userProfileModelData;

    public Edit_profile_fragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);//using data binding

        View view = binding.getRoot();
        // View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setHasOptionsMenu(true);

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.edit_profile));

        sessionManagement = new Session_management(getActivity());
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
        String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
        String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
        String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        String getphone = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);

        if (ConnectivityReceiver.isConnected()) {
            getStateApi();
            getProfile();
        } else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.btnProEdit.setOnClickListener(this);
        binding.ivProImg.setOnClickListener(this);


        //*****************state selected
        binding.state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    disttModelList.clear();
                } catch (Exception e) {

                }
                state_id = stateModelList.get(position).getId();
                CallDisttApi(state_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //*****************distt selected
        binding.dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    tehtModelList.clear();
                } catch (Exception e) {

                }
                distt_id = disttModelList.get(position).getId();
                CallTehseelApi(distt_id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //*****************teh selected
        binding.teh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    blockModelList.clear();
                } catch (Exception e) {

                }
                Tehseel_Id = tehtModelList.get(position).getId();
                CallBlockApi(Tehseel_Id);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //*****************teh selected
        binding.block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    // blockModelList.clear();
                } catch (Exception e) {

                }
                Block_id = blockModelList.get(position).getId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void CallBlockApi(String block_id) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_get_block)
                .addBodyParameter("id", block_id)
                .build()
                .getObjectObservable(StateModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("res_pro_del", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            blockModelList.clear();
                            blocklists_name.clear();
                            Log.e("res_pro_det", "" + user.getResponce());
                            if (user.getResponce() == true) {
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                blockModelList = user.getData();
                                for (int i = 0; i < user.getData().size(); i++) {
                                    blocklists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item, blocklists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.block.setAdapter(spinnerArrayAdapter);


                            } else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        } catch (Exception e) {
                            Log.e("reg_rescatch", e.getMessage());
                        }

                    }
                });
    }

    private void CallTehseelApi(String distt_id) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_get_tehsil)
                .addBodyParameter("id", distt_id)
                .build()
                .getObjectObservable(StateModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("res_pro_del", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            tehtModelList.clear();
                            tehlists_name.clear();
                            Log.e("res_pro_det", "" + user.getResponce());
                            if (user.getResponce() == true) {
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                tehtModelList = user.getData();
                                for (int i = 0; i < user.getData().size(); i++) {
                                    tehlists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item, tehlists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.teh.setAdapter(spinnerArrayAdapter);


                            } else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        } catch (Exception e) {
                            Log.e("reg_rescatch", e.getMessage());
                        }

                    }
                });
    }

    private void CallDisttApi(String state_id) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_get_district)
                .addBodyParameter("state_id", state_id)
                .build()
                .getObjectObservable(StateModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("res_pro_del", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            disttModelList.clear();
                            disttlists_name.clear();
                            Log.e("res_pro_det", "" + user.getResponce());
                            if (user.getResponce() == true) {
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                disttModelList = user.getData();
                                for (int i = 0; i < user.getData().size(); i++) {
                                    disttlists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item, disttlists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.dist.setAdapter(spinnerArrayAdapter);


                            } else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        } catch (Exception e) {
                            Log.e("reg_rescatch", e.getMessage());
                        }

                    }
                });

    }

    private void getStateApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.get(GET_get_state)
                //.addBodyParameter("product_id", product_id)
                .build()
                .getObjectObservable(StateModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("res_pro_del", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            Log.e("res_pro_det", "" + user.getResponce());
                            if (user.getResponce() == true) {
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                stateModelList = user.getData();
                                for (int i = 0; i < user.getData().size(); i++) {
                                    stateLists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item, stateLists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.state.setAdapter(spinnerArrayAdapter);


                            } else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        } catch (Exception e) {
                            Log.e("reg_rescatch", e.getMessage());
                        }

                    }
                });

    }

    private void getProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", user_id);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_getprofile, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("GET_getprofile", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    Log.e("GET_getprostatus", "" + status);
                    if (status) {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<UserProfileModelData>() {
                        }.getType();

                        userProfileModelData = gson.fromJson(response.getString("data"), listType);
                        setCompanyProfile(userProfileModelData);

                        if (!userProfileModelData.getProfileImage().isEmpty() && userProfileModelData.getProfileImage() != null) {

                            Glide.with(getActivity())
                                    .load(BaseURL.IMG_PROFILE_URL + userProfileModelData.getProfileImage())
                                    //.placeholder(R.drawable.logo)
                                    // .crossFade()
                                    .into(binding.ivProImg);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ProfileError1: ", "" + e.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                VolleyLog.d("Error: " + error.getMessage());
                Log.e("ProfileError: ", "" + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void setCompanyProfile(UserProfileModelData user) {

        binding.fullName.setText(user.getFullName());
        binding.fatherName.setText(user.getFatherName());
//        binding.state.setText(user.getState());
//        binding.dist.setText(user.getDistrict());
//        binding.teh.setText(user.getTehsil());
//        binding.block.setText(user.getBlock());
        binding.vill.setText(user.getVillage());
        binding.pin.setText(user.getPincode());
        binding.mob.setText(user.getMobileNo());
        binding.adhar.setText(user.getAadharNo());
        binding.email.setText(user.getEmail());
        binding.houseNo.setText(user.getAddress());

        binding.spinAcre.setText(user.getTotalAcres());
        binding.spinCorp1.setText(user.getCropFirst());
        binding.spinCorp2.setText(user.getCropSecond());
        binding.corp3.setText(user.getCropThird());
        binding.corpOther.setText(user.getCropOther());

        binding.corp1Acre.setText(user.getCropFirstQty());
        binding.spinCorp2Qty.setText(user.getCropSecondQty());
        binding.corp3Acre.setText(user.getCropThirdQty());
        binding.corpotherQty.setText(user.getCropOtherQty());

        if (!user.getProfileImage().isEmpty() && user.getProfileImage() != null) {

            Glide.with(getActivity())
                    .load(BaseURL.IMG_PROFILE_URL + user.getProfileImage())
                    //.placeholder(R.drawable.logo)
                    .crossFade()
                    .into(binding.ivProImg);
        }

        try {

            String dob = user.getDob();
            String last2Digit = dob.substring(dob.length() - 2);
            binding.spinDate.setSelection(Integer.parseInt(last2Digit));

            String year = dob.substring(0, 4);
            //**set dob
            String remove_year = dob.substring(5);
            String remove_date = remove_year.substring(0, 2);
            binding.spinMonth.setSelection(Integer.parseInt(remove_date));
            Log.e("_year", year);
            Log.e("remove_year", remove_year);
            Log.e("only_month", remove_date);

            ArrayList<String> year_san = new ArrayList<>();

            for (int i = 1974; i < 2019; i++) {
                year_san.add(String.valueOf(i));
            }
            for (int i = 0; i < year_san.size(); i++) {
                if (year_san.get(i).equalsIgnoreCase(year)) {
                    binding.spinYear.setSelection(i);
                    Log.e("year_first", "" + i);
                }
            }

            if (stateModelList != null) {

                for (int i = 0; i < stateModelList.size(); i++) {
                    if (stateModelList.get(i).getName().equalsIgnoreCase(user.getState())) {
                        binding.state.setSelection(i);
                        Log.e("state_set", "" + i);
                    }
                }
            }

            if (disttModelList != null) {

                for (int i = 0; i < disttModelList.size(); i++) {
                    if (disttModelList.get(i).getName().equalsIgnoreCase(user.getDistrict())) {
                        binding.dist.setSelection(i);
                        Log.e("distt_set", "" + i);
                    }
                }
            }

            if (tehtModelList != null) {

                for (int i = 0; i < tehtModelList.size(); i++) {
                    if (tehtModelList.get(i).getName().equalsIgnoreCase(user.getTehsil())) {
                        binding.teh.setSelection(i);
                        Log.e("teh_set", "" + i);
                    }
                }
            }

            if (blockModelList != null) {

                for (int i = 0; i < blockModelList.size(); i++) {
                    if (blockModelList.get(i).getName().equalsIgnoreCase(user.getBlock())) {
                        binding.block.setSelection(i);
                        Log.e("block_set", "" + i);
                    }
                }
            }


        } catch (Exception e) {

        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_pro_edit) {
            attemptEditProfile();
        } else if (id == R.id.iv_pro_img) {
            if (checkIfAlreadyhavePermission()) {
                selectImage(getActivity());
            } else {
                check_permission();
            }
        }
    }

    private void attemptEditProfile() {
        String Fullname = binding.fullName.getText().toString();
        String Fathername = binding.fatherName.getText().toString();
        if (binding.radioF.isChecked()) {
            Gender = "Female";
        } else {
            Gender = "Male";
        }
        String spindate = binding.spinDate.getSelectedItem().toString();
        String spin_month = binding.spinMonth.getSelectedItem().toString();
        String spin_year = binding.spinYear.getSelectedItem().toString();
        String village = binding.vill.getText().toString();
        String house_no = binding.houseNo.getText().toString();
//        String block=binding.block.getText().toString();
//        String teh=binding.teh.getText().toString();
//        String dist=binding.dist.getText().toString();
//        String state=binding.state.getText().toString();
        String pin = binding.pin.getText().toString();
        String mob = binding.mob.getText().toString();
        String adhar = binding.adhar.getText().toString();
        String email = binding.email.getText().toString();

        //****using rxjava with retrofit fast networking
        if (filePath == null) {
            FarmerUpdate(Fullname, Fathername, Gender, spindate, spin_month, spin_year, village, Block_id, Tehseel_Id, distt_id, state_id, pin,
                    mob, adhar, email, house_no);
        } else {
            FarmerUpdate_img(Fullname, Fathername, Gender, spindate, spin_month, spin_year, village, Block_id, Tehseel_Id, distt_id, state_id, pin,
                    mob, adhar, email, house_no);
        }
        //.addMultipartFile("image", filePath)
    }

    // Without Image ... No Image Parameter in this one //
    private void FarmerUpdate(String fullname, String fathername, String gender, String spindate, String spin_month, String spin_year,
                              String village, String block, String teh, String dist, String state, String pin,
                              String mob, String adhar, String email, String house_no) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.upload(GET_update_profile)
                .addMultipartParameter("farmer_id", user_id)
                .addMultipartParameter("full_name", fullname)
                .addMultipartParameter("father_name", fathername)
                .addMultipartParameter("gender", gender)
                .addMultipartParameter("dob", spin_year + "/" + spin_month + "/" + spindate)
                .addMultipartParameter("village", village)
                .addMultipartParameter("block", block)
                .addMultipartParameter("tehsil", teh)
                .addMultipartParameter("district", dist)
                .addMultipartParameter("state", state)
                .addMultipartParameter("pincode", pin)
                .addMultipartParameter("mobile_no", mob)
                .addMultipartParameter("aadhar_no", adhar)
                .addMultipartParameter("email", email)
                .addMultipartParameter("address", house_no)

                .build()
                .getObjectObservable(FarmerRegistrationModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FarmerRegistrationModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("reg_rescomp", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(FarmerRegistrationModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            // Log.e("reg_responce",user.getData().getFullName());
                            Toast.makeText(getActivity(), "" + user.getResponce(), Toast.LENGTH_SHORT).show();

                            if (user.getResponce() == true) {
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                startActivity(i);
                                Session_management sessionManagement = new Session_management(getActivity());
                                sessionManagement.createLoginSession(user.getData().getFarmerId(), user.getData().getEmail(), user.getData().getFullName()
                                        , user.getData().getMobileNo(), user.getData().getProfile_image(), "", "", "", "", "");

                                Log.e("reg_responce", user.getData().getFullName());


                            } else {
                                Toast.makeText(getActivity(), "" + user.getError(), Toast.LENGTH_SHORT).show();
                                Log.e("reg_respo", user.getError());
                            }

                        } catch (Exception e) {
                            Log.e("reg_rescatch", e.getMessage());
                        }

                    }
                });
    }


    // With Image //
    private void FarmerUpdate_img(String fullname, String fathername, String gender, String spindate,
                                  String spin_month, String spin_year, String village, String block, String teh,
                                  String dist, String state, String pin, String mob, String adhar,
                                  String email, String house_no) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.upload(GET_update_profile)
                .addMultipartParameter("farmer_id", user_id)
                .addMultipartParameter("full_name", fullname)
                .addMultipartParameter("father_name", fathername)
                .addMultipartParameter("gender", gender)
                .addMultipartParameter("dob", spin_year + "/" + spin_month + "/" + spindate)
                .addMultipartParameter("village", village)
                .addMultipartParameter("block", block)
                .addMultipartParameter("tehsil", teh)
                .addMultipartParameter("district", dist)
                .addMultipartParameter("state", state)
                .addMultipartParameter("pincode", pin)
                .addMultipartParameter("mobile_no", mob)
                .addMultipartParameter("aadhar_no", adhar)
                .addMultipartParameter("email", email)
                .addMultipartParameter("address", house_no)
                .addMultipartFile("image", filePath)
                .build()
                .getObjectObservable(FarmerRegistrationModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FarmerRegistrationModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("reg_rescomp", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(FarmerRegistrationModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            // Log.e("reg_responce",user.getData().getFullName());
                            Toast.makeText(getActivity(), "" + user.getResponce(), Toast.LENGTH_SHORT).show();

                            if (user.getResponce() == true) {
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                startActivity(i);
                                Session_management sessionManagement = new Session_management(getActivity());
                                sessionManagement.createLoginSession(user.getData().getFarmerId(), user.getData().getEmail(), user.getData().getFullName()
                                        , user.getData().getMobileNo(), user.getData().getProfile_image(), "", "", "", "", "");

                                Log.e("reg_responce", user.getData().getFullName());


                            } else {
                                Toast.makeText(getActivity(), "" + user.getError(), Toast.LENGTH_SHORT).show();
                                Log.e("reg_respo", user.getError());
                            }

                        } catch (Exception e) {
                            Log.e("reg_rescatch", e.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem cart = menu.findItem(R.id.action_cart);
        cart.setVisible(false);
        MenuItem change_pass = menu.findItem(R.id.action_change_password);
        change_pass.setVisible(true);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_change_password:
                Fragment fm = new Change_password_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
                return false;
        }
        return false;
    }
    //////////// New Code For Profile Image //////////////

    private void selectImage(Activity context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(cameraIntent, 0);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        onCaptureImageResult(data);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageStoragePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                                binding.ivProImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                filePath = new File(imageStoragePath);
                                Log.e("imageStoragePath ", "GALLERY >>>>>>>________________     " + imageStoragePath);

                                cursor.close();
                            }
                        }
                        break;
                    }
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            if (destination != null) {
                imageStoragePath = destination.getAbsolutePath();
                filePath = new File(imageStoragePath);
                // Toast.makeText(getActivity(), "path is"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "something wrong", Toast.LENGTH_SHORT).show();
            }
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // update_PROFILE();
        binding.ivProImg.setImageBitmap(thumbnail);
    }

    void check_permission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                1);
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            check_permission();
            return false;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (userProfileModelData != null) {
            setCompanyProfile(userProfileModelData);
        }

    }
}
