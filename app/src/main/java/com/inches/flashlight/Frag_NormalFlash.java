package com.inches.flashlight;

import android.os.*;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

import android.support.v4.app.Fragment;

public class Frag_NormalFlash extends Fragment
{
	/* Should I have to init cameras in background threaad? It took so long!*/
	
	private final static String TAG ="Inches";
	private ToggleButton flashToggle;
	private boolean isFlashValid = false;
	private static Camera cam = null;
	Parameters p = null;

	@Override
	public void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		final Context c = getActivity();
		isFlashValid = c.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		// TODO: Implement this method	
		final ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.flash_normal, container, false);

		if (isFlashValid){	
			try{
				initCam();
				p = cam.getParameters();
			}
			catch (Exception e){
				e.printStackTrace();
			}	
		    flashToggle = (ToggleButton) layout.findViewById(R.id.nFlashController);
		    flashToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			    	@Override
			    	public void onCheckedChanged(CompoundButton p1, boolean state)
			    	{
			    		if (state){
			    			turnFlashOn();			
			    		}
						else{
				    	    turnFlashOff();			
		    			}
		    	    }		
				});		
	   	}
		return layout;
	}
	
	@Override
	public void onPause(){
		// TODO: Implement this method
		super.onStop();	
		if (flashToggle.isChecked()){
			flashToggle.setChecked(false);
		}
		if (cam != null){
			cam.release();
			cam = null;
		}
	}

	private void turnFlashOn()
	{
		try{
			initCam();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			cam.setParameters(p);
			cam.startPreview();		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private void turnFlashOff(){
		try{
			if (cam == null)
				return;
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			cam.setParameters(p);
			cam.stopPreview();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private void initCam(){
		if (cam == null){
			cam = Camera.open();
	   	}
    }
}
