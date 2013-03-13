package com.mtao.challengenfactorial;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;

/*
public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

}
*/
public class PlayActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new NFactorialGLSurfaceView(this);
//        setContentView(mGLView);
        setContentView(new SimpleView(getApplicationContext()));
    }
}