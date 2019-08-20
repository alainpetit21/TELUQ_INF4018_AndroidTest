package com.bianisoft.engine._3d;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Tunnel3D extends Drawable3D {
    private static final int TYPE_OTHER= 0x03;

    private int nSize;

    public Tunnel3D(int p_nTextureFilename, int pnSize){
        super(p_nTextureFilename);
        setSubClassID(TYPE_OTHER);

        textureIDs = new int[1];   // Array for 1 texture-ID (NEW)
        nSize= pnSize;
    }

    public void loadRes(GL10 gl){
        super.loadRes(gl);

        float[] baseVertices = {
                -5.0f, -5.0f, 0.0f, -5.0f, -5.0f, 5.0f, -5.0f, 5.0f, 0.0f, -5.0f, 5.0f, 5.0f,
                5.0f, -5.0f, 0.0f, 5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 0.0f, 5.0f, 5.0f, 5.0f,
        };

        float[] baseTexture = {
                0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
        };

        short[] baseIndices = {
                0, 1, 2, 2, 1, 3,
                5, 4, 7, 4, 6, 7
        };


        float[] vertices= new float[24*nSize];
        float[] texture= new float[16*nSize];
        short[] indices= new short[12*nSize];

        for(int i= 0; i < nSize; ++i){
            for(int j= 0; j < 8; ++j){
                vertices[(i*24)+(j*3)+0]= baseVertices[(j*3)+0];
                vertices[(i*24)+(j*3)+1]= baseVertices[(j*3)+1];
                vertices[(i*24)+(j*3)+2]= (((baseVertices[(j*3)+2]/5f)-i)*5)+2f;
            }
        }

        for(int i= 0; i < nSize; ++i){
            for(int j= 0; j < 8; ++j){
                texture[(i*16)+(j*2)+0]= baseTexture[(j*2)+0];
                texture[(i*16)+(j*2)+1]= baseTexture[(j*2)+1];
            }
        }

        for(int i= 0; i < nSize; ++i){
            for(int j= 0; j < 12; ++j){
                indices[(i*12)+j]= (short)(baseIndices[j]+(i*8));
            }
        }

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

        setPosZ(nSize*5);
    }

    public void draw(GL10 gl){
        if(!isShown() || !isLoaded())
            return;

        if((m_bufVertices == null) || (m_bufIndices == null) || (m_bufUV == null))
            return;

        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);

        gl.glPushMatrix();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glTranslatef(getPosX(), -getPosY(), -getPosZ());
        gl.glScalef(m_nZoom, m_nZoom, m_nZoom);
        gl.glScalef(m_nScaleX, m_nScaleY, m_nScaleZ);
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY()+180, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ(), 0.0f, 0.0f, 1.0f);
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
