package com.lil.MadWorld;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import com.lil.MadWorld.Models.MadWorld;
import com.lil.MadWorld.Models.MySprite;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer
{
    private final Context mActivityContext;

    //Matrix Initializations
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];

    //Declare as volatile because we are updating it from another thread
    public volatile float mAngle;
    public volatile float worldX = 0.0f;

    //private Triangle triangle;
    private MySprite sprite;

    long mLastTime;
    int mProgram;

    // Our screen resolution
    float   mScreenWidth = 1280;
    float   mScreenHeight = 768;

    public GLRenderer(final Context activityContext)
    {
        mActivityContext = activityContext;
    }


    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        //Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        //Initialize Shapes
        //triangle = new Triangle();
        sprite = new MySprite(mActivityContext);
    }

    public void onDrawFrame(GL10 unused)
    {
        // Get the current time
        long now = System.currentTimeMillis();

        // We should make sure we are valid and sane
        if (mLastTime > now) return;

        // Get the amount of time the last frame took.
        long elapsed = now - mLastTime;
        Log.w("elapsed = ", String.valueOf(elapsed));
        if (elapsed >= 35) {
            // Update our example
            update();
            // Save the current time to see how long it took <img src="http://androidblog.reindustries.com/wp-includes/images/smilies/icon_smile.gif" alt=":)" class="wp-smiley"> .
            mLastTime = now;
        }
        // Render our example
        render();


    }


    private void update() {
        worldX -= 0.005f;

    }

    private void render() {
        //Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setIdentityM(mMVPMatrix,0); // получаем единичную матрицу ( сбрасываем все изменения )
        Matrix.translateM(mMVPMatrix,0,worldX,0,0); // переносим координаты

        //Set the camera position (View Matrix)

//        Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
//
//        Matrix.translateM(mVMatrix, 0, worldX, 0.0f, 0.0f);
//
//        //Calculate the projection and view transformation
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
//
//
//        //Create a rotation transformation for the triangle
//        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);
//
//        //Combine the rotation matrix with the projection and camera view
//        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);


        //Draw Shape
        //triangle.Draw(mMVPMatrix);
        sprite.Draw(mMVPMatrix);
    }


    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        // We need to know the current width and height.
        mScreenWidth = width;
        mScreenHeight = height;

        // Устанавливаем OpenGL окно просмотра того же размера что и поверхность экрана.
        GLES20.glViewport(0, 0, width, height);


        // Создаем новую матрицу проекции. Высота остается та же,
        // а ширина будет изменяться в соответствии с соотношением сторон.
        final float ratio = 1.0f;//(float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 3.0f;
        final float far = 7.0f;

        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public static int loadShader(int type, String shaderCode)
    {
        //Create a Vertex Shader Type Or a Fragment Shader Type (GLES20.GL_VERTEX_SHADER OR GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        //Add The Source Code and Compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
