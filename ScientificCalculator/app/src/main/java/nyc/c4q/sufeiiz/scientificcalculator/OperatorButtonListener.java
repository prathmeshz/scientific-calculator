package nyc.c4q.sufeiiz.scientificcalculator;

import android.view.View;
import android.widget.TextView;

/**
 * Created by sufeizhao on 5/19/15.
 */
public class OperatorButtonListener implements View.OnClickListener {

    private TextView mainOutput;
    private String equation, currNum, lastAns, lastNum, display, operator, operatorDisplay;

    public OperatorButtonListener(TextView mainOutput, String equation, String currNum, String lastAns, String lastNum, String display, String operator) {
        this.mainOutput = mainOutput;
        this.equation = equation;
        this.currNum = currNum;
        this.lastAns = lastAns;
        this.lastNum = lastNum;
        this.display = display;
        this.operator = operator;
        this.operatorDisplay = operator;
    }

    public OperatorButtonListener(TextView mainOutput, String equation, String currNum, String lastAns, String lastNum, String display, String operator, String operatorDisplay) {
        this.mainOutput = mainOutput;
        this.equation = equation;
        this.currNum = currNum;
        this.lastAns = lastAns;
        this.lastNum = lastNum;
        this.display = display;
        this.operator = operator;
        this.operatorDisplay = operatorDisplay;
    }

    @Override
    public void onClick(View v) {
        if (equation.isEmpty() && !lastAns.isEmpty()) {
            lastNum = lastAns;
            equation = lastAns + operator;
            display = lastAns + operatorDisplay;
        } else if (equation.isEmpty());
        else if (Numbers.lastInputIsOperator(equation)) {
            equation = equation.substring(0, equation.length()-1) + operator;
            display = display.substring(0, display.length()-1) + operatorDisplay;
        } else {
            equation += operator;
            lastNum = currNum;
            currNum = "";
            display += operatorDisplay;
        }
        mainOutput.setText(display);
    }
}
