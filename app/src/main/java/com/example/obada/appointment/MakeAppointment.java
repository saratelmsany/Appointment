package com.example.obada.appointment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MakeAppointment extends Fragment {

    private EditText name,mail,phone,code;
    private Button reserve;
    //   private ImageView image;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    //public String userId;

    private String sName,sMail,sPhone,sCode;


    public MakeAppointment() {
        // Required empty public constructor
    }


    public static MakeAppointment newInstance(String param1, String param2) {
        MakeAppointment fragment = new MakeAppointment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_make_appointment, container, false);

        name=rootView.findViewById(R.id.name);
        mail=rootView.findViewById(R.id.email);
        phone=rootView.findViewById(R.id.phone);
        code=rootView.findViewById(R.id.code);
        reserve=rootView.findViewById(R.id.reserve);

        mDatabase=FirebaseDatabase.getInstance().getReference();
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserve();
                hideKeyboard(getActivity());
            }
        });

        return rootView;
    }

    public void reserve(){
    sName=name.getText().toString().trim();
    sMail=mail.getText().toString().trim();
    sCode=code.getText().toString().trim();
    sPhone=phone.getText().toString().trim();

        if (TextUtils.isEmpty(sName)) {
            Toast.makeText(getActivity(), "PLEASE ENTER YOUR NAME", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sMail)) {
            Toast.makeText(getActivity(), "PLEASE ENTER EMAIL", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sCode)) {
            Toast.makeText(getActivity(), "PLEASE ENTER RESERVATION CODE", Toast.LENGTH_LONG).show();
            return;
        }

        if(isEmailValid(sMail) == false){
            Toast.makeText(getActivity(),"MAIL INCORRECT",Toast.LENGTH_LONG).show();
            return;
        }
        if(sCode.length()<2){
            Toast.makeText(getActivity(),"RESERVATION CODE LESS THAN 2 CHARACTER",Toast.LENGTH_LONG).show();
            return;
        }
        if(sPhone.matches("[0-9]+")){}
        else {
            Toast.makeText(getActivity(),"PHONE NUMBER INCORRECT",Toast.LENGTH_LONG).show();
            return;
        }
        UserModel userData=new UserModel(sName,sMail,sPhone,sCode);
        String id = mDatabase.push().getKey();
        mDatabase.child("resrvations").child(id).setValue(userData);

        Toast.makeText(getActivity(),"RESERVED SUCCESSFULLY",Toast.LENGTH_LONG).show();
        name.setText("");
        mail.setText("");
        phone.setText("");
        code.setText("");


    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}