package com.cbtech.learningpod.adapters.LoginDialogViewPager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cbtech.learningpod.views.LoginDialog;
import com.cbtech.learningpod.views.SignupDialog;

public class LoginDialogViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles;
    public LoginDialogViewPagerAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm);
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginDialog loginDialog = new LoginDialog();
                return loginDialog;
            case 1:
                SignupDialog signupDialog = new SignupDialog();
                return signupDialog;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
