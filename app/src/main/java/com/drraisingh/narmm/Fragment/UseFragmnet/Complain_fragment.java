package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.Session_management;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.drraisingh.narmm.Config.BaseURL.GET_send_complaint;
import static com.drraisingh.narmm.Config.BaseURL.GET_send_enquiry;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class Complain_fragment extends Fragment {
    EditText edit_subject,edit_description;
    Button btn_submit;
    private Session_management sessionManagement;
    private String user_id;
    TextView tv_view_complain;


    public Complain_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_complain, container, false);
        ((MainActivity)  getActivity()).setTitle("Complain");

        sessionManagement = new Session_management(getActivity());
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        edit_subject=view.findViewById(R.id.et_subject);
        edit_description=view.findViewById(R.id.et_description);
        btn_submit=view.findViewById(R.id.btn_submit);
        tv_view_complain=view.findViewById(R.id.tv_view_complain);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Et_sub=edit_subject.getText().toString();
                String Et_description=edit_description.getText().toString();

                if (!Et_sub.isEmpty() && !Et_description.isEmpty()){
                    SubmitQuery(Et_description,Et_sub);

                }else {
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv_view_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment ll_complain = new ViewComplain_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, ll_complain)
                        .addToBackStack(null).commit();


            }
        });


        return view;
    }

    private void SubmitQuery(final String et_description, String et_sub) {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", user_id);
        params.put("message", et_description);
        params.put("subject", et_sub);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_send_complaint, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("local_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        Toast.makeText(getActivity(), "Send successful", Toast.LENGTH_SHORT).show();
                        edit_description.setText("");
                        edit_subject.setText("");

                    }else {
                        Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
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
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }



}
