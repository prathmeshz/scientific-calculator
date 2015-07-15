package nyc.c4q.sufeiiz.scientificcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.math.BigDecimal;

public class MainActivity extends ActionBarActivity {

    private Button clr, ans, negative, backspace, open, close, add, minus, multiply, divide, percent, zero, one, two, three,
            four, five, six, seven, eight, nine, dot, equal, factorial, pi, ee, sqRoot, sq, sin, cos, tan, exp, ln, log;
    private TextView outputEq, outputAns, mode;
    private String equation, currNum, lastAns, lastNum, display;
    private int count;
    private double π = 3.14159265359, e = 2.71828182846;
    private boolean degMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        if (savedInstanceState == null) {
            outputEq.setText("");
            outputAns.setText("");
            clear();
            lastAns = "0";
        } else {
            outputAns.setText(savedInstanceState.getString("lastAns"));
            outputEq.setText(savedInstanceState.getString("display"));
            display = savedInstanceState.getString("display");
            equation = savedInstanceState.getString("equation");
            currNum = savedInstanceState.getString("currNum");
            lastNum = savedInstanceState.getString("lastNum");
            lastAns = savedInstanceState.getString("lastAns");
            count = savedInstanceState.getInt("count");
        }

        // set OnClickListeners, methods located below onCreate
        zero.setOnClickListener(new numClickListener(0));
        one.setOnClickListener(new numClickListener(1));
        two.setOnClickListener(new numClickListener(2));
        three.setOnClickListener(new numClickListener(3));
        four.setOnClickListener(new numClickListener(4));
        five.setOnClickListener(new numClickListener(5));
        six.setOnClickListener(new numClickListener(6));
        seven.setOnClickListener(new numClickListener(7));
        eight.setOnClickListener(new numClickListener(8));
        nine.setOnClickListener(new numClickListener(9));
        add.setOnClickListener(new operatorClickListener('+'));
        minus.setOnClickListener(new operatorClickListener('-'));
        multiply.setOnClickListener(new operatorClickListener('*'));
        divide.setOnClickListener(new operatorClickListener('/'));
        dot.setOnClickListener(new dotClickListener());
        clr.setOnClickListener(new clrClickListener());
        backspace.setOnClickListener(new backspaceClickListener());
        percent.setOnClickListener(new percentClickListener());
        negative.setOnClickListener(new negativeClickListener());
        open.setOnClickListener(new openClickListener());
        close.setOnClickListener(new closeClickListener());
        ans.setOnClickListener(new ansClickListener());
        equal.setOnClickListener(new equalClickListener());

        // Scientific calculator
        if (log != null) {

            // Toggle radians/degrees
            ToggleButton toggle = (ToggleButton) findViewById(R.id.btn_rad);
            mode = (TextView) findViewById(R.id.mode);
            mode.setText("Deg");
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mode.setText("Rad");
                        degMode = false;
                    } else {
                        mode.setText("Deg");
                        degMode = true;
                    }
                }
            });

            // set scientific onClickListener
            pi.setOnClickListener(new constantsClickListener(π));
            ee.setOnClickListener(new constantsClickListener(e));
            sin.setOnClickListener(new functionClickListener("sin("));
            cos.setOnClickListener(new functionClickListener("cos("));
            tan.setOnClickListener(new functionClickListener("tan("));
            ln.setOnClickListener(new functionClickListener("LOG(")); //display = ln(), equation = log()
            log.setOnClickListener(new functionClickListener("LOG10(")); // display = log(), equation = log10()
            sqRoot.setOnClickListener(new functionClickListener("SQRT(")); //display = √(), equations = SQRT()
            factorial.setOnClickListener(new factorialClickListener());
            exp.setOnClickListener(new expClickListener());
            sq.setOnClickListener(new sqClickListener());
        }
    }

    /**
     * ON CLICK LISTENERS
     * **/
    private class numClickListener implements View.OnClickListener {
        private int num;
        public numClickListener(int num) {
            this.num = num;
        }

        @Override
        public void onClick(View v) {
            if (display.isEmpty()) ;
            else if (Numbers.lastInputIsConstant(display) || display.endsWith("!")) {
                insertNum("*", false, true, true);
            }
            insertNum(num + "", true, true, true);
            outputEq.setText(display);
        }
    }

    private class operatorClickListener implements View.OnClickListener {
        private char operator;
        public operatorClickListener(char operator) {
            this.operator = operator;
        }

        @Override
        public void onClick(View v) {
            if (equation.isEmpty() && !lastAns.isEmpty()) {
                lastNum = lastAns;
                equation = lastAns + operator;
                display = lastAns + operator;
            } else if (equation.isEmpty() || Numbers.lastInputIsTrig(display) || Numbers.lastInputIsError(display)) ;
            else if (Numbers.lastInputIsOperator(equation))
                removeCharsAddChars(1, 1, operator+"", operator+"");
            else {
                lastNum = currNum;
                currNum = "";
                insertNum(operator + "", false, true, true);
            }
            outputEq.setText(display.replaceAll("\\*", "×").replaceAll("/", "÷"));
        }
    }

    class constantsClickListener implements View.OnClickListener {
        private double constant;
        constantsClickListener(double constant) {
            this.constant = constant;
        }

        @Override
        public void onClick(View v) {
            if (display.isEmpty())
                insertNum(Double.toString(constant), false, true, true);
            else if (Numbers.lastInputIsConstant(display) || Numbers.lastInputIsDigit(display) || display.endsWith(")"))
                insertNum("*" + constant, false, true, true);
            else
                insertNum(Double.toString(constant), false, true, true);

            outputEq.setText(display.replaceAll("3.14159265359", "π").replaceAll("2.71828182846", "e"));
        }
    }

    //TODO fix display vs equation
    class functionClickListener implements View.OnClickListener {
        private String trig;
        functionClickListener(String trig) {
            this.trig = trig;
        }

        @Override
        public void onClick(View v) {
            if (degMode || Numbers.equalsNontrig(trig)) {
                if (equation.isEmpty())
                    insertNum(trig, false, true, true);
                else if (display.endsWith("E") || display.endsWith("^") || display.endsWith(".")) ;
                else if (Numbers.lastInputIsDigit(display) || equation.endsWith(")") || Numbers.lastInputIsConstant(display))
                    insertNum("*" + trig, false, true, true);
                else
                    insertNum(trig, false, true, true);
            } else {
                if (equation.isEmpty()) {
                    equation += "RAD(" + trig;
                    display += trig;
                } else if (display.endsWith("E") || display.endsWith("^") || display.endsWith(".")) ;
                else if (Numbers.lastInputIsDigit(display) || equation.endsWith(")") || Numbers.lastInputIsConstant(display)) {
                    equation += "*RAD(" + trig;
                    display += "×" + trig;
                } else {
                    equation += "RAD(" + trig;
                    display += trig;
                }
                count++;
            }
            count++;
            outputEq.setText(display.replaceAll("LOG\\(", "ln(").replaceAll("LOG10\\(", "log(").replaceAll("SQRT\\(", "√("));
        }
    }

    private class dotClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (currNum.contains(".")) ;
            else if (equation.isEmpty())
                insertNum("0.", true, true, true);
            else {
                if (currNum.equals("") || Numbers.lastInputIsOperator(equation))
                    insertNum("0.", true, true, true);
                else
                    insertNum(".", true, true, true);
            }
            outputEq.setText(display);
        }
    }

    private class clrClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            lastAns = "0";
            clear();
            outputAns.setText("");
            outputEq.setText(display);
        }
    }

    private class backspaceClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty() || display.isEmpty()) ;
            else if (display.endsWith("%")) {
                String converted = Numbers.percentage(currNum, lastNum);
                removeChars(converted.length(), 1);
                equation += currNum;
            } else if (display.endsWith("!")) {
                String converted = String.valueOf(Numbers.factorial(Integer.valueOf(currNum)));
                removeChars(converted.length(), 1);
                equation += currNum;
            } else if (display.endsWith("e") || display.endsWith("π")) {
                currNum = "";
                removeChars(13, 1);
            } else if (display.endsWith("s")) {
                currNum = "";
                removeChars(currNum.length(), 3);
            } else if (Numbers.lastInputIsTrig(display)) {
                removeChars(4, 4);
                count--;
            } else if (display.endsWith("LN(")) {
                removeChars(3, 3);
                count--;
            } else if (display.endsWith(")")) {
                removeChars(1, 1);
                count++;
            } else {
                if (currNum.length() > 0)
                    currNum = currNum.substring(0, currNum.length() - 1);
                if (equation.length() > 0)
                    equation = equation.substring(0, equation.length() - 1);
                if (display.length() > 0)
                    display = display.substring(0, display.length() - 1);
            }
            outputEq.setText(display);
        }
    }

    private class percentClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty() || display.isEmpty()) ;
            else if (Numbers.lastInputIsFunction(display) || Numbers.lastInputIsOperator(equation)) ;
            else {
                equation = equation.substring(0, equation.length() - currNum.length());
                String converted = Numbers.percentage(currNum, lastNum);

                equation += converted;
                display += "%";
                outputEq.setText(display);
            }
        }
    }

    public class negativeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty()) {
                if (currNum.contains("-"))
                    currNum = currNum.substring(1);
                else
                    currNum = "-" + currNum;
                insertNum(currNum, false, true, true);
            } else if (display.endsWith("%")) {
                String converted = Numbers.percentage(currNum, lastNum);
                removeChars(converted.length(), 1);
                if (currNum.contains("-")) {
                    currNum = currNum.substring(1);
                    display += currNum + "%";
                    equation += converted.substring(1);
                } else {
                    currNum = "-" + currNum;
                    display += currNum + "%";
                    equation += "-" + converted;
                }
            } else if (display.endsWith("!")) {
                String converted = String.valueOf(Numbers.factorial(Integer.valueOf(currNum)));
                removeChars(converted.length(), 1);
                if (currNum.contains("-")) {
                    currNum = currNum.substring(1);
                    display += currNum + "!";
                    equation += converted.substring(1);
                } else {
                    currNum = "-" + currNum;
                    display += currNum + "!";
                    equation += "-" + converted;
                }
            } else if (display.endsWith("e") || display.endsWith("π")) {
                char constant = display.charAt(display.length() - 1);
                if (currNum.contains("-")) {
                    currNum = currNum.substring(1);
                    removeCharsAddChars(14, 2, currNum, constant+"");
                } else {
                    currNum = "-" + currNum;
                    removeCharsAddChars(13, 1, currNum, constant+"");
                }
            } else if (display.endsWith("s")) {
                if (currNum.equals("0")) {
                    display = display.substring(0, display.length() - 3) + "-" + "Ans";
                } else if (currNum.contains("-")) {
                    currNum = currNum.substring(1);
                    removeCharsAddChars(currNum.length() - 1, 4, currNum, "Ans");
                } else {
                    currNum = "-" + currNum;
                    removeCharsAddChars(currNum.length() + 1, 3, currNum, "-Ans");
                }
            } else if (display.endsWith("^")) {
                if (currNum.contains("-")) {
                    currNum = currNum.substring(1);
                    removeCharsAddChars(currNum.length() - 1, currNum.length() - 1, currNum, currNum);
                } else {
                    currNum = "-" + currNum;
                    removeCharsAddChars(currNum.length() + 1, currNum.length() + 1, currNum, currNum);
                }
            } else {
                removeChars(currNum.length(), currNum.length());
                if (currNum.contains("-"))
                    currNum = currNum.substring(1);
                else
                    currNum = "-" + currNum;

                insertNum(currNum, false, true, true);
            }
            outputEq.setText(display);
        }
    }

    public class openClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty())
                insertNum("(", false, true, true);
            else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                    equation.endsWith(")") || Numbers.lastInputIsConstant(display))
                insertNum("*(", false, true, true);
            else
                insertNum("(", false, true, true);
            lastAns = currNum;
            currNum = "";
            count++;
            outputEq.setText(display);
        }
    }

    public class closeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty() || count <= 0 || Numbers.lastInputIsOperator(equation)) ;
            else {
                insertNum(")", false, true, true);
                lastAns = currNum;
                currNum = "";
                count--;
                outputEq.setText(display);
            }
        }
    }

    public class ansClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            lastNum = currNum;
            currNum = lastAns;
            if (lastAns.isEmpty()) ;
            else if (display.isEmpty()) {
                equation += lastAns;
                display += "Ans";
            } else if (Numbers.lastInputIsConstant(display) || Character.isDigit(display.charAt(display.length() - 1))) {
                equation += "*" + lastAns;
                display += "×Ans";
            } else {
                equation += lastAns;
                display += "Ans";
            }
            outputEq.setText(display);
        }
    }

    public class equalClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (count > 0) {
                for (; count > 0; count--) {
                    equation += ")";
                }
            }

            if (Numbers.startsWithTrig(equation))
                equation = 0 + equation;
            if (display.endsWith("E"))
                equation = equation.substring(0, equation.length() - 6);

            if (equation.equals(""))
                outputEq.setText("");
            else if (equation.contains("infinity")) {
                clear();
                outputEq.setText("Infinity");
            } else {
                try {
                    BigDecimal answer = new Expression(equation).eval();

                    lastAns = String.valueOf(answer.doubleValue());
                    // returns long if answer is a whole number, otherwise return double
                    if (lastAns.substring(lastAns.length() - 2).equals(".0"))
                        lastAns = String.valueOf(answer.longValue());
                } catch (Exception e) {
                    clear();
                    outputEq.setText("Error");
                }
                clear();
                outputAns.setText(lastAns);
            }
        }
    }

    public class factorialClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty()) ;
            else if (Numbers.lastInputIsOperator(equation) || Numbers.lastInputIsFunction(display)) ;
            else {
                if (currNum.endsWith(".")) {
                    currNum = currNum.substring(0, currNum.length() - 1);
                    equation = equation.substring(0, equation.length() - 1);
                }

                try {
                    equation = equation.substring(0, equation.length() - currNum.length());
                    if (Numbers.factorial(Integer.valueOf(currNum)) == 0)
                        equation += "infinity";
                    else
                        equation += String.valueOf(Numbers.factorial(Integer.valueOf(currNum)));
                    display += "!";
                    outputEq.setText(display);
                } catch (Exception e) {
                    clear();
                    outputEq.setText("Error");
                }
            }

            //TODO 2!2!, 243564564! crash
        }
    }

    public class expClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (display.isEmpty()) ;
            else if (display.endsWith(".")) ;
            else if (Character.isDigit(display.charAt(display.length() - 1))) {
                equation = equation.substring(0, display.length() - currNum.length()) + currNum;
                lastNum = currNum;
                currNum = "";
                insertNum("*(10^", false, true, true); //display = E, equation = *(10^
                count++;
            }
            outputEq.setText(display.replaceAll("\\*\\(10\\^", "E"));
        }
    }

    public class sqClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (equation.isEmpty() || equation.endsWith("(")) ;
            else {
                lastNum = currNum;
                currNum = "";
                insertNum("^", false, true, true);
            }
            outputEq.setText(display);
        }
    }

    /**
    * Other Methods
    **/
    public void insertNum(String insert, boolean addCurrNum, boolean addEquation, boolean addDisplay) {
        if (addCurrNum)
            currNum += insert;
        if (addEquation)
            equation += insert;
        if (addDisplay)
            display += insert;
    }

    public void removeChars(int equationX, int displayX) {
        equation = equation.substring(0, equation.length() - equationX);
        display = display.substring(0, display.length() - displayX);
    }

    public void removeCharsAddChars(int equationX, int displayX, String equationAdd, String displayAdd) {
        removeChars(equationX, displayX);
        equation += equationAdd;
        display += displayAdd;
    }

    public void clear() {
        equation = "";
        currNum = "";
        lastNum = "";
        display = "";
        count = 0;
    }

    public void initializeViews() {
        outputEq = (TextView) findViewById(R.id.output_equation);
        outputAns = (TextView) findViewById(R.id.output_answer);
        clr = (Button) findViewById(R.id.btn_clear);
        ans = (Button) findViewById(R.id.btn_ans);
        negative = (Button) findViewById(R.id.btn_int);
        backspace = (Button) findViewById(R.id.btn_backspace);
        open = (Button) findViewById(R.id.btn_paren_open);
        close = (Button) findViewById(R.id.btn_paren_close);
        add = (Button) findViewById(R.id.btn_add);
        minus = (Button) findViewById(R.id.btn_sub);
        multiply = (Button) findViewById(R.id.btn_mult);
        divide = (Button) findViewById(R.id.btn_div);
        percent = (Button) findViewById(R.id.btn_percent);
        zero = (Button) findViewById(R.id.btn_0);
        one = (Button) findViewById(R.id.btn_1);
        two = (Button) findViewById(R.id.btn_2);
        three = (Button) findViewById(R.id.btn_3);
        four = (Button) findViewById(R.id.btn_4);
        five = (Button) findViewById(R.id.btn_5);
        six = (Button) findViewById(R.id.btn_6);
        seven = (Button) findViewById(R.id.btn_7);
        eight = (Button) findViewById(R.id.btn_8);
        nine = (Button) findViewById(R.id.btn_9);
        dot = (Button) findViewById(R.id.btn_decimal);
        equal = (Button) findViewById(R.id.btn_equals);
        factorial = (Button) findViewById(R.id.btn_factorial);
        pi = (Button) findViewById(R.id.btn_pi);
        ee = (Button) findViewById(R.id.btn_e);
        sqRoot = (Button) findViewById(R.id.btn_sq_root);
        sq = (Button) findViewById(R.id.btn_sq);
        sin = (Button) findViewById(R.id.btn_sin);
        cos = (Button) findViewById(R.id.btn_cos);
        tan = (Button) findViewById(R.id.btn_tan);
        exp = (Button) findViewById(R.id.btn_exp);
        ln = (Button) findViewById(R.id.btn_ln);
        log = (Button) findViewById(R.id.btn_log);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currNum", currNum);
        outState.putString("equation", equation);
        outState.putString("lastAns", lastAns);
        outState.putString("display", display);
        outState.putString("lastNum", lastNum);
        outState.putInt("count", count);
    }
}