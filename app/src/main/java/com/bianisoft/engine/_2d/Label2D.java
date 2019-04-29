package com.bianisoft.engine._2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLUtils;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Drawable;

import javax.microedition.khronos.opengles.GL10;

public class Label2D extends Drawable{
    private String strTextToDisplay;
    int[] textureIDs;


    public Label2D(){
        textureIDs = new int[1];
    }

    public void setText(String pText){
        strTextToDisplay= pText;
    }

    // Load an image into GL texture
    public void loadBackgroundTexture(GL10 gl, Context context, int nRes) {
    }

    public void load(){

    }
    
    public void draw(GL10 gl){
        // THIS IS THE CODE FOR ANDROID
        // Create an empty, mutable bitmap
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        // get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0);

        // Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(32);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        // draw the text centered
        canvas.drawText("Hello World", 16,112, textPaint);

        //Generate one texture pointer...
        gl.glGenTextures(1, textureIDs, 0);
        //...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)

        //Create Nearest Filtered Texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        //Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        //Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        //Clean up
        gl.glDisable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        bitmap.recycle();
    }
}
