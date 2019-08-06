package com.bianisoft.project_inf4018.controller;

import android.os.AsyncTask;

import com.bianisoft.project_inf4018.model.DomainFacade;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;


public class ModMgrPosterToServer extends AsyncTask<String, Void, String> {
    public String strDetailsError;
    public boolean bIsCompleted;
    public boolean bIsSuccess;


    @Override
    protected String doInBackground(String... params) {
        String strXMLPost = params[0];
        String strServer = params[1];

        System.out.println(strXMLPost);
        bIsCompleted = false;
        bIsSuccess = false;

        // String stURL = "http://192.168.0.133:8081/rest_service";
        String stURL = strServer;
        stURL+= "?userID=" + DomainFacade.getFacadeObject().getWorld().getPlayerShip().GetPlayerName();
        stURL+= "&time=" + Calendar.getInstance().getTime().getTime();

        try {
            URL obj = new URL(stURL);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            // postConnection.setRequestProperty("userId", "a1bcdefgh");
            postConnection.setRequestProperty("Content-Type", "text/xml");
            postConnection.setDoOutput(true);

            OutputStream os = postConnection.getOutputStream();
            os.write(strXMLPost.getBytes());
            os.flush();
            os.close();

            int responseCode = postConnection.getResponseCode();
            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());

            bIsCompleted = true;
            if((responseCode == HttpURLConnection.HTTP_CREATED) || (responseCode == HttpURLConnection.HTTP_OK)){ //success
                System.out.println("POST WORKED");
                bIsSuccess= true;
            } else {
                strDetailsError = "HTTP_CREATED not right";
                System.out.println("POST NOT WORKED");
                bIsSuccess= false;
            }
        }catch(Exception e){
            strDetailsError = e.getMessage();
            bIsCompleted = true;
            bIsSuccess= false;
        }

        return "";
    }
}
