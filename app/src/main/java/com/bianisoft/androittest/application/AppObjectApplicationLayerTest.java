package com.bianisoft.androittest.application;


public class AppObjectApplicationLayerTest {
    private static AppObjectApplicationLayerTest mSingletonInstance= null;
    public boolean misRunning= false;
    

    public static AppObjectApplicationLayerTest getAppObject(){
            if(mSingletonInstance != null)
                    return mSingletonInstance;

            return mSingletonInstance= new AppObjectApplicationLayerTest();
    }

    public static void sleep(int p_nMS){
        try{
            Thread.sleep(p_nMS);
        }catch(InterruptedException e){
        }
    }
    
    
    public AppObjectApplicationLayerTest(){
        
    }

    public void Load(){
        System.out.print("inside of Load\n");
        misRunning= true;
    }

    public void Main(){
        System.out.print("inside of Main\n");
        while(misRunning){
            onManage();
            sleep(100);
        }

        sleep(500);
    }

    public void onManage(){
        //Command for Start Record
        //Command for Stop Record
    }
}
