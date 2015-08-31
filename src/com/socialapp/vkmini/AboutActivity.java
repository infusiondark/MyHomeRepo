package com.socialapp.vkmini;



import android.view.Menu;
import java.net.URLEncoder;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity {
	 Button button3;
	    Button button2;
	    Button button1;
	    
	    TextView tvShow;
	    TextView tvShow_company;
	    ImageView image1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		 tvShow = (TextView) findViewById(R.id.textView1);
		 tvShow_company = (TextView) findViewById(R.id.textView2);
		 setupUI();
	
	}


	
	  private void setupUI() {
		  button1=(Button)findViewById(R.id.button1);
		  button1.setOnClickListener(button1Click);
	    
		  button2=(Button)findViewById(R.id.button2);        	     
		  button2.setOnClickListener(button2Click);
		  
		  button3=(Button)findViewById(R.id.button3);        	     
		  button3.setOnClickListener(button3Click);
		  
		  image1=(ImageView) findViewById(R.id.imageView1);
		  image1.setOnClickListener(imageClick); 
	    }
	    
	  private OnClickListener button2Click=new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        	final Intent email = new Intent(android.content.Intent.ACTION_SENDTO);
				String uriText = "mailto:muhabohdan@hotmail.com" +
						"?subject=" + URLEncoder.encode("VK mini Feedback"); 
				email.setData(Uri.parse(uriText));
				try {
					startActivity(email);
				} catch (Exception e) {
					
				}
	        }
	    };
	    
	    private OnClickListener button3Click=new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        	Intent Intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(
	                    "http://bmm-studio.mobi"));
	                  startActivity(Intent3);  
	       }
	    }; 

	    private OnClickListener button1Click=new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	        	Intent marketIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(
	                    "http://market.android.com/details?id=" + getPackageName()));
	                  startActivity(marketIntent2);    
	       }
	    }; 
	    private OnClickListener imageClick=new OnClickListener(){
	        @Override
	    public void onClick(View v){
	        	
	        	Intent MyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/infusi0n"));
	                  startActivity(MyIntent); 
	    	//new AlertDialog.Builder(this)
				//.setTitle(R.string.about_app)
			//	.setMessage(Html.fromHtml(getString(R.string.about_msg)))
				//.show();
	        }
	    };      
	    
	    @Override
		  public boolean onCreateOptionsMenu(Menu menu) {
			  
			  
		    getMenuInflater().inflate(R.menu.about, menu);
		   	   
		    return true;
		  }

		  
		  @Override
		  public boolean onOptionsItemSelected(MenuItem item) 
		  { switch (item.getItemId())
			    {  
			    
			      case R.id.about1:
			    	  Intent MyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bodjabest?ref=tn_tnmn"));
	                  startActivity(MyIntent); 
			       
			        break;
			      case R.id.about2:
			    	  Intent MyIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/107357645842189645136/posts"));
	                  startActivity(MyIntent2); 
			    	  
			        break;
			      case R.id.about3:
			    	new AlertDialog.Builder(this)
						.setTitle(R.string.prog_lizens)
						.setMessage(Html.fromHtml(getString(R.string.apache_license)))
						.show();
			    	 
			    	  
			        break;
			      case R.id.about4:
			    	  new AlertDialog.Builder(this)
						.setTitle(R.string.about4)
						.setMessage(Html.fromHtml(getString(R.string.about_msg)))
						.show();
			    	  
			    	  
			        break;  
			     
			    }
			 
			    return true;
			  }
}