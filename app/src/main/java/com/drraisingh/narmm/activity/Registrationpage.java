package com.drraisingh.narmm.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.androidnetworking.AndroidNetworking;
import com.drraisingh.narmm.ActivityRegistrationpageBinding;
import com.drraisingh.narmm.Model.CropModel.CropModel;
import com.drraisingh.narmm.Model.CropModel.CropModelData;
import com.drraisingh.narmm.Model.RegistraionModel.FarmerRegistrationModel;
import com.drraisingh.narmm.Model.state_model.StateModel;
import com.drraisingh.narmm.Model.state_model.StateModelData;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.GPSTracker;
import com.drraisingh.narmm.util.Session_management;
import com.rxandroidnetworking.RxAndroidNetworking;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_get_block;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_district;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_state;
import static com.drraisingh.narmm.Config.BaseURL.GET_get_tehsil;
import static com.drraisingh.narmm.Config.BaseURL.REGISTER_URL;
import static com.drraisingh.narmm.Config.BaseURL.get_crop;


public class Registrationpage extends AppCompatActivity {
    ActivityRegistrationpageBinding binding;
    private int PICK_IMAGE_REQUEST = 1;
    ProgressDialog progressDoalog;
    private String Gender;
     Session_management sessionManagement;
    List<CropModelData> cropLists=new ArrayList<>();
    List<StateModelData> stateModelList=new ArrayList<>();
    List<StateModelData> disttModelList=new ArrayList<>();
    List<StateModelData> tehtModelList=new ArrayList<>();
    List<StateModelData> blockModelList=new ArrayList<>();
    List<String> cropLists_name=new ArrayList<>();
    List<String> stateLists_name=new ArrayList<>();
    List<String> disttlists_name=new ArrayList<>();
    List<String> tehlists_name=new ArrayList<>();
    List<String> blocklists_name=new ArrayList<>();
    GPSTracker tracker;
    double longitude, latitude;
     File filePath;
    int acar1 = 0,acar2=0,acar3=0,acar4=0;
    private String state_id,distt_id,Tehseel_Id,Block_id;
  //  private String crop_first,crop_other,crop_second,crop_third;
    String CropName_Id1,CropName_Id2,CropName_Id3,CropName_Id4;
    private String imageStoragePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(this, R.layout.activity_registrationpage);//using data binding
       // setContentView(R.layout.activity_registrationpage);
        sessionManagement = new Session_management(Registrationpage.this);
        AndroidNetworking.initialize(getApplicationContext());//initialize must be important api call networking

        try {
            tracker = new GPSTracker(Registrationpage.this);
            if (tracker.canGetLocation()) {
                latitude = tracker.getLatitude();
                longitude = tracker.getLongitude();
                Log.e("current_lat ", " " + latitude);
                Log.e("current_Lon ", " " + longitude);
               // address = getAddress(latitude, longitude);
               // Log.e("Address ", " " + getAddress(latitude, longitude));
            } else {
                tracker.showSettingsAlert();
            }
        } catch (Exception e) {

        }

        if (ConnectivityReceiver.isConnected()){
            getCropApi();
            getStateApi();
        }else {
            Toast.makeText(this, "Please check internet", Toast.LENGTH_SHORT).show();
        }

        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
//                startActivityForResult(i, PICK_IMAGE_REQUEST);


                if (checkIfAlreadyhavePermission()) {
                    selectImage(Registrationpage.this);
                } else {
                    check_permission();
                }

            }
        });

        //*****************state selected
        binding.state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    disttModelList.clear();
                }catch (Exception e){

                }
                state_id=stateModelList.get(position).getId();
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
                }catch (Exception e){

                }
                distt_id=disttModelList.get(position).getId();
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
                }catch (Exception e){

                }
                Tehseel_Id=tehtModelList.get(position).getId();
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
                }catch (Exception e){

                }
                Block_id=blockModelList.get(position).getId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinCorp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id1=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinCorp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id2=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.corp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id3=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.corpOther.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id4=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void selectImage(final Activity context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
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
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageStoragePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                                binding.imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                filePath = new File(imageStoragePath);
                                Log.e("imageStoragePath ", "GALLERY >>>>>>>____" + imageStoragePath);

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
                Toast.makeText(Registrationpage.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // update_PROFILE();
        binding.imageView1.setImageBitmap(thumbnail);
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(Registrationpage.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(Registrationpage.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            check_permission();
            return false;
        }
    }

    void check_permission() {
        ActivityCompat.requestPermissions(Registrationpage.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                1);
    }

    private void CallBlockApi(String block_id) {
        final ProgressDialog progressDialog = new ProgressDialog(Registrationpage.this,R.style.MyGravity);
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
                        Log.e("res_pro_del","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            blockModelList.clear();
                            blocklists_name.clear();
                            Log.e("res_pro_block",""+user.getResponce());
                            if (user.getResponce()==true){
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                blockModelList=user.getData();
                                for (int i=0; i<user.getData().size();i++){
                                    blocklists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (Registrationpage.this, android.R.layout.simple_spinner_item, blocklists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                             binding.block.setAdapter(spinnerArrayAdapter);


                            }else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });
    }

    private void CallTehseelApi(String distt_id) {
        final ProgressDialog progressDialog = new ProgressDialog(Registrationpage.this,R.style.MyGravity);
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
                        Log.e("res_pro_del","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            tehtModelList.clear();
                            tehlists_name.clear();
                            Log.e("res_pro_teh",""+user.getResponce());
                            if (user.getResponce()==true){
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                tehtModelList=user.getData();
                                for (int i=0; i<user.getData().size();i++){
                                    tehlists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (Registrationpage.this, android.R.layout.simple_spinner_item, tehlists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.teh.setAdapter(spinnerArrayAdapter);


                            }else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });
    }

    private void CallDisttApi(String state_id) {
        final ProgressDialog progressDialog = new ProgressDialog(Registrationpage.this,R.style.MyGravity);
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
                        Log.e("res_pro_del","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            disttModelList.clear();
                            disttlists_name.clear();
                            Log.e("res_pro_det",""+user.getResponce());
                            if (user.getResponce()==true){
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                disttModelList=user.getData();
                                for (int i=0; i<user.getData().size();i++){
                                    disttlists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (Registrationpage.this, android.R.layout.simple_spinner_item, disttlists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.dist.setAdapter(spinnerArrayAdapter);


                            }else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });

    }

    private void getStateApi() {
        final ProgressDialog progressDialog = new ProgressDialog(Registrationpage.this,R.style.MyGravity);
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
                        Log.e("res_pro_del","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(StateModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            Log.e("res_pro_det",""+user.getResponce());
                            if (user.getResponce()==true){
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                stateModelList=user.getData();
                                for (int i=0; i<user.getData().size();i++){
                                    stateLists_name.add(user.getData().get(i).getName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (Registrationpage.this, android.R.layout.simple_spinner_item, stateLists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.state.setAdapter(spinnerArrayAdapter);


                            }else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });

    }

    private void getCropApi() {
        final ProgressDialog progressDialog = new ProgressDialog(Registrationpage.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.get(get_crop)
                //.addBodyParameter("product_id", product_id)
                .build()
                .getObjectObservable(CropModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CropModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("res_pro_del","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(CropModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            cropLists.clear();
                            Log.e("res_pro_det",""+user.getResponce());
                            if (user.getResponce()==true){
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                cropLists=user.getData();
                                for (int i=0; i<user.getData().size();i++){
                                    cropLists_name.add(user.getData().get(i).getMValue());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (Registrationpage.this, android.R.layout.simple_spinner_item, cropLists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.spinCorp1.setAdapter(spinnerArrayAdapter);
                                binding.spinCorp2.setAdapter(spinnerArrayAdapter);
                                binding.corp3.setAdapter(spinnerArrayAdapter);
                                binding.corpOther.setAdapter(spinnerArrayAdapter);

                            }else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });

    }

    public void go_next(View view) {
        /*if(sessionManagement.isLoggedIn()) {*/

        String Fullname=binding.fullName.getText().toString();
        String Fathername=binding.fatherName.getText().toString();
        if (binding.radioF.isChecked()){
            Gender="Female";
        }else {
             Gender="Male";
        }
        String spindate=binding.spinDate.getSelectedItem().toString();
        String spin_month=binding.spinMonth.getSelectedItem().toString();
        String spin_year=binding.spinYear.getSelectedItem().toString();
        String village=binding.vill.getText().toString();
        String house_no=binding.houseNo.getText().toString();
       // String block=binding.block.getText().toString();
        //String teh=binding.teh.getText().toString();
        //String dist=binding.dist.getText().toString();
      //  String state=binding.state.getText().toString();
        String pin=binding.pin.getText().toString();
        String mob=binding.mob.getText().toString();
        String adhar=binding.adhar.getText().toString();
        String email=binding.email.getText().toString();
        String spinAcre=binding.spinAcre.getSelectedItem().toString();
       // String spinCorp1=binding.spinCorp1.getSelectedItem().toString();
        String corp1Acre=binding.corp1Acre.getSelectedItem().toString();
        //String spinCorp2=binding.spinCorp2.getSelectedItem().toString();
        String spinCorp2Qty=binding.spinCorp2Qty.getSelectedItem().toString();
       // String corp3=binding.corp3.getSelectedItem().toString();
        String corp3Acre=binding.corp3Acre.getSelectedItem().toString();
       // String corpOther=binding.corpOther.getSelectedItem().toString();
        String corpotherQty=binding.corpotherQty.getSelectedItem().toString();



        if (!spinAcre.equalsIgnoreCase("Acar")){

            int total_acar= Integer.parseInt(spinAcre);

            if (!corp1Acre.equalsIgnoreCase("Acar")){
                acar1= Integer.parseInt(corp1Acre);
            }if (!spinCorp2Qty.equalsIgnoreCase("Acar")){
                acar2= Integer.parseInt(spinCorp2Qty);
            } if (!corp3Acre.equalsIgnoreCase("Acar")){
                acar3= Integer.parseInt(corp3Acre);
            }if (!corpotherQty.equalsIgnoreCase("Acar")){
                acar4= Integer.parseInt(corpotherQty);
            }
          //  else {

              //  Toast.makeText(this, "Please select any one crop acar", Toast.LENGTH_SHORT).show();
            //}

            int selected_total=acar1+acar2+acar3+acar4;
            if (selected_total!=0){

                Log.e("Selected_Total",""+selected_total);
                Log.e("total_acar",""+total_acar);

                if (total_acar<selected_total){
                    Toast.makeText(this, "total acar not small crop acar calculation", Toast.LENGTH_LONG).show();
                }else {

                    //****using rxjava with retrofit fast networking
                    FarmerRegistration(Fullname,Fathername,Gender,spindate,spin_month,spin_year,village,Tehseel_Id,Block_id,distt_id,state_id,pin,
                            mob,adhar,email,spinAcre,CropName_Id1,corp1Acre,CropName_Id2,spinCorp2Qty,CropName_Id3,corp3Acre,
                            CropName_Id4,corpotherQty,house_no);

                }
            }else {
                Toast.makeText(this, "Please select any one crop acar", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "Please select Total Acar", Toast.LENGTH_SHORT).show();

        }


    }


    public void FarmerRegistration(String fullname, String fathername, String gender, String spindate,
                                   String spin_month, String spin_year, String village, String block, String teh,
                                   String dist, String state, String pin, String mob, String adhar, String email,
                                   String spinAcre, String spinCorp1, String corp1Acre, String spinCorp2,
                                   String spinCorp2Qty, String corp3, String corp3Acre, String corpOther,
                                   String corpotherQty, String house_no){
        final ProgressDialog progressDialog = new ProgressDialog(Registrationpage.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.upload(REGISTER_URL)
                .addMultipartParameter("full_name", fullname)
                .addMultipartParameter("father_name", fathername)
                .addMultipartParameter("gender", gender)
                .addMultipartParameter("dob", spin_year+"/"+spin_month+"/"+spindate)
                .addMultipartParameter("village", village)
                .addMultipartParameter("block", block)
                .addMultipartParameter("tehsil", teh)
                .addMultipartParameter("district", dist)
                .addMultipartParameter("state", state)
                .addMultipartParameter("pincode", pin)
                .addMultipartParameter("mobile_no", mob)
                .addMultipartParameter("aadhar_no", adhar)
                .addMultipartParameter("email", email)
                .addMultipartParameter("total_acres", spinAcre)
                .addMultipartParameter("crop_first", spinCorp1)
                .addMultipartParameter("crop_first_qty", corp1Acre)
                .addMultipartParameter("crop_second", spinCorp2)
                .addMultipartParameter("crop_second_qty", spinCorp2Qty)
                .addMultipartParameter("crop_third", corp3)
                .addMultipartParameter("crop_third_qty", corp3Acre)
                .addMultipartParameter("crop_other", corpOther)
                .addMultipartParameter("crop_other_qty", corpotherQty)
                .addMultipartParameter("latitude", String.valueOf(latitude))
                .addMultipartParameter("longitude", String.valueOf(longitude))
                .addMultipartParameter("address",house_no)
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
                        Log.e("reg_rescomp","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(FarmerRegistrationModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {

                           // Log.e("reg_responce",user.getData().getFullName());
                            Toast.makeText(Registrationpage.this, ""+user.getResponce(), Toast.LENGTH_SHORT).show();

                          if (user.getResponce()==true){
                              //sessionManagement.setLogin(true);
                              //Session_management sessionManagement = new Session_management(Registrationpage.this);
                             // sessionManagement.createLoginSession(user.getData().getFarmerId(),user.getData().getEmail(),user.getData().getFullName()
                              //        ,user.getData().getMobileNo(),user.getData().getProfile_image(),"","","","","");

                              Log.e("reg_responce",user.getData().getFullName());
                                Intent i = new Intent(Registrationpage.this, SetPinActivity.class);
                                i.putExtra("formar_id",user.getData().getFarmerId());
                                i.putExtra("Amount",user.getAmount().toString());
                                i.putExtra("GstAmt",user.getGst_amount());
                                i.putExtra("NetAmt",user.getNet_amount());
                                startActivity(i);
                                finish();
                           }else {
                              Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
                              Log.e("reg_respo",user.getError());
                          }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
////            Uri uri = data.getData();
////            filePath = new File(getPath(uri));
////            Log.e("filePath_rrr",""+filePath);
////            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
////                // Log.d(TAG, String.valueOf(bitmap));
////                binding.imageView1.setImageBitmap(bitmap);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            if (selectedImage != null) {
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                if (cursor != null) {
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    imageStoragePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
//                    binding.imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    filePath = new File(imageStoragePath);
//                    Log.e("imageStoragePath ","GALLERY >>"+imageStoragePath);
//                    cursor.close();
//                }
//            }
//
//        }
//    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

}
