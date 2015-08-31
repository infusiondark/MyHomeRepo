package com.socialapp.vkmini;

import com.socialapp.vkmini.Account;
import com.socialapp.vkmini.Constants;
import com.socialapp.vkmini.LoginActivity;
import com.socialapp.vkmini.MainActivity;
import com.socialapp.vkmini.NewsActivity;
import com.socialapp.vkmini.api.Api;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	 private final int REQUEST_LOGIN=1;
	    
	    Button authorizeButton;
	    Button logoutButton;
	
	    
	    Account account=new Account();
	    Api api;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	       
	        
	        setupUI();
	        
	        //Восстановление сохранённой сессии
	        account.restore(this);
	        
	        //Если сессия есть создаём API для обращения к серверу
	       
	        
	        if(account.access_token!=null)
	        { api=new Api(account.access_token, Constants.API_ID);
	      
	        }
	       
	     
	        showButtons();
	    }
	    


	    private void setupUI() {
	        authorizeButton=(Button)findViewById(R.id.authorize);
	        authorizeButton.setOnClickListener(authorizeClick);
	    
	        logoutButton=(Button)findViewById(R.id.logout);        	     
	        logoutButton.setOnClickListener(logoutClick);

	        
	    }
	    
	    private OnClickListener authorizeClick=new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	            startLoginActivity();
	        }
	    };
	    
	    private OnClickListener logoutClick=new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	            logOut();
	       }
	    }; 
	   
	    
	    private void startLoginActivity() {
	        Intent intent = new Intent();
	        intent.setClass(this, LoginActivity.class);
	        startActivityForResult(intent, REQUEST_LOGIN);
	    }
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == REQUEST_LOGIN) {
	            if (resultCode == RESULT_OK) {
	                //авторизовались успешно 
	                account.access_token=data.getStringExtra("token");
	                account.user_id=data.getLongExtra("user_id", 0);
	                account.save(MainActivity.this);
	                api=new Api(account.access_token, Constants.API_ID);
	               
	                
	                showButtons();
	            }
	        }
	    }
	    
	  
	     
	    private void logOut() {
	        api=null;
	        account.access_token=null;
	        account.user_id=0;
	        account.save(MainActivity.this);
	        showButtons();
	    }
	    void Action (){
	    
	    	if(account.access_token!=null) {
	        api=new Api(account.access_token, Constants.API_ID);
	        	finish();
	        	Intent intent = new Intent(MainActivity .this, NewsActivity.class);
	        	startActivity(intent);
	        
	    	
	    	}
	    	
	    }
	    
	    void showButtons(){
	    	Action ();
	        if(api!=null){
	            authorizeButton.setVisibility(View.GONE);
	            logoutButton.setVisibility(View.VISIBLE);

	        }else{ 
	            authorizeButton.setVisibility(View.VISIBLE);
	            logoutButton.setVisibility(View.GONE);
	    
	        }
	        
	    }
	
	    
	    
}
