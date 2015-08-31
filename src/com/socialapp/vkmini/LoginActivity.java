package com.socialapp.vkmini;

import com.socialapp.vkmini.Constants;
import com.socialapp.vkmini.LoginActivity.FacebookWebViewClient;
import com.socialapp.vkmini.api.Auth;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LoginActivity extends Activity {
	 private static final String TAG = "Vkmini.LoginActivity";
	 private String LASTURL = "";
	    WebView webview;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.loginn);
	        
	        webview = (WebView) findViewById(R.id.facebookview);
	        webview.getSettings().setJavaScriptEnabled(true);
	        webview.clearCache(true);
	        
	        //Чтобы получать уведомления об окончании загрузки страницы
	        webview.setWebViewClient(new FacebookWebViewClient());
	                
	        //otherwise CookieManager will fall with java.lang.IllegalStateException: CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()
	        CookieSyncManager.createInstance(this);
	        
	        CookieManager cookieManager = CookieManager.getInstance();
	        cookieManager.removeAllCookie();
	        
	        String url=Auth.getUrl(Constants.API_ID, Auth.getSettings());
	        webview.loadUrl(url);
	    }
	    final Activity activity = this;
	    
	    class FacebookWebViewClient extends WebViewClient {
	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	            super.onPageStarted(view, url, favicon);
	            parseUrl(url);
	        }
	        
	        public void onProgressChanged(WebView view, int progress)
		      {
		        activity.setTitle(" "+LASTURL);
		        activity.setProgress(progress * 100);

		        if(progress == 100)
		          activity.setTitle(" "+LASTURL);
		      }
	        
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		        Toast.makeText(getApplicationContext(), R.string.inet_check, Toast.LENGTH_LONG).show();
		        
		       
		      }
	    }
	    
	    private void parseUrl(String url) {
	        try {
	            if(url==null)
	                return;
	           // Log.i(TAG, "url="+url);
	            if(url.startsWith(Auth.redirect_url))
	            {
	                if(!url.contains("error=")){
	                    String[] auth=Auth.parseRedirectUrl(url);
	                    Intent intent=new Intent();
	                    intent.putExtra("token", auth[0]);
	                    intent.putExtra("user_id", Long.parseLong(auth[1]));
	                    setResult(Activity.RESULT_OK, intent);
	                }
	               finish();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
