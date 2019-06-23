package com.bianisoft.project_inf4018.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bianisoft.project_inf4018.R;

public class ActivityTitle extends AppCompatActivity {
    private Button btTitleLaunch;
    private Button btTitleQuit;

    private void onClickbtTitleLaunch(){
        Intent intentSwitchTitleToMain = new Intent(this, ActivityGame.class);
        startActivity(intentSwitchTitleToMain);
    }
    private void onClickbtTitleQuit(){
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        btTitleLaunch=  findViewById(R.id.btTitleLaunch);
        btTitleLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickbtTitleLaunch();
            }
        });


        btTitleQuit=  findViewById(R.id.btTitleQuit);
        btTitleQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickbtTitleQuit();
            }
        });
    }
}
