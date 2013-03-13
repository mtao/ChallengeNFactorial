package com.mtao.challengenfactorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SimpleView extends View {
	private String mText;
	private final Paint mPaint;
	private Drawable mIcon;
    private float mPosX;
    private float mPosY;
    
    private float mLastTouchX;
    private float mLastTouchY;
    
    private CardFactory mCardFactory = new CardFactory(4);

	public SimpleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		mText = "Bollocks!";
        mIcon = context.getResources().getDrawable(R.drawable.ic_launcher);
        mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());
        
		mPaint = new Paint();
		mPaint.setTextSize(30);
		
	}

	public SimpleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SimpleView(Context context) {
		this(context, null, 0);
	}

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.save();
        canvas.translate(mPosX, mPosY);
        mIcon.draw(canvas);
        canvas.restore();
        
        canvas.drawText(mText, mLastTouchX, mLastTouchY, mPaint);
    }

	@Override
    public boolean onTouchEvent(MotionEvent ev) {
	    final int action = ev.getAction();
	    switch (action) {
		    case MotionEvent.ACTION_DOWN: {
		        final float x = ev.getX();
		        final float y = ev.getY();
		        
		        // Remember where we started
		        mLastTouchX = x;
		        mLastTouchY = y;
		        Card c = mCardFactory.genCard();
		        mText = c.toString();
//		        CardSolver solver = new CardSolver(c);
//		        mText = Equation.toString(solver.solutions.get(0),c);
		        
		        // Invalidate to request a redraw
		        invalidate();
		        break;
		    }
		        
		    case MotionEvent.ACTION_MOVE: {
		        final float x = ev.getX();
		        final float y = ev.getY();
		        
		        // Calculate the distance moved
		        final float dx = x - mLastTouchX;
		        final float dy = y - mLastTouchY;
		        
		        // Move the object
		        mPosX += dx;
		        mPosY += dy;
		        
		        // Remember this touch position for the next move event
		        mLastTouchX = x;
		        mLastTouchY = y;
		        invalidate();
		        break;
		    }
	    }
    
	    return true;
	}

}
