package com.ioiomint.pixelhalloweenprox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.hardware.SensorManager;

/**
 * Displays images from an SD card.
 */
@SuppressLint({ "ParserError", "NewApi" })
public class calibrate extends IOIOActivity   {

    //look into internet loads
	//make an  directory, copy some files in there
   
	//private ioio.lib.api.RgbLedMatrix.Matrix KIND;  //have to do it this way because there is a matrix library conflict
	
    private static final String TAG = "Pixel Calibrate";
  
	private String OKText;
	private Resources resources;
	private String app_ver;	
	private int matrix_model;
	private final String tag = "";	
	private String setupInstructionsString; 
	private String setupInstructionsStringTitle;	
	private boolean noSleep = false;
	
	
    private Display display;
     
     private int proximityPin_;
     private int proximityThresholdLower_;
     private int proximityThresholdUpper_;
     
     
    // private TextView firstTimeSetupCounter_;
     private TextView proxTextView_;
     private TextView firstTimeSetup1_;
     private ProgressDialog pDialog = null;
     private boolean showProx_;
     private int proxTriggeredFlag = 0;
     private int proxCounter = 1;
  //   private boolean dimDuringSlideShow = false;
     
     //add long click to delete an image
     

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //force only portrait mode        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        
               
        try
        {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        }
        catch (NameNotFoundException e)
        {
            Log.v(tag, e.getMessage());
        }
        
      
        
        proxTextView_ = (TextView)findViewById(R.id.proxTextView);
        firstTimeSetup1_ = (TextView)findViewById(R.id.firstTimeSetup1);
        
      
 		
 	//	setupInstructionsString = getResources().getString(R.string.setupInstructionsString);
      //  setupInstructionsStringTitle = getResources().getString(R.string.setupInstructionsStringTitle);
   
    }
    
   
    protected void onDestroy() {
        super.onDestroy();
    }
	
	private void showNotFound() {	
		AlertDialog.Builder alert=new AlertDialog.Builder(this);
		alert.setTitle(getResources().getString(R.string.notFoundString)).setIcon(R.drawable.icon).setMessage(getResources().getString(R.string.bluetoothPairingString)).setNeutralButton(getResources().getString(R.string.OKText), null).show();	
}
      
	
    
    class IOIOThread extends BaseIOIOLooper {
  		//private ioio.lib.api.RgbLedMatrix matrix_;
  		private AnalogInput prox_;
  		float proxValue;

  		@Override
  		protected void setup() throws ConnectionLostException {
  			prox_ = ioio_.openAnalogInput(proximityPin_);		
  			
  			//deviceFound = 1; //if we went here, then we are connected over bluetooth or USB
  		}

  		@Override
  		public void loop() throws ConnectionLostException {
  		
  		try {
			
  			proxValue = prox_.read();
  			proxValue = proxValue * 1000;	
  			int proxInt = (int)proxValue;
  			
  			//if (showProx_ == true) {
  				setText(Integer.toString(proxInt));
  			//}
  			
  		
				
				Thread.sleep(10);
			} catch (InterruptedException e) {
				ioio_.disconnect();
			} catch (ConnectionLostException e) {
				throw e;
			}
  		
  			
		}
  		
  		
  		
  		}

  	@Override
  	protected IOIOLooper createIOIOLooper() {
  		return new IOIOThread();
  	}
    
    private void setHomeText(final String str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				firstTimeSetup1_.setText(str);
			}
		});
	}
    
    private void setText(final String str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				proxTextView_.setText(str);
			}
		});
	}
    
   
    
}
    
    
    
    
    
















