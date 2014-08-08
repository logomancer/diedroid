package net.logomancy.diedroid;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import ec.util.MersenneTwisterFast;

public class PIOActivity extends ActionBarActivity implements OnClickListener {

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuAbout:
                Intent about = new Intent("org.openintents.action.SHOW_ABOUT_DIALOG");
                try {
                    startActivityForResult(about, 0);
                } catch (ActivityNotFoundException e) {
                    AlertDialog.Builder notFoundBuilder = new AlertDialog.Builder(this);
                    notFoundBuilder.setMessage(R.string.aboutNotFoundText)
                            .setTitle(R.string.aboutNotFoundTitle)
                            .setPositiveButton(R.string.commonYes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        Intent getApp = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.urlAboutMarket)));
                                        startActivity(getApp);
                                    } catch (ActivityNotFoundException e) {
                                        Intent getAppAlt = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.urlAboutWeb)));
                                        startActivity(getAppAlt);
                                    }
                                }
                            })
                            .setNegativeButton(R.string.commonNo, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog notFound = notFoundBuilder.create();
                    notFound.show();
                }
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
            Integer blank = R.color.Transparent;
            Integer[] OPS = {
                    R.drawable.pio_left,
                    R.drawable.pio_nop,
                    R.drawable.pio_in,
                    R.drawable.pio_nop,
                    R.drawable.pio_right,
                    R.drawable.pio_nop
            };
            ImageView imgOne = (ImageView) findViewById(R.id.pioDieOne);
            ImageView imgTwo = (ImageView) findViewById(R.id.pioDieTwo);
            ImageView imgThree = (ImageView) findViewById(R.id.pioDieThree);
            switch (howMany) {
                case R.id.pioOneDieBtn: // roll one die
                    imgOne.setImageResource(blank);
                    imgTwo.setImageResource(OPS[Random.nextInt(6)]);
                    imgThree.setImageResource(blank);
                    break;
                case R.id.pioTwoDiceBtn: // roll two dice
                    imgOne.setImageResource(OPS[Random.nextInt(6)]);
                    imgTwo.setImageResource(blank);
                    imgThree.setImageResource(OPS[Random.nextInt(6)]);
                    break;
                default: // roll three dice
                    imgOne.setImageResource(OPS[Random.nextInt(6)]);
                    imgTwo.setImageResource(OPS[Random.nextInt(6)]);
                    imgThree.setImageResource(OPS[Random.nextInt(6)]);
                    break;
            }
        } else {
            Toast.makeText(this, getString(R.string.pioErrNoDice), Toast.LENGTH_SHORT).show();
        }

    }


}
