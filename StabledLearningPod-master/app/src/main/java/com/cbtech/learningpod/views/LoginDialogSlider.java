package com.cbtech.learningpod.views;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbtech.learningpod.R;
import com.cbtech.learningpod.adapters.LoginDialogViewPager.LoginDialogViewPagerAdapter;


public class LoginDialogSlider extends Fragment   {

    public ViewPager viewPager;
    public TabLayout tabLayout;
    private String[] tabTitles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login_dialog_slider, container, false);

        init(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Login/Register");
    }

    private void init(View view) {

        tabTitles = new String[]{"Login", "Register"};

        viewPager = view.findViewById(R.id.view_pager_login);
        viewPager.setAdapter(new LoginDialogViewPagerAdapter(getChildFragmentManager(), tabTitles));

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }



}
