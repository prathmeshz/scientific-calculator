package nyc.c4q.sufeiiz.scientificcalculator;

import android.view.View;
import android.widget.TextView;

/**
 * Created by sufeizhao on 5/19/15.
 */
public class ButtonListener implements View.OnClickListener {

    private TextView mainOutput;
    private String equation, currNum, display, button;

    public ButtonListener(TextView mainOutput, String button) {
        this.mainOutput = mainOutput;
        this.equation = equation;
        this.display = display;
        this.currNum = currNum;
        this.button = button;
    }

    @Override
    public void onClick(View v) {
        if (display.isEmpty());
        else if (display.charAt(display.length() - 1) == 's') {
            equation += "*";
            display += "×";
        }
        currNum += button;
        equation += button;
        display += button;
        mainOutput.setText(display);
    }
}
