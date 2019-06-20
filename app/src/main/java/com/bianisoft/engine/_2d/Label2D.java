package com.bianisoft.engine._2d;

import android.content.Context;

import com.bianisoft.engine.Drawable;
import com.bianisoft.engine.FrontendApp;
import com.bianisoft.engine.manager.MngrFont.GLText;


import javax.microedition.khronos.opengles.GL10;

public class Label2D extends Drawable {
    private static GLText objGLText;                             // A GLText Instance

    private String strTextToDisplay;


    public Label2D(){

    }

    public void setText(String pText){
        strTextToDisplay= pText;
    }

    public void loadRes(GL10 gl) {
        loadTypeface(gl, FrontendApp.getContext());
    }

    public void loadTypeface(GL10 gl, Context context){
        if(objGLText == null){
            // Create the GLText
            objGLText = new GLText( gl, context.getAssets() );

            // Load the font from file (set size + padding), creates the texture
            // NOTE: after a successful call to this the font is ready for rendering!
            objGLText.load( "Roboto-Regular.ttf", 14, 2, 2 );  // Create Font (Height: 14 Pixels / X+Y Padding 2 Pixels)
        }
    }

    public void load(){
    }

    public void draw(GL10 gl) {
        gl.glPushMatrix();

        gl.glTranslatef(getPosX(), getPosY(), getPosZ());
        gl.glScalef(m_nZoom/10, m_nZoom/10, m_nZoom/10);
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ()+180, 0.0f, 0.0f, 1.0f);

        gl.glEnable( GL10.GL_TEXTURE_2D );              // Enable Texture Mapping
        gl.glEnable( GL10.GL_BLEND );                   // Enable Alpha Blend
        gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );  // Set Alpha Blend Function

        objGLText.begin( 0.0f, 0.0f, 0.0f, 1.0f );         // Begin Text Rendering (Set Color WHITE)
        objGLText.draw( strTextToDisplay, 0, 0 );          // Draw Test String
        objGLText.end();                                   // End Text Rendering

        gl.glPopMatrix();
        gl.glDisable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glDisable(GL10.GL_BLEND);
    }
}
