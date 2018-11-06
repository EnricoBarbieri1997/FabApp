package com.simonegherardi.enricobarbieri.fabapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simonegherardi.enricobarbieri.fabapp.R;
import com.simonegherardi.enricobarbieri.fabapp.fragments.ImageGalleryFragment;
import com.simonegherardi.enricobarbieri.fabapp.fragments.ProfileFragment;
import com.simonegherardi.enricobarbieri.fabapp.requester.NewsFeedImageRequester;
import com.simonegherardi.enricobarbieri.fabapp.requester.ProfileImageRequester;

public abstract class FragmentAwareActivity extends FragmentActivity {

    protected FragmentManager fragmentManager;
    protected int container = 0;
    public SharedPreferences userSharedPref;
    protected Integer loggedUserId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        this.userSharedPref = this.userSharedPref = getSharedPreferences(getString(R.string.userInfoFile), Context.MODE_PRIVATE);
        loggedUserId = userSharedPref.getInt(getString(R.string.idKey),0);
    }
    public void SwapFragment(Fragment fragment)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(container, fragment);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }
    public void ReplaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }
    public void ClearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            ClearBackStack(first.getId());
        }
    }

    private void ClearBackStack(int fragmentId) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void PopVisible()
    {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
    }

    public void AddFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }

    public void SetFragment(Fragment fragment)
    {
        ClearBackStack();
        ReplaceFragment(fragment);
    }
    protected Fragment GetProfileInfoFragment(int id)
    {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(getString(R.string.idKey), id);
        fragment.setArguments(args);
        return fragment;
    }
    protected Fragment GetProfileGalleryFragment(int id)
    {
        ImageGalleryFragment fragment = new ImageGalleryFragment();
        fragment.imageRequester = new ProfileImageRequester();
        fragment.imageRequester.SetUserId(id);
        fragment.imageRequester.SetCallback(fragment);
        return fragment;
    }
    public Fragment GetNewsFeedImageGalleryFragment(int id)
    {
        ImageGalleryFragment fragment = new ImageGalleryFragment();
        fragment.SetColumnCount(1);
        fragment.imageRequester = new NewsFeedImageRequester();
        fragment.imageRequester.SetUserId(id);
        fragment.imageRequester.SetCallback(fragment);
        return fragment;
    }
    public Intent GetProfileActivity(int id)
    {
        Intent intent = new Intent(this.getApplicationContext(), ProfileActivity.class);
        Bundle b = new Bundle();
        b.putInt(getString(R.string.idKey), id); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        return intent;
    }
    public Intent GetActiveProfileActivity()
    {
        return GetProfileActivity(this.loggedUserId);
    }
    public Intent GetBoardActivity()
    {
        Intent intent = new Intent(this.getApplicationContext(), BoardActivity.class);
        return intent;
    }

    public void OnGalleryReady()
    {

    }
}
