package com.simonegherardi.enricobarbieri.fabapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.simonegherardi.enricobarbieri.fabapp.MainActivity;
import com.simonegherardi.enricobarbieri.fabapp.R;

public class IntegratedFragment extends Fragment {
    protected SharedPreferences userSharedPref;
    protected void DisplayToast(final String s)
    {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void SetTextTextView(final TextView textView, final String text)
    {
        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                textView.setText(text);
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.userSharedPref = context.getSharedPreferences(getString(R.string.userInfoFile), Context.MODE_PRIVATE);
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must be of type Context");
        }
    }
}
