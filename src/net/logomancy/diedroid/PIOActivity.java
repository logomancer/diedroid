package net.logomancy.diedroid;

import ec.util.MersenneTwisterFast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PIOActivity extends Activity implements OnClickListener{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passiton); // set our layout
        
        Button roll = (Button) findViewById(R.id.pioRollBtn);
        roll.setOnClickListener(this);
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
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.menuAboutText)
	    	       .setCancelable(false)
	    	       .setTitle(R.string.menuAboutTitle)
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
	    	       .setNegativeButton(R.string.CloseBtnText, new DialogInterface.OnClickListener() {
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
	    	       .setCancelable(false)
	    	       .setTitle(R.string.menuHelp)
	    	       .setNegativeButton(R.string.CloseBtnText, new DialogInterface.OnClickListener() {
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
		RadioGroup diceBtns = (RadioGroup) findViewById(R.id.pioDiceGroup);
		int howMany = diceBtns.getCheckedRadioButtonId();
		if (howMany != -1) {
			MersenneTwisterFast Random = new MersenneTwisterFast();
			Integer[] diceArray = new Integer[3];
			Integer[] resArray = new Integer[3];
			resArray[0] = R.id.pioDieOne;
			resArray[1] = R.id.pioDieTwo;
			resArray[2] = R.id.pioDieThree;
			switch(howMany) {
			case R.id.pio1Dice: // roll one die
				diceArray[0] = 0;
				diceArray[1] = Random.nextInt(6) + 1;
				diceArray[2] = 0;
				break;
			case R.id.pio2Dice: // roll two dice
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
