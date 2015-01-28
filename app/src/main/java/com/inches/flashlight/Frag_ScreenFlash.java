package com.inches.flashlight;

import android.support.v4.app.Fragment;
import android.view.*;
import android.os.*;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.SeekBar.*;
import android.widget.AbsoluteLayout;

import android.util.Log;
import android.app.*;
import android.widget.CompoundButton.*;
import android.widget.*;

public class Frag_ScreenFlash extends Fragment
{
	private final static String TAG ="Inches";
	private ToggleButton sFlashController;
	private Window w;
	private WindowManager.LayoutParams p;
	private onScreenFlashStateChangedListener sfChanagedListener;
	
	public interface onScreenFlashStateChangedListener{
		public void onScreenFlashOn();
		public void onScreenFlashOff();
	}
	
	// private final int MAX = 90; // Real Max is 100 (Max+Min)
	private final int MIN = 10;

	@Override
	public void onAttach(Activity activity)
	{
		// TODO: Implement this method
		super.onAttach(activity);	
		sfChanagedListener =(onScreenFlashStateChangedListener) activity;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		final ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.flash_screen,container,false);
		try{
			
		w=getActivity().getWindow();
		p=w.getAttributes();
			
		final SeekBar brightContoller =(SeekBar) layout.findViewById(R.id.brightController);
		sFlashController =(ToggleButton) layout.findViewById(R.id.sFlashController);
		
	    sFlashController.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean state)
				{
					// TODO: Implement this method
					if(state){
						turnScreenFlashOn(brightContoller.getProgress());
						sfChanagedListener.onScreenFlashOn();
					}
					else{
						Log.i(TAG,"Toggle Off");
						turnScreenFlashOff();
						sfChanagedListener.onScreenFlashOff();
					}
				}	
		});
		
		brightContoller.setOnSeekBarChangeListener( new OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					// TODO: Implement this method
					if(sFlashController.isChecked()){
						turnScreenFlashOn(p2);
					}
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method				
				}		
		});
		
		}catch(Exception e){
			e.printStackTrace();
		}return layout;
	}

	@Override
	public void onStop()
	{
		// TODO: Implement this method
		super.onStop();
		sFlashController.setChecked(false);
		turnScreenFlashOff();
	}
		
	private void turnScreenFlashOn(int progress){
		p.screenBrightness=(float) (MIN+progress)/100;
		w.setAttributes(p);
	}
	
	private void turnScreenFlashOff(){
		p.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
		w.setAttributes(p);
	}
}
