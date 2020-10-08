package com.example.mvvmexample.viewmodels;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.example.mvvmexample.models.User;

public class MainViewModel extends ViewModel {


    private Context context;
    private User user;

    public  MainViewModel(Context context){
        this.context= context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


