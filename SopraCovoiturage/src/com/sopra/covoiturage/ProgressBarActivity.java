package com.sopra.covoiturage;

import modele.Information;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class ProgressBarActivity extends Activity {

	private FacadeView facade ;
	protected ProgressDialog mProgressDialog;

	 
	public static final String TAG = "ProgressBarActivity";
	
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.progress_bar_page);
	    facade = FacadeView.getInstance(this) ;
	    
	    if (getIntent().getStringExtra("nickname").equals("")) {
	    // cas de l'inscription
		    System.out.println(getIntent().getStringExtra("type")) ;
		    mProgressDialog = ProgressDialog.show(this, "Patientez s'il vous plait",getIntent().getStringExtra("type"), true);
		            
		    final Handler uiThreadCallback = new Handler();
		    
		    final Runnable runInUIThread = new Runnable() {
	    	    public void run() {
	    	    	facade.changeActivity(com.sopra.covoiturage.ConnectingActivity.class);  	     
	    	    }
		    };
		    
		    new Thread((new Runnable() {
		        @Override
		        public void run() {
		        	facade.performRegisterGo(facade.getInfo()) ;
		            if (mProgressDialog.isShowing()) {
		                mProgressDialog.dismiss();   
		            }
		            
		            uiThreadCallback.post(runInUIThread);
		            //doLongOperation2() ; 
		        }
		    })).start();
	    }
	    else {
	    	System.out.println(getIntent().getStringExtra("type")) ;
		    mProgressDialog = ProgressDialog.show(this, "Patientez s'il vous plait",getIntent().getStringExtra("type"), true);
		            
		    final Handler uiThreadCallback = new Handler();
		    
		    final Runnable runInUIThread = new Runnable() {
	    	    public void run() {
	    	    	facade.changeActivity(com.sopra.covoiturage.ConnectingActivity.class);  	     
	    	    }
		    };
		    
		    new Thread((new Runnable() {
		        @Override
		        public void run() {
		        	facade.performConnectGo(getIntent().getStringExtra("nickname"), getIntent().getStringExtra("mdp")) ;
		            if (mProgressDialog.isShowing()) {
		                mProgressDialog.dismiss();   
		            }
		            
		            uiThreadCallback.post(runInUIThread);
		            //doLongOperation2() ; 
		        }
		    })).start();
	    }
	}

}

