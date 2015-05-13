package nyc.c4q.sufeiiz.scientificcalculator;

import android.widget.TextView;

/**
 * Created by sufeizhao on 5/5/15.
 */
public class Numbers {

    private TextView mainOutput;
    private String num, current, equation;

    public Numbers(String num, TextView mainOutput) {
        this.num = num;
        this.mainOutput = mainOutput;
    }

    public void click() {
        current += "0";
        equation += "0";
        mainOutput.setText(current);
    }
}

