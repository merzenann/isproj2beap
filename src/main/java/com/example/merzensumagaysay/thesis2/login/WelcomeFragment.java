package com.example.merzensumagaysay.thesis2.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.merzensumagaysay.thesis2.MessageDetailActivity;
import com.example.merzensumagaysay.thesis2.ModifyActivity;
import com.example.merzensumagaysay.thesis2.alert.AlertActivity;

public class WelcomeFragment extends Fragment {

    private TextView textView;
    private Button btnLogout;
    private Button btnAlert;
    private Button btnModify;
    private Button btnMessages;




    OnLogoutListener logoutListener;
    public interface  OnLogoutListener {
        public void logoutPerformed();
    }
    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_welcome, container, false);

        textView = view.findViewById(R.id.txtW);
        textView.setText("Welcome " + MainActivity.prefConfig.readName());
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutListener.logoutPerformed();

            }
        });

        btnAlert = view.findViewById(R.id.btnAlert);
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewdata = new Intent(getActivity(), AlertActivity.class);
                startActivity(viewdata);
            }
        });

        btnModify = view.findViewById(R.id.btnModify);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewdata = new Intent (getActivity(), ModifyActivity.class);
                startActivity(viewdata);
            }
        });

        btnMessages = view.findViewById(R.id.btnMessages);
        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewdata = new Intent (getActivity(), MessageDetailActivity.class);
                startActivity(viewdata);
            }
        });


        return view;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        logoutListener = (OnLogoutListener) activity;
    }
}