package com.teqhub.wmsgrotial.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teqhub.wmsgrotial.R;

public class SelectAccountScreen extends AppCompatActivity {

    Button managerLogin,staffLogin,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_screen);

        managerLogin = findViewById(R.id.managerlogin);
        staffLogin = findViewById(R.id.stafflogin);

        managerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(),LoginScreen.class).putExtra("type","Manager"));
            }
        });


        staffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(),LoginScreen.class).putExtra("type","Staff"));
            }
        });



    }
}