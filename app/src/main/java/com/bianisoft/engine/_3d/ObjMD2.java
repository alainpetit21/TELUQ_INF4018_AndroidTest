package com.bianisoft.engine._3d;

import android.content.Context;

import com.bianisoft.engine.App;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

public class ObjMD2 extends Object3D {
    private static final int TYPE_MD2= 0x01;

    private int	m_nResMesh;
    private int		m_nNbFrames;
    private int		m_nVertexFrameOffset;
    private int		m_nVertexFrameSize;
    private int		m_nNbTriangles;

    private float	m_fScalingFactor= 1.0f;


    public ObjMD2(int p_nModelName, int p_nTextureName){
        super(p_nTextureName);

        setSubClassID(TYPE_MD2);
        setMeshFileName(p_nModelName);
    }

    public ObjMD2(ObjMD2 p_objMD2){
        super((Object3D)p_objMD2);
        m_nResMesh= p_objMD2.m_nResMesh;
    }

    public void setMeshFileName(int p_nResMesh){
        m_nResMesh= p_nResMesh;
    }

    public void setScalingFactor(float p_fScalingFactor){
        m_fScalingFactor= p_fScalingFactor;
    }

    public void loadRes(GL10 gl) {
        super.loadRes(gl);

        loadModel(gl, App.g_theApp.m_objAndroidContext);
    }

    public void loadModel(GL10 gl, Context context) {
        super.load();


        try {
            DataInputStream disLE = (DataInputStream)context.getResources().openRawResource(nResTexture);

            disLE.markSupported();

            int	nMagic				= disLE.readInt();
            int	nVersion			= disLE.readInt();
            int	nSkinWidth			= disLE.readInt();
            int	nSkinHeight			= disLE.readInt();
            int	nFrameSize			= disLE.readInt();
            int	nNumSkins			= disLE.readInt();
            int	nNumVertices		= disLE.readInt();
            int	nNumTexCoords		= disLE.readInt();
            int	nNumTriangles		= disLE.readInt();
            int	nNumGlCommands		= disLE.readInt();
            int	nNumFrames			= disLE.readInt();
            int	nOffsetSkins		= disLE.readInt();
            int	nOffsetTexCoords	= disLE.readInt();
            int	nOffsetTriangles	= disLE.readInt();
            int	nOffsetFrames		= disLE.readInt();
            int	nOffsetGlCommands	= disLE.readInt();
            int	nOffsetEnd			= disLE.readInt();

            m_nNbFrames= nNumFrames;
            m_nVertexFrameSize= nNumVertices;
            m_nNbTriangles= nNumTriangles;

            //Read UV
            int nPos= 0;
            float uv[]= new float[nNumTexCoords * 2];

            disLE.reset();
            disLE.skipBytes(nOffsetTexCoords);

            for(int i= 0; i < nNumTexCoords; ++i){
                uv[nPos++]= ((float)disLE.readShort() / nSkinWidth) * 256;  //Yes yes our texture will be hardcoded to 256
                uv[nPos++]= ((float)disLE.readShort() / nSkinHeight) * 256;
            }

            //Read Coordonnes
            nPos= 0;
            float vertices[]= new float[nNumFrames*nNumVertices * 3];

            disLE.reset();
            disLE.skipBytes(nOffsetFrames);

            for(int i= 0, cptVertices= 0; i < nNumFrames; ++i){
                float[] scale= new float[3];
                float[] transl= new float[3];

                scale[0]= disLE.readFloat()*m_fScalingFactor;
                scale[1]= disLE.readFloat()*m_fScalingFactor;
                scale[2]= disLE.readFloat()*m_fScalingFactor;
                transl[0]= disLE.readFloat()*m_fScalingFactor;
                transl[1]= disLE.readFloat()*m_fScalingFactor;
                transl[2]= disLE.readFloat()*m_fScalingFactor;
                disLE.skip(16);

                for(int j= 0; j < nNumVertices; ++j){
                    vertices[nPos++]= (((float)disLE.readUnsignedByte()) * scale[0]) + transl[0];
                    vertices[nPos++]= (((float)disLE.readUnsignedByte()) * scale[1]) + transl[1];
                    vertices[nPos++]= (((float)disLE.readUnsignedByte()) * scale[2]) + transl[2];
                    disLE.skip(1);
                    ++cptVertices;
                }
            }

            //Read triangles
            nPos= 0;
            ByteBuffer bufUV= ByteBuffer.allocateDirect(nNumTriangles * 3 * 2 * 4);
            bufUV.order(ByteOrder.nativeOrder());
            m_bufUV= bufUV.asFloatBuffer();

            ByteBuffer bufVertices= ByteBuffer.allocateDirect(nNumTriangles * 3 * 3 * 4);
            bufVertices.order(ByteOrder.nativeOrder());
            m_bufVertices= bufVertices.asFloatBuffer();

            disLE.reset();
            disLE.skipBytes(nOffsetTriangles);
            for(int i= 0; i < nNumTriangles; ++i){

                short tri1= disLE.readShort();
                short tri2= disLE.readShort();
                short tri3= disLE.readShort();

                short uv1= disLE.readShort();
                short uv2= disLE.readShort();
                short uv3= disLE.readShort();

                m_bufVertices.put(vertices[(tri1*3)+0]);	m_bufVertices.put(vertices[(tri1*3)+1]);	m_bufVertices.put(vertices[(tri1*3)+2]);
                m_bufVertices.put(vertices[(tri2*3)+0]);	m_bufVertices.put(vertices[(tri2*3)+1]);	m_bufVertices.put(vertices[(tri2*3)+2]);
                m_bufVertices.put(vertices[(tri3*3)+0]);	m_bufVertices.put(vertices[(tri3*3)+1]);	m_bufVertices.put(vertices[(tri3*3)+2]);

                m_bufUV.put(uv[(uv1*2)+0]);	m_bufUV.put(uv[(uv1*2)+1]);
                m_bufUV.put(uv[(uv2*2)+0]);	m_bufUV.put(uv[(uv2*2)+1]);
                m_bufUV.put(uv[(uv3*2)+0]);	m_bufUV.put(uv[(uv3*2)+1]);
            }
            int totot= m_bufVertices.position();
            int totot2= m_bufUV.position();

            m_bufUV.position(0);
            m_bufVertices.position(0);

            disLE.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(GL10 gl){
        if(!isShown() || textureIDs == null)
            return;

        if((m_bufVertices == null) || (m_bufUV == null))
            return;

        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);

        gl.glPushMatrix();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glTranslatef(-getPosX(), getPosY(), -getPosZ());
        gl.glScalef(m_nZoom, m_nZoom, m_nZoom);
        gl.glRotatef(getAngleX(), 1.0f, 0.0f, 0.0f);
        gl.glRotatef(getAngleY(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(getAngleZ(), 0.0f, 0.0f, 1.0f);
        gl.glColor4f(m_colorFilterRed, m_colorFilterGreen, m_colorFilterBlue, m_colorFilterAlpha);

        //Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_bufVertices);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, m_bufUV);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, m_nNbTriangles*3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

        gl.glPopMatrix();
    }

    public String toString(){
        return "ObjMD2 @ " + (int)getPosX() + ";"+ (int)getPosY() + ";"+ (int)getPosZ() + ";";
    }
}
