package com.mtao.challengenfactorial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.ImageView;
import android.util.Log;
public class NFactorialGLSurfaceView extends GLSurfaceView {

	public NFactorialGLSurfaceView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		// TODO Auto-generated constructor stub
		//Bitmap digits = ((TextureView)findViewById(R.drawable.digits)).getBitmap();
		super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
		setRenderer(renderer = new NFactorialRenderer());
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		Vector2 p = new Vector2(e.getX(),e.getY());
		switch(e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mouseDown = p;
			break;
		case MotionEvent.ACTION_UP:
			evaluateSlide(mouseDown,p);
			break;
		case MotionEvent.ACTION_MOVE:
			if(p.y < 50)
				selectOperator(p);
		}
		//Log.i("info","Mouse poll?" +p.x + " " + p.y);
		return true;
	}

	void evaluateSlide(Vector2 first, Vector2 second) {
		Log.i("info",first.x + " " + first.y + " -> " + second.x + " " + second.y);
	}
	

	void selectOperator(Vector2 p) {
		int pos = (int)(p.x * 4 /(getWidth()));
		Log.i("info",""+pos);
		switch(pos) {
		case 0:
			optype = OperatorType.ADDITION;
			break;
		case 1:
			optype = OperatorType.SUBTRACTION;
			break;
		case 2:
			optype = OperatorType.MULTIPLICATION;
			break;
		case 3:
			optype = OperatorType.DIVISION;
			break;
		}

		
		
	}
	private NFactorialRenderer renderer;
	private Vector2 mouseDown = new Vector2(0,0);
	private OperatorType optype = OperatorType.VALUE;
}
