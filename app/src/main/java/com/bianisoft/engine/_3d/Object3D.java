/* This file is part of the Bianisoft game library.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *----------------------------------------------------------------------
 * Copyright (C) Alain Petit - alainpetit21@hotmail.com
 *
 * 18/12/10			0.1 First beta initial Version.
 * 12/09/11			0.1.2 Moved everything to a com.bianisoft
 *
 *-----------------------------------------------------------------------
 */
package com.bianisoft.engine._3d;


//Standard Java library imports
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//Bianisoft library imports
//import com.bianisoft.engine.resmng.ImageCache;
//import com.bianisoft.engine.resmng.Texture;
import com.bianisoft.engine.App;
import com.bianisoft.engine.Drawable;

import javax.microedition.khronos.opengles.GL10;


public class Object3D extends Drawable{
    protected int nResTexture;
    protected int[] textureIDs;

    //Native Vertex buffer
    protected FloatBuffer m_bufVertices;
    protected ShortBuffer m_bufIndices;
    protected FloatBuffer m_bufUV;

    protected int 	m_nNbVertices;
    protected int 	m_nNbIndices;


    public Object3D()	{super(IDCLASS_Object3D);}
    public Object3D(int p_nResIDTexture){
        super(IDCLASS_Object3D);

        nResTexture = p_nResIDTexture;
    }

    public Object3D(Object3D p_obj3D){
        this();

        nResTexture = p_obj3D.nResTexture;
        textureIDs		= p_obj3D.textureIDs;

        //Read the ref into a java array
        float[] arVertices= new float[p_obj3D.m_bufVertices.capacity()];
        p_obj3D.m_bufVertices.get(arVertices);

        //Create the copy native array
        ByteBuffer vbb= ByteBuffer.allocateDirect(p_obj3D.m_bufVertices.capacity() * 4);
        vbb.order(ByteOrder.nativeOrder());

        //Copy into the native array
        m_bufVertices= vbb.asFloatBuffer();
        m_bufVertices.put(arVertices);
        m_bufVertices.position(0);
    }

    public void loadRes(GL10 gl) {
        loadTexture(gl, App.g_theApp.m_objAndroidContext);
    }

    // Load an image into GL texture
    public void loadTexture(GL10 gl, Context context) {
        if (textureIDs == null)
            return;

        gl.glGenTextures(1, textureIDs, 0); // Generate texture-ID array

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);   // Bind to texture ID
        // Set up texture filters
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Construct an input stream to texture image "res\drawable\nehe.png"
        InputStream istream = context.getResources().openRawResource(nResTexture);
        Bitmap bitmap;
        try {
            // Read and decode input as bitmap
            bitmap = BitmapFactory.decodeStream(istream);
        } finally {
            try {
                istream.close();
            } catch(IOException e) { }
        }

        // Build Texture from loaded bitmap for the currently-bind texture ID
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    public boolean	isLoaded()		{return textureIDs != null;}


    public void draw(GL10 gl){
        if(!isShown() || textureIDs == null)
            return;

        if((m_bufVertices == null) || (m_bufIndices == null) || (m_bufUV == null))
            return;

        gl.glPushMatrix();

        gl.glTranslatef(getPosX(), getPosY(), getPosZ());
        gl.glScalef(m_nZoom, m_nZoom, m_nZoom);
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ(), 0.0f, 0.0f, 1.0f);
        gl.glColor4f(m_colorFilterRed, m_colorFilterGreen, m_colorFilterBlue, m_colorFilterAlpha);

        //Point to our buffers
        gl.glFrontFace(GL10.GL_CW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_bufVertices);
        gl.glDrawElements(GL10.GL_TRIANGLES, m_nNbIndices, GL10.GL_UNSIGNED_SHORT, m_bufIndices);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glPopMatrix();
    }

    public String toString(){
        return "Object3D @ " + (int)getPosX() + ";"+ (int)getPosY() + ";"+ (int)getPosZ() + ";";
    }
}
