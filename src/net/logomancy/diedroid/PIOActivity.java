package net.logomancy.diedroid;

import ec.util.MersenneTwisterFast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PIOActivity extends Activity implements OnClickListener{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passiton); // set our layout
        
        Button rollOne = (Button) findViewById(R.id.pioOneDieBtn);
        rollOne.setOnClickListener(this);
        Button rollTwo = (Button) findViewById(R.id.pioTwoDiceBtn);
        rollTwo.setOnClickListener(this);
        Button rollThree = (Button) findViewById(R.id.pioThreeDiceBtn);
        rollThree.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.optionmenu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menuAbout:
	    	String version = null;
	    	try {
				version = getPackageManager().getPackageInfo("net.logomancy.diedroid", 0).versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuilder title = new StringBuilder();
			title.append(getString(R.string.app_name));
			title.append(" ");
			title.append(version);
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.menuAboutText)
	    	       .setTitle(title.toString())
	    	       .setPositiveButton(R.string.menuAboutSiteBtn, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                Uri url = Uri.parse(getString(R.string.urlWebsite));
	    	                startActivity(new Intent("android.intent.action.VIEW", url));
	    	                
	    	           }
	    	       })
	    	       .setNeutralButton(R.string.menuAboutLicBtn, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   Uri url = Uri.parse(getString(R.string.urlLicense));
	    	               startActivity(new Intent("android.intent.action.VIEW", url));
	    	           }
	    	       })
	    	       .setNegativeButton(R.string.commonClose, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog about = builder.create();
	    	about.show();
	    	return true;
	    case R.id.menuHelp:
	    	AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
	    	builder1.setMessage(R.string.menuHelpPIO)
	    	       .setTitle(R.string.menuHelp)
	    	       .setNegativeButton(R.string.commonClose, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog help = builder1.create();
	    	help.show();
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	public void onClick(View v) {
		int howMany = v.getId();
		if (howMany != -1) {
			MersenneTwisterFast Random = new MersenneTwisterFast();
			Integer[] diceArray = new Integer[3];
			Integer[] resArray = new Integer[3];
			resArray[0] = R.id.pioDieOne;
			resArray[1] = R.id.pioDieTwo;
			resArray[2] = R.id.pioDieThree;
			switch(howMany) {
			case R.id.pioOneDieBtn: // roll one die
				diceArray[0] = 0;
				diceArray[1] = Random.nextInt(6) + 1;
				diceArray[2] = 0;
				break;
			case R.id.pioTwoDiceBtn: // roll two dice
				diceArray[0] = Random.nextInt(6) + 1;
				diceArray[1] = 0;
				diceArray[2] = Random.nextInt(6) + 1;
				break;
			default: // roll three dice
				diceArray[0] = Random.nextInt(6) + 1;
				diceArray[1] = Random.nextInt(6) + 1;
				diceArray[2] = Random.nextInt(6) + 1;
				break;
			}
			for (int i = 0; i < 3; i++) {
				ImageView img = (ImageView) findViewById(resArray[i]);
				switch(diceArray[i]) {
				case 0: // blank
					img.setImageResource(R.color.Black);
					break;
				case 1: // pass left
					img.setImageResource(R.drawable.pio_left);
					break;
				case 3: // pass in
					img.setImageResource(R.drawable.pio_in);
					break;
				case 5: // pass right
					img.setImageResource(R.drawable.pio_right);
					break;
				default: // do nothing
					img.setImageResource(R.drawable.pio_nop);
					break;
				}
			}			
		}
		else {
			Toast.makeText(this, getString(R.string.pioErrNoDice), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	

}
