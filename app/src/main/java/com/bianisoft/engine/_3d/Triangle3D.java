package com.bianisoft.engine._3d;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Triangle3D extends Object3D {
    private static final int TYPE_OTHER= 0x03;


    public Triangle3D(int p_nTextureFilename){
        super(p_nTextureFilename);
        setSubClassID(TYPE_OTHER);

        textureIDs = new int[1];   // Array for 1 texture-ID (NEW)
    }

    public void load(){
        super.load();

        float vertices[]= {
                -1.0f, 1.0f,  0.0f,	    0.0f, -1.0f,  0.0f,   	1.0f,  1.0f,  0.0f,
        };

        float texture[]= {
                0.0f, 1.0f,		0.5f, 0.0f,		1.0f, 1.0f,
        };

        short indices[]= {
                0,   1,  2,
        };

        ByteBuffer vbb= ByteBuffer.allocateDirect((m_nNbVertices= vertices.length) * 4);
        vbb.order(ByteOrder.nativeOrder());
        m_bufVertices= vbb.asFloatBuffer();
        m_bufVertices.put(vertices);
        m_bufVertices.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect((m_nNbIndices= indices.length) * 2);
        ibb.order(ByteOrder.nativeOrder());
        m_bufIndices= ibb.asShortBuffer();
        m_bufIndices.put(indices);
        m_bufIndices.position(0);

        ByteBuffer uvb= ByteBuffer.allocateDirect(m_nNbVertices * 4);
        uvb.order(ByteOrder.nativeOrder());
        m_bufUV= uvb.asFloatBuffer();
        m_bufUV.put(texture);
        m_bufUV.position(0);
    }


    public void draw(GL10 gl){
        if(!isShown() || textureIDs == null)
            return;

        if((m_bufVertices == null) || (m_bufIndices == null) || (m_bufUV == null))
            return;

        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);

        gl.glPushMatrix();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glTranslatef(getPosX(), getPosY(), -getPosZ());
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY()+180, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ(), 0.0f, 0.0f, 1.0f);
        gl.glScalef(m_nZoom, m_nZoom, m_nZoom);
        gl.glScalef(m_nScaleX, m_nScaleY, m_nScaleZ);
        gl.glColor4f(m_colorFilterRed, m_colorFilterGreen, m_colorFilterBlue, m_colorFilterAlpha);

        gl.glFrontFace(GL10.GL_CW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        //Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_bufVertices);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, m_bufUV);

        gl.glDrawElements(GL10.GL_TRIANGLES, m_nNbIndices, GL10.GL_UNSIGNED_SHORT, m_bufIndices);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glPopMatrix();
    }
}
