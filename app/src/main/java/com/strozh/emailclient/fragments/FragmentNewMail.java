package com.strozh.emailclient.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.strozh.emailclient.LoginActivity;
import com.strozh.emailclient.MailSenderClass;
import com.woods.emailclient.R;


public class FragmentNewMail extends Fragment {

    private Button sendMailButton;
    Context context;
    SharedPreferences appSharedPreferences;

    final static String LOGIN = "login";
    final static String PASSWORD = "password";
    final static String HOST = "host";
    final static String PORT = "port";
    final static String LOGIN_FLAG = "loginFlag";

    private String user;
    private String password;
    private String host;
    private String port;

    String subject;
    String message;
    String where;
    String attach = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_mail, container, false);
        appSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        context = getContext();

        if (appSharedPreferences.getBoolean(LOGIN_FLAG, false)) {
            user = appSharedPreferences.getString(LOGIN, "");
            password = appSharedPreferences.getString(PASSWORD, "");
            host = appSharedPreferences.getString(HOST, "");
            port = appSharedPreferences.getString(PORT, "");
        } else {
            Toast.makeText(context, "You are not sing in...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }

        final EditText editTextRecipient = (EditText) view.findViewById(R.id.edit_text_recipient);
        final EditText editTextSubject = (EditText) view.findViewById(R.id.edit_text_subject);
        final EditText editTextMessage = (EditText) view.findViewById(R.id.edit_text_message);

        sendMailButton = (Button) view.findViewById(R.id.button_send);
        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                where = editTextRecipient.getText().toString().trim();
                subject = editTextSubject.getText().toString();
                message = editTextMessage.getText().toString();

                SenderMailAsync asyncMailSender = new SenderMailAsync();
                asyncMailSender.execute();
            }
        });

        return view;
    }

    public class SenderMailAsync extends AsyncTask<Object, String, Boolean> {
        ProgressDialog WaitingDialog;

        @Override
        protected void onPreExecute() {
            WaitingDialog = ProgressDialog.show(context, "Sending data", "Sending mail...", true);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            WaitingDialog.dismiss();
            Toast.makeText(context, "To sending is complite!!!", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }

        @Override
        protected Boolean doInBackground(Object... params) {

            try {

                MailSenderClass sender = new MailSenderClass(user, password, host, port);

                sender.sendMail(subject, message, user, where, attach);
            } catch (Exception e) {
                Toast.makeText(context, "Error to sending mail!", Toast.LENGTH_SHORT).show();
            }

            return false;
        }
    }

}
