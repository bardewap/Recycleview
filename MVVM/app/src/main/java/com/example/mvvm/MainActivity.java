package com.example.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);



        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(activityMainBinding.eTname.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Enter your name ", Toast.LENGTH_LONG).show();
                } else {

                    activityMainBinding.textView.setText("Welcome" + " " + activityMainBinding.eTname.getText().toString());
                    activityMainBinding.eTname.setText("");
                }
            }
        });

    }
}