package com.mtao.challengenfactorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SimpleView extends View {
	private GameMode mMode;
	private final Paint mPaint;
	private Drawable mIcon;
	private Rect mCardBounds[];
	private Rect mOpBounds[];
	private float mPosX;
	private float mPosY;
	private int mWidth;
	private int mHeight;
	private int mValues[];
	private Vector2 mCenterPos;
	private Vector2 mPos[];
	private Vector2 mDragStart;
	private int mStartInd;
	private int mSelectedLHS;
	private int mSelectedRHS;
	private Card mCard;

	private float mLastTouchX;
	private float mLastTouchY;

	private CardFactory mCardFactory = new CardFactory(4);

	public SimpleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mIcon = context.getResources().getDrawable(R.drawable.ic_launcher);
		mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextSize(30);
		mPaint.setStyle(Paint.Style.STROKE);


		mMode = GameMode.ValuePairSelection;
		mCenterPos = new Vector2(0,0);
		createNewCard();

	}
	@Override
	protected
	void onSizeChanged(int w, int h, int ow, int oh){	
		mWidth = w;
		mHeight = h;
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		mCenterPos = new Vector2(mWidth/2.0f-mIcon.getIntrinsicWidth()/2,mHeight/2.0f-mIcon.getIntrinsicHeight()/2);
		
		setPositions(mValues.length);
	}

	private void resetCard() {
		mValues = mCard.values().clone();
		setPositions(4);
	}
	private void createNewCard() {

		mCard = mCardFactory.genCard();
		resetCard();

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
		if(mMode == GameMode.Victory) {
			return;
		}
		if(mDragStart != null) {
			final float oldStrokeWidth = mPaint.getStrokeWidth();
			mPaint.setStrokeWidth(8);
			canvas.drawLine(mDragStart.x, mDragStart.y, mPosX, mPosY, mPaint);
			mPaint.setStrokeWidth(oldStrokeWidth);
		}
		//canvas.save();
		canvas.translate(mCenterPos.x, mCenterPos.y*.8f);
		mIcon.draw(canvas);
		canvas.restore();

		for(int i=0; i < mValues.length; ++i) {
			canvas.save();
			canvas.translate(mCardBounds[i].left, mCardBounds[i].top);
			mIcon.draw(canvas);
			canvas.drawText(mValues[i]+"", 30, 50, mPaint);
			canvas.restore();
		}
		for(int i=0; i < 4; ++i) {
			canvas.save();
			canvas.translate(mOpBounds[i].left, mOpBounds[i].top);
			mIcon.draw(canvas);
			String opText = "";
			switch(i) {
			case 0: opText = "+"; break;
			case 1: opText = "-"; break;
			case 2: opText = "*"; break;
			case 3: opText = "/"; break;
			}
			canvas.drawText(opText, 30, 50, mPaint);
			canvas.restore();
		}


	}

	private void setPositions(int numPos) {
		int w=mIcon.getIntrinsicWidth();
		int h=mIcon.getIntrinsicHeight();

		switch(numPos) {
		case 1:
			mPos = new Vector2[1];
			mPos[0] = new Vector2(0,0);
			break;
		case 2:
			mPos = new Vector2[2];
			mPos[0] = new Vector2(-2*w,0);
			mPos[1] = new Vector2(+2*w,0);
			break;
		case 3:
			mPos = new Vector2[3];
			mPos[0] = new Vector2(-2*w,0);
			mPos[1] = new Vector2(2*w,0);
			mPos[2] = new Vector2(0,+2*h);
			break;
		case 4:
			mPos = new Vector2[4];
			mPos[0] = new Vector2(-2*w,0);
			mPos[1] = new Vector2(2*w,0);
			mPos[2] = new Vector2(0,+2*h);
			mPos[3] = new Vector2(0,-2*h);
			break;
		}


		mCardBounds = new Rect[numPos];

		for(int i=0; i < numPos; ++i) {
			final int xp = (int)(mCenterPos.x + mPos[i].x);
			final int yp = (int)(mCenterPos.y*.8 + mPos[i].y);

			//mIcons[i] = mIcon.getConstantState().newDrawable();
			mCardBounds[i] = new Rect(xp,yp, xp+mIcon.getIntrinsicWidth(),yp+ mIcon.getIntrinsicHeight());
			//Log.e("info", "Bounds created!");
		}
		mOpBounds = new Rect[4];
		for(int i=0; i < 4; ++i) {
			final int xp = (int)(mCenterPos.x + (i-1.5) * mIcon.getIntrinsicWidth() * 1.1);
			final int yp = (int)(mCenterPos.y*5/3);


			mOpBounds[i] = new Rect(xp,yp, xp+mIcon.getIntrinsicWidth(),yp+ mIcon.getIntrinsicHeight());
			
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();

		switch(mMode) {

		case ValuePairSelection: {
			switch (action) {
			case MotionEvent.ACTION_DOWN: {

				mDragStart = new Vector2(x,y);
				mPosX = x;
				mPosY = y;

				for(int i=0; i < mPos.length; ++i) {
					if(mCardBounds[i].contains((int)x, (int)y)) {
						mStartInd = i;
						mSelectedLHS = i;
					}
				}

				// Remember where we started
				mLastTouchX = x;
				mLastTouchY = y;
				invalidate();
				break;
			}
			case MotionEvent.ACTION_UP: {
				mDragStart = null;
				for(int i=0; i < mPos.length; ++i) {
					if(i != mStartInd && mCardBounds[i].contains((int)x, (int)y)) {
						//evalOp(mStartInd,i);
						mMode = GameMode.OperatorSelection;
						mSelectedRHS = i;
						Log.i("info", mValues[mStartInd] + "->" + mValues[i]);
						break;
					}
				}
				invalidate();
				break;
			}

			case MotionEvent.ACTION_MOVE: {

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
			break;
		}
		
		case OperatorSelection: {
			switch (action) {
			case MotionEvent.ACTION_DOWN: {


				for(int i=0; i < mPos.length; ++i) {
					
					if(mOpBounds[i].contains((int)x, (int)y)) {
						System.out.println(i);
						switch(i) {
						case 0:
							evalOp(OperatorType.ADDITION);
							break;
						case 1:
							evalOp(OperatorType.SUBTRACTION);
							break;
						case 2:
							evalOp(OperatorType.MULTIPLICATION);
							break;
						case 3:
							evalOp(OperatorType.DIVISION);
							break;
						default:
							break;
						}
					}
				}

				// Remember where we started
				mLastTouchX = x;
				mLastTouchY = y;
				invalidate();
				break;
			}
			}

			break;
		}
		default:
			createNewCard();
			mMode = GameMode.ValuePairSelection;

			break;
		}

		return true;
	}

	void evalOp(OperatorType type) {
		mMode = GameMode.ValuePairSelection;
		Node n = new Node(type, new Node(0), new Node(1));
		Card c = new Card(2);
		c.set(0,mValues[mSelectedLHS]);
		c.set(1,mValues[mSelectedRHS]);
		int newval = n.eval(c);
		Log.i("info",mValues[mSelectedLHS] + " " + mValues[mSelectedRHS] + " " + newval);

		Log.i("size", mValues.length+"");
		if(mValues.length == 2) {
			if (newval == 24) {
				mMode = GameMode.Victory;
				invalidate();
			} else {
				resetCard();
				
			}
		} else {
			
			int newValues[] = new int[mValues.length-1];
			for(int i=0, j=0; i < mValues.length; ++i) {
				if(j == newValues.length-1) {
					break;
				} else if(i != mSelectedLHS && i != mSelectedRHS){
					newValues[j++] = mValues[i];
					Log.i("update", j + "+" + i + ")" + mValues[i]);
				} else {
					Log.i("update", j + " " + i);
				}

			}
			newValues[newValues.length-1] = newval;
			mValues = newValues;
			setPositions(newValues.length);
			
		}

	}


}
