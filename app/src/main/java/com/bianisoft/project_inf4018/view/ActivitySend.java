package com.bianisoft.project_inf4018.view;

import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bianisoft.engine.FrontendApp;
import com.bianisoft.project_inf4018.R;
import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.controller.ModMgrPosterToServer;
import com.bianisoft.project_inf4018.controller.ModMgrRecorder;

import java.io.IOException;

public class ActivitySend extends AppCompatActivity {
    private Button btSendSendData;
    private Button btSendQuit;
    private EditText txtServerName;
    private TextView txtXMLResult;
    private TextView LblSendResult;

    private Handler timerHandlerManage;
    private Runnable timerManage;

    ModMgrPosterToServer objPoster;


    private void onClickbtSendSendData(){
        System.out.print(txtServerName.getText().toString() + "\n");
        System.out.print(txtXMLResult.getText().toString() + "\n");

        timerHandlerManage = new Handler();
        timerManage = new Runnable() {
            @Override
            public void run() {
                onTimerManage(this);
            }
        };
        timerHandlerManage.postDelayed(timerManage, 1000);

        String strServerName = txtServerName.getText().toString() + "/rest_service";
        String strXMLResult = txtXMLResult.getText().toString();
        objPoster = (ModMgrPosterToServer) new ModMgrPosterToServer().execute(strXMLResult, strServerName);
    }

    private void onClickbtSendQuit(){
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        String strEmptyXML = "<EventsLog>\n\t<UserID></UserID>\n\t<EpochTime></EpochTime>\n</EvenstLog>";
        String strFinalMovement = ApplicationFacade.getFacadeObject().getMgrRecorder().getDOMString();

        if(strFinalMovement.length() == 0)
            strFinalMovement= strEmptyXML;

        txtXMLResult = findViewById(R.id.LblXMLResulted);
        txtXMLResult.setText(strFinalMovement.toCharArray(), 0, strFinalMovement.length());

        txtServerName = findViewById(R.id.txtServerName);
        LblSendResult = findViewById(R.id.LblSendResult);
        btSendSendData=  findViewById(R.id.btSendSendData);
        btSendSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickbtSendSendData();
            }
        });

        btSendQuit=  findViewById(R.id.btSendQuit);
        btSendQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickbtSendQuit();
            }
        });
    }

    protected void onTimerManage(Runnable pRunnable){

        if(!objPoster.bIsCompleted)
            timerHandlerManage.postDelayed(pRunnable, 1000);
        else{
            if(!objPoster.bIsSuccess) {
                String errorMsg = "Connection Failed" + objPoster.strDetailsError;
                LblSendResult.setText(errorMsg.toCharArray(), 0, errorMsg.length());
            }else{
                String errorMsg = "Connection Success";
                LblSendResult.setText(errorMsg.toCharArray(), 0, errorMsg.length());
            }
        }
    }

}
