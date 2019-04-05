package com.bianisoft.engine.manager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

import com.bianisoft.engine.App;
import com.bianisoft.engine.PhysObj;

public class MyGLRenderer implements Renderer {
    protected ShortBuffer m_bufVertices;
    protected ShortBuffer m_bufVertices2;
    private PointF surfaceSize;

    public MyGLRenderer(){
        surfaceSize = new PointF();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(.5f, .5f,0.5f,1f);
        gl.glClearDepthf(1f);

        App.g_theApp.m_objGL = gl;

        //Go through all object in app and reload the textures
        if(App.g_theApp.m_ctxCur != null)
            App.g_theApp.m_ctxCur.loadAllRes(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, (int) width, (int) height);

        float ratio = (float) width / height;

        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);  // apply the projection matrix
    }

    public void onDrawFrameDebug(GL10 gl) {

    }

    public void onDrawFrame(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);


        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        if(App.g_theApp.m_ctxCur != null)
            App.g_theApp.m_ctxCur.draw(gl);
    }
}