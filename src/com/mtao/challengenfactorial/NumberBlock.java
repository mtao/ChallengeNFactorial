package com.mtao.challengenfactorial;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class NumberBlock {

	NumberBlock(int value, Vector2 pos, float sidelength) {
		this.value = value;
		this.sidelength = sidelength;
		this.pos = pos;
		digits = 0;
		for(;value > 0;++digits,value/=10);

		mProgram = GLES20.glCreateProgram();
		int vshader = NFactorialRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fshader = NFactorialRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		GLES20.glAttachShader(mProgram, vshader);
		GLES20.glAttachShader(mProgram, fshader);
		GLES20.glLinkProgram(mProgram);

		ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(squareCoords);
		vertexBuffer.position(0);
		
		ByteBuffer dlb = ByteBuffer.allocateDirect(
				drawOrder.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);


	}
	void draw() {
		GLES20.glUseProgram(mProgram);
		int positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
		GLES20.glEnableVertexAttribArray(positionHandle);
		GLES20.glUniform2f(GLES20.glGetUniformLocation(mProgram, "center"), pos.x, pos.y);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(mProgram, "sidelength"), sidelength);

		GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
		
		
		int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

	    // Set color for drawing the triangle
	    GLES20.glUniform4fv(mColorHandle, 1, new float[]{1,0,0,1}, 0);

	    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

	    // Disable vertex array
	    GLES20.glDisableVertexAttribArray(positionHandle);
		
	}


	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;
	int value;
	float sidelength;
	Vector2 pos;
	int digits;
	private final int mProgram;
	static final int COORDS_PER_VERTEX = 2;
	private static final int vertexStride = COORDS_PER_VERTEX * 4;
	 
	static float squareCoords[] = { -0.5f,  0.5f,   // top left
		-0.5f, -0.5f,   // bottom left
		0.5f, -0.5f,   // bottom right
		0.5f,  0.5f }; // top right
	private static final int vertexCount = squareCoords.length;
	private static short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
	private static final String vertexShaderCode =
			"attribute vec2 vPosition;" +
					"uniform vec2 center" +
					"uniform float sidelength" +
					"void main() {" +
					"  gl_Position = vec4(center + sidelength * vPosition,0,0);" +
					"}";

	private static final String fragmentShaderCode =
			"precision mediump float;" +
					"uniform vec4 vColor;" +
					"void main() {" +
					"  gl_FragColor = vColor;" +
					"}";
}
