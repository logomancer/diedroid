package net.logomancy.diedroid;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PoolDialog extends Dialog implements OnClickListener, OnSeekBarChangeListener {

    public interface ReturnListener {
        public void onReturn(int returned);
    }

    public static final int POOL_DIALOG_WIN = 1;
    public static final int POOL_DIALOG_FAIL = 0;

    private SeekBar slider;
    private TextView caption;
    private TextView valueText;
    private Button submit;
    private ReturnListener returnCall;

    private int value;

    public PoolDialog(Context context, Integer dialogType, ReturnListener returner) {
        super(context);
        setTitle(R.string.poolDialogTitle);
        setContentView(R.layout.pool_dialog);

        slider = (SeekBar) findViewById(R.id.poolDialogSlider);
        slider.setOnSeekBarChangeListener(this);

        caption = (TextView) findViewById(R.id.poolDialogCaption);

        if (dialogType == POOL_DIALOG_WIN) {
            caption.setText(R.string.poolWinDialogCaption);
        } else {
            caption.setText(R.string.poolFailDialogCaption);
        }

        valueText = (TextView) findViewById(R.id.poolDialogValue);
        submit = (Button) findViewById(R.id.poolDialogClose);
        submit.setOnClickListener(this);

        returnCall = returner;
    }

    public void setLimit(int lim) {
        if (lim >= 0) {
            if (value > lim) {
                value = lim;
            }
            slider.setProgress(value);
            slider.setMax(lim);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (progress == 0) {
            valueText.setText("--");
        } else {
            valueText.setText(Integer.toString(progress));
        }

        value = progress;

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        // do nothing

    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        // do nothing

    }

    public void onClick(View v) {
        returnCall.onReturn(value);
        dismiss();
    }

}
