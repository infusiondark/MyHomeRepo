package com.socialapp.vkmini;



import com.socialapp.vkmini.api.Api;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import android.view.MenuInflater;


public class NewsActivity  extends Activity {

	 private final int REQUEST_LOGIN=1;
		private WebView wv;
		private String LASTURL = "";
		private Menu myMenu = null;  
		 private static final String PREFS_NAME = "MyPrefs";
		 private Boolean imgOn;
		 private  boolean mState = false;  
		 

	    Account account=new Account();
	    Api api;
	    
	    //початок Activity
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       // setTitle("Vkontakte Mini");
			  this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
	        setContentView(R.layout.activity_news);
	        
	  
	        //
	        account.restore(this);
	        
	        //
	        if(account.access_token!=null)
	            api=new Api(account.access_token, Constants.API_ID);
	        
	        wv = (WebView) findViewById(R.id.wv);
		    //збереження паролів і настройок на sd карті девайсу
		    WebSettings webSettings = wv.getSettings();
		    webSettings.setSavePassword(true);
		    webSettings.setSaveFormData(true);
		    webSettings.setJavaScriptEnabled(true);
		  	    
		    wv.getSettings().setPluginState(PluginState.ON);
		    
		   // cookie 
		    CookieSyncManager.createInstance(this); 
		    CookieManager cookieManager = CookieManager.getInstance();
		 
		    
		    saveSettings(true); 
		    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		    imgOn = settings.getBoolean("IMGMODE", false);
		    webSettings.setLoadsImagesAutomatically(imgOn);
		    
		

		
		    
		   
		 
		    
		    final Activity activity = this;
		    
		    
		    //створення прогресс бару загрузки сторінки
		    wv.setWebChromeClient(new WebChromeClient() {
		      public void onProgressChanged(WebView view, int progress)
		      {
		        activity.setTitle(" "+LASTURL);
		        activity.setProgress(progress * 100);

		        if(progress == 100)
		          activity.setTitle(" "+LASTURL);
		      }
		    });
		    
		    wv.setWebViewClient(new WebViewClient() {
		      public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		        Toast.makeText(getApplicationContext(), R.string.inet_check, Toast.LENGTH_LONG).show();
		        
		       
		      }
		      
		      @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url)
		      { 
		    	  	
		    	 
		        if  (url.indexOf("m.vk")<=0)  {
		        	
		        	// the link is not for a page on my site, so launch another Activity that handles URLs
		          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		          startActivity(intent);
		          	         
		        	          
		          return true;
		        } 
		        return false;
		      }
		      
		      public void Log(){
		    	  
		    	  logOut();
		    	 
		    	  backToStart();
		      }
		    
		      
	 // Запам'ятовування попередньої сесії
		      public  void onPageStarted (WebView view, String url) {
		    	 
		    	  LASTURL = url;
		    	  
		    	  
		    	  view.getSettings().setLoadsImagesAutomatically(false);
		      }
		      
		      public <Bitmap> void onPageFinished (WebView view, String url, Bitmap favicon) {
		    	
		    	
		        if (imgOn) view.getSettings().setLoadsImagesAutomatically(true);
		      }
		      
		    });
		   
		  
		    
		   
		    wv.loadUrl("http://m.vk.com");
	        
	        
	        
	    } //end of oncreate
	  
	//    @Override
	//    public void onConfigurationChanged(Configuration newConfig) {
	  //      super.onConfigurationChanged(newConfig);
	        // Проверяем ориентацию экрана
	   //     if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	            
	     //   } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
	          
	     //   }
	  //  }
	   
	  
	  //Опрацьовуємо кнопку бек на телефоні функцією go.back
	  @Override
	  //original- if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()
	  public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)&& wv.canGoBack() ) {
	      wv.goBack();

	      return true;
	    }
	    return super.onKeyDown(keyCode, event);
	  }
	  @Override
	  public void onBackPressed() {
	  new AlertDialog.Builder(this)
	  .setTitle(R.string.out_app)
	  .setMessage(R.string.out_quastion)
	  .setNegativeButton(R.string.out_no, null)
	  .setPositiveButton(R.string.out_yes, new OnClickListener()
	  {
	  public void onClick(DialogInterface arg0, int arg1) {
	 NewsActivity.super.onBackPressed();
	 finish();
	  }
	  }).create().show();
	  }
	  
	  //menu
	  
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
		  
		  
	    getMenuInflater().inflate(R.menu.news, menu);
	   	   
	    return true;
	  }

	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) 
	  { switch (item.getItemId())
		    {  
		    case android.R.id.home:
		        wv.loadUrl("http://m.vk.com/feed");
		        break;
		      case R.id.item1:
		    	  wv.loadUrl("javascript:(function() { " +
		      "window.scrollTo(0, 0); }");
		       
		        break;
		      case R.id.item2:
		    	  wv.loadUrl("http://m.vk.com/feed");
		    	  
		        break;
		      case R.id.item3:
		    	  wv.reload();
		    	 
		    	  
		        break;
		      case R.id.item4:
		    	  wv.loadUrl("http://m.vk.com/search?c%5Bsection%5D=people&c%5Bname%5D=1");
		    	  
		    	  
		        break;  
		      case R.id.item5:
		    	  wv.loadUrl("http://m.vk.com/search?c%5Bsection%5D=statuses&c%5Brated%5D=10");
		    	  
		    	  
		        break;  
		        
		      case R.id.settings_item5:
		    	  wv.clearCache(true);
		    	 
		    	  break;
		      case R.id.settings_item6:
		    	  saveSettings(true);     
		    	  Toast toast = Toast.makeText(getApplicationContext(), 
		    			   "Images On ", Toast.LENGTH_SHORT); 
		   	    	   	    toast.show(); 
		   	    	   	create_activity();
		   	    	   //	wv.reload();
		    	  break;
		    	  
		      case R.id.settings_item7:
		    	  saveSettings(false);  
			        Toast toast2 = Toast.makeText(getApplicationContext(), 
			    			   "Images Off ", Toast.LENGTH_SHORT); 
			   	    	   	    toast2.show();
			   	    	   	create_activity();
			   	    	  // 	wv.reload();
		    	 
		    	  break;
		    	  
		      case R.id.item8:
		    	  finish();
		    	  Intent intent = new Intent();
			        intent.setClass(this, AboutActivity.class);
			        startActivity(intent);  
		    	  break;
		    	  
		      case R.id.item9:
		    	  logOut();
		    	 
		    	  backToStart();
		    	  break;
		    }
		 
		    return true;
		  }
		
	  
	  
	  
	  
	  private void saveSettings(Boolean val)
	  {
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean("IMGMODE", val);
	    editor.commit();
	  }  
	  
	  
	    private void logOut() {
	        api=null;
	        account.access_token=null;
	        account.user_id=0;
	        account.save(NewsActivity.this);
	       
	    }
	    
	    
	    private void create_activity()
	    { finish();
	    	Intent intent = new Intent();
	        intent.setClass(this, MainActivity.class);
	        startActivity(intent);  
	    	
	    }
	    private void backToStart() {
	    	finish();
	        Intent intent = new Intent();
	        intent.setClass(this, MainActivity.class);
	        startActivity(intent);  
	    }
	    
	    
}