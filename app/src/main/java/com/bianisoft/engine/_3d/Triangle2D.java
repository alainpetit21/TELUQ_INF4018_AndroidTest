package com.bianisoft.engine._3d;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Triangle2D extends Object3D {
    private static final int TYPE_OTHER = 0x03;

    private int m_nNbColors;
    protected FloatBuffer m_bufColors;


    public Triangle2D(int p_stTextureFilename) {
        super(p_stTextureFilename);
        setSubClassID(TYPE_OTHER);

    }

    public void load() {
        super.load();

        float vertices[] = {
                0f, 1f,
                1f, -1f,
                -1f, -1f,
        };

        short indices[] = {
                0, 1, 2
        };

        float rgbaVal[] = {
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 0f, 1f, 1f
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect((m_nNbVertices = vertices.length) * 4);
        vbb.order(ByteOrder.nativeOrder());
        m_bufVertices = vbb.asFloatBuffer();
        m_bufVertices.put(vertices);
        m_bufVertices.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect((m_nNbIndices= indices.length) * 2);
        ibb.order(ByteOrder.nativeOrder());
        m_bufIndices= ibb.asShortBuffer();
        m_bufIndices.put(indices);
        m_bufIndices.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect((m_nNbColors= rgbaVal.length) * 4);
        cbb.order(ByteOrder.nativeOrder());
        m_bufColors= cbb.asFloatBuffer();
        m_bufColors.put(rgbaVal);
        m_bufColors.position(0);
    }

    public void manage(float p_nMoveRatio) {
        super.manage(p_nMoveRatio);
    }

    public void draw(GL10 gl) {
        if (!isShown() /*|| m_texImage == null*/)
            return;

        gl.glPushMatrix();

        gl.glTranslatef(getPosX(), getPosY(), getPosZ());
        gl.glScalef(m_nZoom, m_nZoom, m_nZoom);
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ(), 0.0f, 0.0f, 1.0f);
        gl.glColor4f(m_colorFilterRed, m_colorFilterGreen, m_colorFilterBlue, m_colorFilterAlpha);

        gl.glFrontFace(GL10.GL_CW);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, m_bufVertices);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, m_bufColors);
        gl.glDrawElements(GL10.GL_TRIANGLES, m_nNbIndices, GL10.GL_UNSIGNED_SHORT, m_bufIndices);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        gl.glPopMatrix();
    }
}