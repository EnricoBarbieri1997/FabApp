package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simonegherardi.enricobarbieri.fabapp.MainActivity;
import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.activity.FragmentAwareActivity;
import com.simonegherardi.enricobarbieri.fabapp.restapi.HttpMethod;
import com.simonegherardi.enricobarbieri.fabapp.restapi.IRESTable;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSON;
import com.simonegherardi.enricobarbieri.fabapp.restapi.JSONParseException;
import com.simonegherardi.enricobarbieri.fabapp.restapi.RESTResponse;
import com.simonegherardi.enricobarbieri.fabapp.restapi.Table;
import com.simonegherardi.enricobarbieri.fabapp.restapi.WebServer;

public class SignInFragment extends IntegratedFragment implements IRESTable {


    private enum SIGNINSTATUS
    {
        ACTIVE,
        INACTIVE
    }
    private enum LOGINSTATUS
    {
        FAILED,
        SUCCEDED,
        PENDING
    }
    private TextView identifier;
    private TextView password;
    private ProgressBar progressBar;
    private Button login;
    private RESTResponse response;

    private RESTResponse emailResponse;
    private RESTResponse phoneResponse;
    private LOGINSTATUS emailResult = LOGINSTATUS.PENDING;
    private LOGINSTATUS phoneResult = LOGINSTATUS.PENDING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_signin, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        identifier = getView().findViewById(R.id.identifier);
        password = getView().findViewById(R.id.signInPassword);
        progressBar = getView().findViewById(R.id.signInProgressBar);
        login = getView().findViewById(R.id.signInButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emailResponse = SignInFragment.this.response = WebServer.Main().GenericRequest(HttpMethod.GET, Table.SingleUser, "email", identifier.getText().toString(), SignInFragment.this);
                phoneResponse = SignInFragment.this.response = WebServer.Main().GenericRequest(HttpMethod.GET, Table.SingleUser, "phone", identifier.getText().toString(), SignInFragment.this);
                SetSignIn(SIGNINSTATUS.INACTIVE);
            }
        });
    }

    @Override
    public void Success(RESTResponse response) {

        if ((new JSON(response.GetResponse())).GetString("password").equals(password.getText().toString())) {
            SetStatus(response, LOGINSTATUS.SUCCEDED);
        }
        else
        {
            SetStatus(response, LOGINSTATUS.FAILED);
        }
        Login();
    }

    @Override
    public void Error(RESTResponse response) {
        SetStatus(response, LOGINSTATUS.FAILED);
        Login();
    }
    private void SetSignIn(SIGNINSTATUS signinstatus)
    {
        if(signinstatus == SIGNINSTATUS.ACTIVE)
        {
            login.setClickable(true);
            progressBar.setVisibility(View.INVISIBLE);
        }
        else
        {
            login.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    private void SetStatus(RESTResponse response, LOGINSTATUS loginstatus)
    {
        if(response == this.emailResponse)
        {
            this.emailResult = loginstatus;
        }
        else if(response == this.phoneResponse)
        {
            this.phoneResult = loginstatus;
        }
    }
    private void ResetStatus()
    {
        this.emailResult = LOGINSTATUS.PENDING;
        this.phoneResult = LOGINSTATUS.PENDING;
    }

    private void Login()
    {
        if(this.emailResult != LOGINSTATUS.PENDING && this.phoneResult != LOGINSTATUS.PENDING)
        {
            if(this.emailResult == LOGINSTATUS.SUCCEDED || this.phoneResult == LOGINSTATUS.SUCCEDED)
            {
                RESTResponse response = this.phoneResponse;
                if(this.emailResult == LOGINSTATUS.SUCCEDED)
                {
                    response = this.emailResponse;
                }
                JSON userInfo = new JSON(response.GetResponse());
                SharedPreferences.Editor editor = this.parentActivity.userSharedPref.edit();
                try {
                    editor.putInt(getString(R.string.idKey), userInfo.GetInt("id"));
                    editor.putString(getString(R.string.emailKey), userInfo.GetString("email"));
                    editor.putString(getString(R.string.phoneKey), userInfo.GetString("phone"));
                    editor.putInt(getString(R.string.pictureKey), userInfo.GetInt("picture"));
                    editor.putString(getString(R.string.passwordKey), userInfo.GetString("password"));

                } catch (JSONParseException e) {
                    e.printStackTrace();
                }
                editor.commit();
                DisplayToast("Login successful");

                this.parentActivity.startActivity(this.parentActivity.GetBoardActivity());
                this.parentActivity.finish();
            }
            else
            {
                ResetStatus();
                SetSignIn(SIGNINSTATUS.ACTIVE);
                DisplayToast("Login unsuccessful");
            }
        }
    }

}
