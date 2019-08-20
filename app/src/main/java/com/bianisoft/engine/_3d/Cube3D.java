package com.bianisoft.engine._3d;


//Standard Java library imports

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;


public class Cube3D extends Drawable3D {
    private static final int TYPE_OTHER= 0x03;

    float[] vertices3 = {
            -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f,
    };

    float[] texCoords3 = {
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
    };

    short[] indices3 = {
            0, 1, 3, 0, 3, 2,
            4, 5, 7, 4, 7, 6,
            8, 9, 11, 8, 11, 10,
            12, 13, 15, 12, 15, 14,
            16, 17, 19, 16, 19, 18,
            20, 21, 23, 20, 23, 22,
    };

    public Cube3D(int p_nTextureFilename){
        super(p_nTextureFilename);
        setSubClassID(TYPE_OTHER);
    }

    @Override
    public void loadRes(GL10 gl) {
        super.loadRes(gl);

        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb3 = ByteBuffer.allocateDirect(vertices3.length * 4);
        vbb3.order(ByteOrder.nativeOrder()); // Use native byte order
        m_bufVertices = vbb3.asFloatBuffer(); // Convert from byte to float
        m_bufVertices.put(vertices3);         // Copy data into buffer
        m_bufVertices.position(0);           // Rewind

        // Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
        ByteBuffer tbb3 = ByteBuffer.allocateDirect(texCoords3.length * 4);
        tbb3.order(ByteOrder.nativeOrder());
        m_bufUV = tbb3.asFloatBuffer();
        m_bufUV.put(texCoords3);
        m_bufUV.position(0);


        ByteBuffer ibb3 = ByteBuffer.allocateDirect((indices3.length) * 2);
        ibb3.order(ByteOrder.nativeOrder());
        m_bufIndices= ibb3.asShortBuffer();
        m_bufIndices.put(indices3);
        m_bufIndices.position(0);
    }

    public void manage(float p_nMoveRatio){
        super.manage(p_nMoveRatio);
    }

      public void draw(GL10 gl){
        if(!isShown() || !isLoaded())
            return;

        if((m_bufVertices == null) || (m_bufIndices == null) || (m_bufUV == null))
            return;

        gl.glPushMatrix();

        gl.glTranslatef(getPosX(), -getPosY(), -(getPosZ() ));
        gl.glScalef(m_nZoom, m_nZoom, m_nZoom);
        gl.glScalef(m_nScaleX, m_nScaleY, m_nScaleZ);
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ(), 0.0f, 0.0f, 1.0f);
        gl.glColor4f(m_colorFilterRed, m_colorFilterGreen, m_colorFilterBlue, m_colorFilterAlpha);

        //Point to our buffers
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_bufVertices);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, m_bufUV);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices3.length, GL10.GL_UNSIGNED_SHORT, m_bufIndices);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Disable texture-coords-array (NEW)

        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glDisable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glPopMatrix();
    }
}