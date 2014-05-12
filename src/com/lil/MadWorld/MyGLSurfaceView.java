package com.lil.MadWorld;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.lil.MadWorld.Models.MySprite;


class MyGLSurfaceView extends GLSurfaceView {
    GLRenderer glRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
        setEGLContextClientVersion(2);
        glRenderer = new GLRenderer(context);
        setRenderer(glRenderer);
    }

//    public boolean onTouchEvent(final MotionEvent event) {
//        queueEvent(new Runnable(){
//            public void run() {
//                glRenderer.setColor(event.getX() / getWidth(),
//                        event.getY() / getHeight(), 1.0f);
//            }});
//        return true;
//    }


}

