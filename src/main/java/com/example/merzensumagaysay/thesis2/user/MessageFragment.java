package com.example.merzensumagaysay.thesis2.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.merzensumagaysay.thesis2.R;
import com.example.merzensumagaysay.thesis2.isproj2;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {


    public MessageFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((isproj2)getActivity()).setActionBarTitle("Message Admin");


        return inflater.inflate(R.layout.fragment_message, container, false);
    }



}