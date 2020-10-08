package com.example.mvvmexample.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mvvmexample.viewmodels.MainViewModel;
import com.example.mvvmexample.R;
import com.example.mvvmexample.models.User;
import com.example.mvvmexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel= new MainViewModel(getApplicationContext());
        user= new User();
        user.setEmailId("pradeep.bardewa@gmail.com");
        user.setName("Pradeep Bardewa");
        mainViewModel.setUser(user);
        activityMainBinding.setUserView(mainViewModel);
    }
}