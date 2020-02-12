package com.drraisingh.narmm.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.drraisingh.narmm.Fragment.UseFragmnet.AskExpert_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Complain_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Helpline_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Local_Retailer_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Narmm_pro_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.TrailPop_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Weather_fragment;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.BuildConfig;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.UseFragmnet.Dashboard_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Cart_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Edit_profile_fragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.My_order_fragment;

import cn.refactor.lib.colordialog.PromptDialog;

import com.drraisingh.narmm.Fragment.UseFragmnet.Support_info_fragment;
import com.drraisingh.narmm.Notification;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.fcm.MyFirebaseRegister;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.DatabaseHandler;
import com.drraisingh.narmm.util.Session_management;
import com.drraisingh.narmm.util.Utilities;

import static com.drraisingh.narmm.Config.BaseURL.GET_cart_counting;
import static com.drraisingh.narmm.Config.BaseURL.GET_send_complaint;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private TextView tv_name,tv_number;
    public static TextView totalBudgetCount;
    private ImageView iv_profile;

    private DatabaseHandler dbcart;
    String versionName = "";
    int versionCode = -1;
    private Session_management sessionManagement;

    private Menu nav_menu;
    String currentVersion, latestVersion;
    Dialog dialog;
    private String cart_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getVersionInfo();
      //  new Regisster().execute();
        dbcart = new DatabaseHandler(this);

        checkConnection();

        sessionManagement = new Session_management(MainActivity.this);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_menu = navigationView.getMenu();

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

        iv_profile = (ImageView) header.findViewById(R.id.iv_header_img);
        tv_name = (TextView) header.findViewById(R.id.tv_header_name);
        tv_number = (TextView) header.findViewById(R.id.tv_header_moblie);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                if(sessionManagement.isLoggedIn()) {
                    Fragment fm = new Edit_profile_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();
                }else{
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });

        updateHeader();
        if (ConnectivityReceiver.isConnected()){
            //GetCartCount();
        }
   /*     sideMenu();*/

        try {

            if (getIntent()!=null){
                if (getIntent().getStringExtra("Track").equalsIgnoreCase("Track")){
                    Fragment fm = new My_order_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.contentPanel, fm, "Home_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();

                }
            }else {
                if (savedInstanceState == null) {
                    Fragment fm = new Dashboard_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.contentPanel, fm, "Home_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            }

        }catch (Exception e){
            if (savedInstanceState == null) {
                Fragment fm = new Dashboard_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Home_fragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }





        if(sessionManagement.getUserDetails().get(BaseURL.KEY_ID) != null && !sessionManagement.getUserDetails().get(BaseURL.KEY_ID).equalsIgnoreCase("")) {
            MyFirebaseRegister fireReg = new MyFirebaseRegister(this);
            fireReg.RegisterUser(sessionManagement.getUserDetails().get(BaseURL.KEY_ID));
        }
    }

    private void GetCartCount() {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", sessionManagement.getUserDetails().get(BaseURL.KEY_ID));

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_cart_counting, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("local_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        cart_count = response.getString("count");
                        totalBudgetCount.setText(""+cart_count);

                    }else {
                       // Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                VolleyLog.d("Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    if (MainActivity.this != null) {
                        //Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);



}

    public void updateHeader(){
        if(sessionManagement.isLoggedIn()) {
            String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
            String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
            String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);

            if (!getimage.isEmpty() && getimage!=null){
                Glide.with(this)
                        .load(BaseURL.IMG_PROFILE_URL + getimage)
                        //.placeholder(R.drawable.logo)
                        .crossFade()
                        .into(iv_profile);
            }

            tv_name.setText(getname);
            tv_number.setText(getemail);
        }
    }


    public void setFinish(){
        finish();
    }

    public void setCartCounter(String totalitem) {
        totalBudgetCount.setText(totalitem);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem item = menu.findItem(R.id.action_cart);
        MenuItem c_password = menu.findItem(R.id.action_change_password);
        MenuItem search = menu.findItem(R.id.action_search);
        MenuItem what = menu.findItem(R.id.nav_whats);

        item.setVisible(true);
        c_password.setVisible(false);
        search.setVisible(false);
       what.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {

                 Intent intent=new Intent(MainActivity.this,MainActivity.class);
                 startActivity(intent);
                 finish();

               return false;
           }
       });



        View count = item.getActionView();
        count.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                menu.performIdentifierAction(item.getItemId(), 0);
            }
        });
        totalBudgetCount = (TextView) count.findViewById(R.id.actionbar_notifcation_textview);

        //totalBudgetCount.setText("" +cart_count );
        if (ConnectivityReceiver.isConnected()){
            GetCartCount();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

           // if(dbcart.getCartCount() >0) {
                Fragment fm = new Cart_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
          //  }else{
             //   Toast.makeText(MainActivity.this, "No item in cart", Toast.LENGTH_SHORT).show();
           // }


            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fm = null;
        Bundle args = new Bundle();

        if (id == R.id.nav_home) {
            Fragment fm_home = new Dashboard_fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contentPanel, fm_home, "Home_fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

        } else if (id == R.id.nav_myorders) {
            fm = new My_order_fragment();

        } else if (id == R.id.nav_myprofile) {
            fm = new Edit_profile_fragment();

        }

        //************products**************
        else if (id == R.id.nav_trail_pop) {
            fm = new TrailPop_fragment();

        }else if (id == R.id.nav_narmm_pro) {
            fm = new Narmm_pro_fragment();

        }else if (id == R.id.nav_whether) {
            fm = new Weather_fragment();

        }else if (id == R.id.nav_ask_an_expert) {
            fm = new AskExpert_fragment();

        }else if (id == R.id.nav_helpline) {
            fm = new Helpline_fragment();

        }
        else if (id == R.id.nav_local_retailer) {
            fm = new Local_Retailer_fragment();

        }else if (id == R.id.nav_complain) {
            fm = new Complain_fragment();

        }

        //*****************************************
        else if (id == R.id.nav_support) {
            fm = new Support_info_fragment();
            args.putString("url", BaseURL.GET_SUPPORT_URL);
            args.putString("title", getResources().getString(R.string.nav_support));
            fm.setArguments(args);
        } else if (id == R.id.nav_aboutus) {
            fm = new Support_info_fragment();
            args.putString("url", BaseURL.GET_ABOUT_URL);
            args.putString("title", getResources().getString(R.string.nav_about));
            fm.setArguments(args);
        } else if (id == R.id.nav_policy) {

            fm = new Support_info_fragment();
            args.putString("url", BaseURL.GET_TERMS_URL);
            args.putString("title", getResources().getString(R.string.nav_terms));
            fm.setArguments(args);
        } else if (id == R.id.nav_review) {
           reviewOnApp();
        } else if (id == R.id.nav_share) {
          shareApp();
        }
        else if (id == R.id.nav_notify) {
           Intent notify=new Intent(MainActivity.this, Notification.class);
           startActivity(notify);
        }
        else if (id == R.id.nav_logout) {
            sessionManagement.logoutSession();
            sessionManagement.createLoginSession("","","","","","","","","","");
            sessionManagement.setLogin(false);
            Intent i = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(i);
            finish();
        }


        if (fm != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void shareApp() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Narmm");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public void reviewOnApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(BaseURL.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(BaseURL.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (!isConnected) {
            message = ""+getResources().getString(R.string.no_internet);
            color = Color.RED;

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.coordinatorlayout), message, Snackbar.LENGTH_LONG)
                /*.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })*/;

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(BaseURL.PREFS_NAME, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            //txtRegId.setText("Firebase Reg Id: " + regId);
        }else {
            //txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }


    public void forceUpdate(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        new ForceUpdateAsync(currentVersion,MainActivity.this).execute();
    }

    public class ForceUpdateAsync extends AsyncTask<String, String, JSONObject>{

        private String latestVersion;
        private String currentVersion;
        private Context context;
        public ForceUpdateAsync(String currentVersion, Context context){
            this.currentVersion = currentVersion;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            try {
                latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+context.getPackageName()+"&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if(latestVersion!=null){
                if(!currentVersion.equalsIgnoreCase(latestVersion)){
                    // Toast.makeText(context,"update is available.",Toast.LENGTH_LONG).show();
                    if(!(context instanceof SplashActivity)) {
                        if(!((Activity)context).isFinishing()){
                            showForceUpdateDialog();
                        }
                    }
                }
            }
            super.onPostExecute(jsonObject);
        }

        public void showForceUpdateDialog(){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context,
                    R.style.AppTheme));

            alertDialogBuilder.setTitle(context.getString(R.string.youAreNotUpdatedTitle));
            alertDialogBuilder.setMessage(context.getString(R.string.youAreNotUpdatedMessage) + " " + latestVersion + context.getString(R.string.youAreNotUpdatedMessage1));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                    dialog.cancel();
                }
            });
            alertDialogBuilder.show();
        }
    }





    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    class Regisster extends AsyncTask<String,String,String> {
        String result=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                result = Utilities.getRequestAndfindJSON("https://harshadbhaifruitcompany.com/hfcmall/index.php/Api/version?");
            }
            catch (Exception e){}
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (result == null) {


            } else {
                try {

                    JSONObject json = new JSONObject(result);

                    if (json.getString("responce").equalsIgnoreCase("true")) {

                        if(versionCode<Integer.parseInt(json.getString("version_data"))){



                            PromptDialog promptDialog=  new PromptDialog(MainActivity.this);
                            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_INFO);
                            promptDialog.setAnimationEnable(true);
                            promptDialog.setTitleText("New Version Available");
                            promptDialog.setCancelable(false);
                            promptDialog.setCanceledOnTouchOutside(false);
                            promptDialog.setPositiveListener("Update", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                                }
                            }).show();

                        }


                    } else if (json.getString("responce").equalsIgnoreCase("false")) {

                    }

                } catch (Exception e) {
                    /*Utility.ShowToastMessage(mContext, "please enter correct username and password");*/

                    e.printStackTrace();
                }
            }



        }










    }




}
