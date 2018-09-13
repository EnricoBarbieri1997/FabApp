package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simonegherardi.enricobarbieri.fabapp.MainActivity;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.Resources.SingleUser;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ISyncObserver;
import com.simonegherardi.enricobarbieri.fabapp.restapi.ResourceSynchronizer;


public class SignUpFragment extends IntegratedFragment implements ISyncObserver{
    private TextView phone;
    private TextView email;
    private TextView password;
    private ProgressBar progressBar;
    private Button register;
    private TextView login;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mainActivity = (MainActivity) context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must be of type MainActivitty");
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phone = getView().findViewById(R.id.phone);
        email = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.signUpPassword);
        progressBar = getView().findViewById(R.id.signUpProgressBar);
        register = getView().findViewById(R.id.signUpButton);
        login = getView().findViewById(R.id.signInFragmentLink);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.this.mainActivity.SwapFragment(new SignInFragment());
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SingleUser su = SingleUser.Empty();
                su.Init(phone.getText().toString(), email.getText().toString(), password.getText().toString());
                ResourceSynchronizer rsu = new ResourceSynchronizer(su, SignUpFragment.this);
                rsu.Upload();
                progressBar.setVisibility(View.VISIBLE);
                register.setClickable(false);
            }
        });
    }

    @Override
    public void UploadStart() {

    }

    @Override
    public void UpdateStart() {

    }

    @Override
    public void DownloadStart() {

    }

    @Override
    public void DeleteStart() {

    }

    @Override
    public void UploadComplete(boolean result) {
        progressBar.setVisibility(View.INVISIBLE);
        register.setClickable(true);
    }

    @Override
    public void UpdateComplete(boolean result) {

    }

    @Override
    public void DownloadComplete(boolean result) {

    }

    @Override
    public void DeleteComplete(boolean result) {

    }
}
