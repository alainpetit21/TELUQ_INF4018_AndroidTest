package com.bianisoft.androittest;

import com.bianisoft.engine.App;

public class MyApp extends App {
    public static final int IDCTX_MYCONTEXT	= 0x0;


    public MyApp(int nWidht, int nHeight){
        super("MyApp", nWidht, nHeight);
    }

    public String getVersion(){
        return "0.1";
    }

    public void load(){
        addContext(new MyContext1());
        setCurContext(IDCTX_MYCONTEXT);
    }

    public void manage(){
        super.manage();
    }
}