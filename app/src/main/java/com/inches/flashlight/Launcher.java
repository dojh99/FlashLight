package com.inches.flashlight;

import android.os.*;
import android.view.*;
import android.widget.*;
import android.util.Log;

import android.support.v7.app.ActionBarActivity;

import com.inches.flashlight.Frag_NormalFlash;
import com.inches.flashlight.Frag_ScreenFlash;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



public class Launcher extends ActionBarActivity implements Frag_ScreenFlash.onScreenFlashStateChangedListener
{
	private FragmentManager fm;
	private Frag_NormalFlash nf = new Frag_NormalFlash();
	private Frag_ScreenFlash sf = new Frag_ScreenFlash();
	
	private Button modeChanger;
	
	private int mode;
	private final int MODE_NORMAL_FLASH = 0x0;
	private final int MODE_SCREEN_FLASH = 0x1;
	
	int container = R.id.fragContainer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		fm = getSupportFragmentManager();
		
		final FragmentTransaction ft = fm.beginTransaction();
		ft.add(container,nf);
		ft.commit();
		
		mode = MODE_NORMAL_FLASH;
		
		modeChanger = (Button) findViewById(R.id.modeChanger);
		modeChanger.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//Should Fix Later!!
					try{
				    FragmentTransaction ft = fm.beginTransaction();
					// TODO: Implement this method
					switch(mode){
						case MODE_NORMAL_FLASH:
							if(mode!=MODE_SCREEN_FLASH){
								ft.replace(container,sf);
								mode=MODE_SCREEN_FLASH;
							}
							break;
						case MODE_SCREEN_FLASH:
							if(mode!=MODE_NORMAL_FLASH){
								ft.replace(container,nf);
								mode=MODE_NORMAL_FLASH;
							}
							break;
					}
					ft.commit();
					} catch(Exception e){
						e.printStackTrace();
					}
				}

			
		});
		
		
	}
	
	@Override
	public void onScreenFlashOn()
	{
		// TODO: Implement this method
		modeChanger.setVisibility(View.INVISIBLE);
		getSupportActionBar().hide();
	}

	@Override
	public void onScreenFlashOff()
	{
		// TODO: Implement this method
		modeChanger.setVisibility(View.VISIBLE);
		getSupportActionBar().show();
	}
	
	
}
