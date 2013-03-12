package com.mtao.challengenfactorial;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import android.opengl.Matrix;

import android.util.Log;






public class NFactorialRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";

    private NumberBlock mBlock;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    Point size;

    // Declare as volatile because we are updating it from another thread
    public volatile float mAngle;

	public Bitmap numbers;
	int number_tex_id;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


		/*
		int[] tmp = new int[1];
		GLES20.glGenTextures(1, tmp,0);
		number_tex_id = tmp[0];
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, number_tex_id);
		GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
		GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
		GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, numbers, 0);
*/
        mBlock = new NumberBlock(1, new Vector2(0.5f,0.2f), .1f);
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, -1, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

        // Draw square
        //mSquare.draw(mMVPMatrix);
        mBlock.draw(mMVPMatrix);

        // Create a rotation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        
        Matrix.orthoM(mProjMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f);


    }
float ratio;
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

	public void sendLocation(Vector2 first) {
		// TODO Auto-generated method stub
		//Log.i("info", ratio + "" );
		float mInv[] = new float[16];
		Matrix.invertM(mInv, 0, mMVPMatrix, 0);
		float vec[] = {first.x,first.y,0,1};
		float resultVec[] = new float[4];
		Matrix.multiplyMV(resultVec, 0, mInv, 0, vec, 0);
		mBlock.pos = new Vector2(resultVec[0],resultVec[1]);
		//mBlock.pos.x /= ratio;
	}
}
