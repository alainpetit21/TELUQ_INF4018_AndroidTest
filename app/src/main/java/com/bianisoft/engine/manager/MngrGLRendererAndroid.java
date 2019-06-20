package com.bianisoft.engine.manager;

import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.bianisoft.engine.FrontendApp;
import com.bianisoft.engine.PhysObj;

public class MngrGLRendererAndroid implements Renderer {
    protected ShortBuffer m_bufVertices;
    protected ShortBuffer m_bufVertices2;
    private PointF surfaceSize;

    public MngrGLRendererAndroid(){
        surfaceSize = new PointF();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(.5f, .5f,0.5f,1f);
        gl.glClearDepthf(1f);

        FrontendApp.setGL(gl);

        //Go through all object in app and reload the textures
        if(FrontendApp.getFrontendApp().getCurScreen() != null)
            FrontendApp.getFrontendApp().getCurScreen().loadAllRes(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, (int) width, (int) height);

        float ratio = (float) width / height;

        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-1, 1, -1, 1, 1, 100);  // apply the projection matrix

        //Go through all object in app and reload the textures
        if(FrontendApp.getFrontendApp().getCurScreen() != null)
            FrontendApp.getFrontendApp().getCurScreen().loadAllRes(gl);
    }

    public void onDrawFrameDebug(GL10 gl) {

    }

    public void onDrawFrame(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        //By default for now the "Eye" will be "attached" to the last item in the context, minus 15 in Z
        float lookAtX = 0;
        float lookAtY = 0;
        float lookAtZ = 10;

        if(FrontendApp.getFrontendApp().getCurScreen() != null){
            PhysObj objLockedOn= FrontendApp.getFrontendApp().getCurScreen().objCam;

            if(objLockedOn != null) {
                lookAtX = objLockedOn.getPosX();
                lookAtY = objLockedOn.getPosY();
                lookAtZ = objLockedOn.getPosZ();
            }
        }

        GLU.gluLookAt(gl, lookAtX, lookAtY, lookAtZ+15, lookAtX, lookAtY, lookAtZ, 0f, -1.0f, 0.0f);


        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        if(FrontendApp.getFrontendApp().getCurScreen() != null)
            FrontendApp.getFrontendApp().getCurScreen().draw(gl);
    }

    public void onDestroy() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

}