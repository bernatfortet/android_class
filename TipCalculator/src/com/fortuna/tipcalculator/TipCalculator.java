package com.fortuna.tipcalculator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class TipCalculator extends Activity {

	private static float DRAG_FACTOR = 0.05f;
	
	

	private TextView tvCalculatedTip;
	private TextView tvTotalPrice;
	

	private TextView tvCalculatedTipLabel;

	private TextView tvTip10;
	private TextView tvTip15;
	private TextView tvTip20;
	
	private TextView currentTip;
	
	private float tipPercent = 0.1f;
	
	
	
	private float lastTotalPrice = 1000f;
	private float currentTotalPrice = 1000f;
	private float xOnDown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);
		
		tvTotalPrice = (TextView) findViewById(R.id.totalPrice);
		tvCalculatedTip = (TextView) findViewById(R.id.calculatedTip);
		

		tvCalculatedTipLabel = (TextView) findViewById(R.id.calculatedTipLabel);

		float defaultPrice = 50f;
		setCalculatedTotalPrice( defaultPrice );
		setCalculatedTip( calculateTip(defaultPrice) );
		
		
		

		tvTip10 = (TextView) findViewById(R.id.tip10);
		tvTip15 = (TextView) findViewById(R.id.tip15);
		tvTip20 = (TextView) findViewById(R.id.tip20);
		
		currentTip = tvTip15;
		
		setNewTip(tvTip15);
		setListeners();
		setCustomFont();
	}
	

	private void setListeners() {
		tvTotalPrice.setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		    	touchHandler(event.getAction(), event);
		        return true;
		    }
		});
	}
	
	public void onClick(View v){
		setNewTip( (TextView) v);
	}
	

	private void touchHandler( int action, MotionEvent event ){
    	switch( action ){
	    	case MotionEvent.ACTION_DOWN:
	    		xOnDown = event.getX();
	    		break;
	    	case MotionEvent.ACTION_MOVE:
	    		float price = calculateTotalPrice(event.getX());
	    		setCalculatedTotalPrice( price );
	    		setCalculatedTip( calculateTip(price) );
	    		
	    		break;
	    	case MotionEvent.ACTION_UP:
	    		lastTotalPrice = currentTotalPrice;
	    		break;
		}
	}
	
	private float calculateTip(float price) {
		return tipPercent * price;
	}

	private float calculateTotalPrice(float touchX) {
		float relativeX = touchX - xOnDown;
		currentTotalPrice = lastTotalPrice + relativeX;
		
		Log.v( "asdf", ""+currentTotalPrice);
		
		if( currentTotalPrice <= 0 )
			currentTotalPrice = 0;
		
		return currentTotalPrice * DRAG_FACTOR;
	}
	

	private void setNewTip(TextView view) {
		currentTip.setTextColor(Color.parseColor("#626262"));
		view.setTextColor(Color.parseColor("#FFFFFF"));
		currentTip = view;
		
		if( view.getId() == tvTip10.getId()) tipPercent = 0.1f;
		else if( view.getId() == tvTip15.getId()) tipPercent = 0.15f;
		else if( view.getId() == tvTip20.getId()) tipPercent = 0.2f;
		
		setCalculatedTip( calculateTip(lastTotalPrice * DRAG_FACTOR) );
	}

	
	private void setCalculatedTip( float calculatedTip ){
		tvCalculatedTip.setText( "$" + String.valueOf( (int) calculatedTip) );
	}
	
	private void setCalculatedTotalPrice( float calculatedTotalPrice ){
		tvTotalPrice.setText( "$" + String.valueOf( (int) calculatedTotalPrice) );
	}

	private void setCustomFont() {
	    Typeface dinMedium = Typeface.createFromAsset(getAssets(), "fonts/DINPro_Medium.otf");
	    Typeface dinLight = Typeface.createFromAsset(getAssets(), "fonts/DINPro_Light.otf");
	    
	    tvTotalPrice.setTypeface(dinMedium);
	    tvCalculatedTip.setTypeface(dinMedium);
	    
	    tvCalculatedTipLabel.setTypeface(dinLight);
	    
	    tvTip10.setTypeface(dinMedium);
	    tvTip15.setTypeface(dinMedium);
	    tvTip20.setTypeface(dinMedium);
	}

}
