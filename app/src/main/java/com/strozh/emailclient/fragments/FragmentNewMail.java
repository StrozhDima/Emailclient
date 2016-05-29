package com.strozh.emailclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.woods.emailclient.R;

import java.io.FileInputStream;


public class FragmentNewMail extends Fragment {

    private Button sendMailButton;
    private String filepath;

    public FragmentNewMail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_mail, container, false);

        sendMailButton = (Button) view.findViewById(R.id.buttonSend);
        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }



}
