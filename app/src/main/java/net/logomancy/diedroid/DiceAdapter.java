package net.logomancy.diedroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class DiceAdapter extends ArrayAdapter<Integer> {
    private final PoolActivity poolActivity;
    Integer win;
    Integer fail;

    static class ViewHandler {
        TextView text;
    }

    DiceAdapter(PoolActivity poolActivity, Context con, int tvResID, RollResult roll) {
        super(con, tvResID, roll.rolls);
        this.poolActivity = poolActivity;
        win = roll.winThreshold;
        fail = roll.failThreshold;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler hand;
        Integer val = getItem(position);
        LayoutInflater inflater = this.poolActivity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.diegridsquare, parent, false);

            hand = new ViewHandler();
            hand.text = (TextView) convertView.findViewById(R.id.poolDieGridSquare);

            convertView.setTag(hand);
        } else {
            hand = (ViewHandler) convertView.getTag();
        }

        hand.text.setText(val.toString());

        if (win > 0 && val >= win) {
            hand.text.setTextColor(Color.GREEN);
        } else if (fail > 0 && val <= fail) {
            hand.text.setTextColor(Color.RED);
        } else if (fail > 0 || win > 0) {
            hand.text.setTextColor(Color.DKGRAY);
        }

        return convertView;
    }
}