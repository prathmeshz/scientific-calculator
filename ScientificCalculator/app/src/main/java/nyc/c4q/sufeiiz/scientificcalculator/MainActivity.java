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

        // Numbers OnClickListener
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

        // if number already contains ".", ignore
        // if user omits 0 before ".", include 0
        dot.setOnClickListener(new View.OnClickListener() {
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
        });

        // clear everything
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastAns = "0";
                clear();
                outputAns.setText("");
                outputEq.setText(display);
            }
        });

        // if equation is empty, do nothing
        // else if the last input was %, convert to decimal to find length of converted num
        // else delete last char in currNum, equation, and display
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() || display.isEmpty()) ;
                else if (display.charAt(display.length() - 1) == '%') {
                    display = display.substring(0, display.length() - 1); // removes "%"

                    // find length of converted decimal and remove from equation; add back current num
                    String converted = Numbers.percentage(currNum, lastNum);
                    equation = equation.substring(0, equation.length() - converted.length());
                    equation += currNum;
                } else if (display.charAt(display.length() - 1) == '!') {
                    display = display.substring(0, display.length() - 1); // removes "!"

                    String converted = String.valueOf(Numbers.factorial(Integer.valueOf(currNum)));
                    equation = equation.substring(0, equation.length() - converted.length());
                    equation += currNum;
                } else if (display.charAt(display.length() - 1) == 'e' ||
                        display.charAt(display.length() - 1) == 'π') {
                    equation = equation.substring(0, equation.length() - 13);
                    currNum = "";
                    display = display.substring(0, display.length() - 1);
                } else if (display.charAt(display.length() - 1) == 's') {
                    equation = equation.substring(0, equation.length() - currNum.length());
                    currNum = "";
                    display = display.substring(0, display.length() - 3);
                } else if (display.endsWith("sin(") || display.endsWith("cos(") ||
                        display.endsWith("tan(") || display.endsWith("log(")) {
                    equation = equation.substring(0, equation.length() - 4);
                    display = display.substring(0, display.length() - 4);
                    count--;
                } else if (display.endsWith("ln(")) {
                    equation = equation.substring(0, equation.length() - 3);
                    display = display.substring(0, display.length() - 3);
                    count--;
                } else if (display.endsWith(")")) {
                    equation = equation.substring(0, equation.length() - 1);
                    display = display.substring(0, display.length() - 1);
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
        });

        // if equation is empty, ignore
        // else if last input was operator or ( or %, ignore
        // else convert percentage to number
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty()) ;
                else if (display.charAt(display.length() - 1) == 'E' ||
                        display.charAt(display.length() - 1) == '^') ;
                else if (Numbers.lastInputIsOperator(equation) ||
                        display.charAt(display.length() - 1) == '%');
                else {
                    equation = equation.substring(0, equation.length() - currNum.length());
                    String converted = Numbers.percentage(currNum, lastNum);

                    equation += converted;
                    display += "%";
                    outputEq.setText(display);
                }
            }
        });

        // if equation is empty, add - accordingly
        // else if last input was %, find length of converted number and add - accordingly
        // else if last input was e or pi, find length of converted number and add - accordingly
        // else if last input was ans, find length of converted number and add - accordingly
        // else add - accordingly
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty()) {
                    if (currNum.contains("-"))
                        currNum = currNum.substring(1);
                    else
                        currNum = "-" + currNum;
                    insertNum(currNum, false, true, true);
                } else if (Numbers.lastInputIsPercent(display)) {
                    String converted = Numbers.percentage(currNum, lastNum);
                    display = display.substring(0, display.length() - currNum.length() - 1);
                    equation = equation.substring(0, equation.length() - converted.length());
                    if (currNum.contains("-")) {
                        currNum = currNum.substring(1);
                        display += currNum + "%";
                        equation += converted.substring(1);
                    } else {
                        currNum = "-" + currNum;
                        display += currNum + "%";
                        equation += "-" + converted;
                    }
                } else if (display.charAt(display.length() - 1) == '!') {
                    String converted = String.valueOf(Numbers.factorial(Integer.valueOf(currNum)));
                    display = display.substring(0, display.length() - currNum.length() - 1);
                    equation = equation.substring(0, equation.length() - converted.length());
                    if (currNum.contains("-")) {
                        currNum = currNum.substring(1);
                        display += currNum + "!";
                        equation += converted.substring(1);
                    } else {
                        currNum = "-" + currNum;
                        display += currNum + "!";
                        equation += "-" + converted;
                    }
                } else if (display.charAt(display.length() - 1) == 'e' ||
                        display.charAt(display.length() - 1) == 'π') {
                    char constant = display.charAt(display.length() - 1);
                    if (currNum.contains("-")) {
                        currNum = currNum.substring(1);
                        equation = equation.substring(0, equation.length() - 14) + currNum;
                        display = display.substring(0, display.length() - 2) + constant;
                    } else {
                        currNum = "-" + currNum;
                        equation = equation.substring(0, equation.length() - 13) + currNum;
                        display = display.substring(0, display.length() - 1) + "-" + constant;
                    }
                } else if (display.charAt(display.length() - 1) == 's') {
                    if (currNum.equals("0")) {
                        display = display.substring(0, display.length() - 3) + "-" + "Ans";
                    } else if (currNum.contains("-")) {
                        currNum = currNum.substring(1);
                        equation = equation.substring(0, equation.length() - currNum.length() - 1) + currNum;
                        display = display.substring(0, display.length() - 4) + "Ans";
                    } else {
                        currNum = "-" + currNum;
                        equation = equation.substring(0, equation.length() - currNum.length() + 1) + currNum;
                        display = display.substring(0, display.length() - 3) + "-" + "Ans";
                    }
                } else if (display.charAt(display.length() - 1) == '^') {
                    if (currNum.contains("-")) {
                        currNum = currNum.substring(1);
                        display = display.substring(0, display.length() - currNum.length() - 1) + currNum;
                        equation = equation.substring(0, equation.length() - currNum.length() - 1) + currNum;
                    } else {
                        currNum = "-" + currNum;
                        display = display.substring(0, display.length() - currNum.length() + 1) + currNum;
                        equation = equation.substring(0, equation.length() - currNum.length() + 1) + currNum;
                    }
                } else if (display.charAt(display.length() - 1) == '^') {
                    if (currNum.contains("-")) {
                        currNum = currNum.substring(1);
                        display = display.substring(0, display.length() - currNum.length() - 1) + currNum;
                        equation = equation.substring(0, equation.length() - currNum.length() - 1) + currNum;
                    } else {
                        currNum = "-" + currNum;
                        display = display.substring(0, display.length() - currNum.length() + 1) + currNum;
                        equation = equation.substring(0, equation.length() - currNum.length() + 1) + currNum;
                    }
                } else {
                    if (currNum.contains("-")) {
                        equation = equation.substring(0, equation.length() - currNum.length());
                        display = display.substring(0, display.length() - currNum.length());
                        currNum = currNum.substring(1);
                    } else {
                        equation = equation.substring(0, equation.length() - currNum.length());
                        display = display.substring(0, display.length() - currNum.length());
                        currNum = "-" + currNum;
                    }
                    equation += currNum;
                    display += currNum;
                }
                outputEq.setText(display);
            }
        });

        // increase count for every opened parentheses
        // if equation if empty, open parentheses
        // else if last input was a number or last input was ), add multiply before open
        // else just open
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty())
                    insertNum("(", false, true, true);
                else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                        equation.charAt(equation.length() - 1) == ')' ||
                        Numbers.lastInputIsConstant(display))
                    insertNum("*(", false, true, true);
                else
                    insertNum("(", false, true, true);
                lastAns = currNum;
                currNum = "";
                count++;
                outputEq.setText(display);
            }
        });

        // decrease count for every closed parentheses
        // if equation if empty or count is less than zero or last input was operator, ignore
        // else just close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() || count <= 0 ||
                        Numbers.lastInputIsOperator(equation)) ;
                else {
                    insertNum(")", false, true, true);
                    lastAns = currNum;
                    currNum = "";
                    count--;
                    outputEq.setText(display);
                }
            }
        });

        // save last answer (whenever = is clicked)
        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastNum = currNum;
                currNum = lastAns;
                if (lastAns.isEmpty()) ;
                else if (display.isEmpty()) {
                    equation += lastAns;
                    display += "Ans";
                } else if (Numbers.lastInputIsConstant(display) ||
                        Character.isDigit(display.charAt(display.length() - 1))) {
                    equation += "*" + lastAns;
                    display += "×Ans";
                } else {
                    equation += lastAns;
                    display += "Ans";
                }
                outputEq.setText(display);
            }
        });

        // if parentheses were not closed properly, close them
        // else if last input was operator, display error message
        // else parse equation for answer
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    for (; count > 0; count--) {
                        equation += ")";
                    }
                }

                if (equation.startsWith("-LOG") || equation.startsWith("-LOG10") ||
                        equation.startsWith("-SIN") || equation.startsWith("-COS") ||
                        equation.startsWith("-TAN"))
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
        });

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

            // constants clickListener
            pi.setOnClickListener(new constantsClickListener('π'));
            ee.setOnClickListener(new constantsClickListener('e'));
            sin.setOnClickListener(new trigClickListener("SIN("));
            cos.setOnClickListener(new trigClickListener("COS("));
            tan.setOnClickListener(new trigClickListener("TAN("));
            ln.setOnClickListener(new logClickListener("LOG(")); //display = ln(), equation = log()
            log.setOnClickListener(new logClickListener("LOG10(")); // display = log(), equation = log10()
            sqRoot.setOnClickListener(new logClickListener("SQRT(")); //display = √(), equations = SQRT()

            // if equation if empty, ignore
            // else last operator was an operator, ignore
            // else try factorial calculation, catch error
            factorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (equation.isEmpty()) ;
                    else if (Numbers.lastInputIsOperator(equation) ||
                            Numbers.lastInputIsFunction(display)) ;
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
            });

            exp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (display.isEmpty()) ;
                    else if (display.charAt(display.length() - 1) == '.') ;
                    else if (Character.isDigit(display.charAt(display.length() - 1))) {
                        equation = equation.substring(0, display.length() - currNum.length()) + currNum;
                        lastNum = currNum;
                        currNum = "";
                        insertNum("*(10^", false, true, true); //display = E, equation = *(10^
                        count++;
                    }
                    outputEq.setText(display);
                }
            });

            sq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (equation.isEmpty() ||
                            equation.charAt(equation.length() - 1) == '(') ;
                    else {
                        lastNum = currNum;
                        currNum = "";
                        insertNum("^", false, true, true);
                    }
                    outputEq.setText(display);
                }
            });
        }
    }

    /** ON CLICK LISTENERS **/
    private class numClickListener implements View.OnClickListener {
        private int num;
        public numClickListener(int num) {
            this.num = num;
        }

        @Override
        public void onClick(View v) {
            if (display.isEmpty()) ;
            else if (Numbers.lastInputIsConstant(display)) {
                insertNum("*", false, true, true);
            }
            insertNum(num + "", true, true, true);
            outputEq.setText(display);
        }
    }

    // if equation is empty and current is not empty, add current to equation
    // else if only equation is empty, ignore (needed so there's no out of range error)
    // else if last input was operator or (, replace
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
            } else if (equation.isEmpty() || equation.endsWith("LOG(") || equation.endsWith("LOG10(") || equation.endsWith("SIN(") ||
                    equation.endsWith("COS(") || equation.endsWith("TAN(") || equation.endsWith("^")) ;
            else if (Numbers.lastInputIsOperator(equation)) {
                equation = equation.substring(0, equation.length() - 1) + operator;
                display = display.substring(0, display.length() - 1) + operator;
            } else {
                lastNum = currNum;
                currNum = "";
                insertNum(operator + "", false, true, true);
            }
            outputEq.setText(display);
        }
    }

    class constantsClickListener implements View.OnClickListener {
        private char constant;
        constantsClickListener(char constant) {
            this.constant = constant;
        }

        @Override
        public void onClick(View v) {
            if (constant == 'π')
                currNum = String.valueOf(π);
            else
                currNum = String.valueOf(e);

            if (display.isEmpty()) {
                equation += currNum;
                display += constant;
            } else if (Numbers.lastInputIsConstant(display) || Character.isDigit(display.charAt(display.length() - 1)) ||
                    display.charAt(display.length() - 1) == ')') {
                equation += "*" + currNum;
                display += "×" + constant;
            } else {
                equation += currNum;
                display += constant;
            }
            outputEq.setText(display);
        }
    }

    class trigClickListener implements View.OnClickListener {
        private String trig;
        trigClickListener(String trig) {
            this.trig = trig;
        }

        @Override
        public void onClick(View v) {
            if (degMode) {
                if (equation.isEmpty())
                    insertNum(trig, false, true, true);
                else if (display.charAt(display.length() - 1) == 'E' ||
                        display.charAt(display.length() - 1) == '^' ||
                        display.charAt(display.length() - 1) == '.') ;
                else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                        equation.charAt(equation.length() - 1) == ')' ||
                        Numbers.lastInputIsConstant(display))
                    insertNum("*" + trig, false, true, true);
                else
                    insertNum(trig, false, true, true);
            } else {
                if (equation.isEmpty()) {
                    equation += "RAD(" + trig;
                    display += trig;
                } else if (display.charAt(display.length() - 1) == 'E' ||
                        display.charAt(display.length() - 1) == '^' ||
                        display.charAt(display.length() - 1) == '.') ;
                else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                        equation.charAt(equation.length() - 1) == ')' ||
                        Numbers.lastInputIsConstant(display)) {
                    equation += "*RAD(" + trig;
                    display += "×" + trig;
                } else {
                    equation += "RAD(" + trig;
                    display += trig;
                }
                count++;
            }
            count++;
            outputEq.setText(display);
        }
    }

    class logClickListener implements View.OnClickListener {
        private String input;
        logClickListener(String input) {
            this.input = input;
        }

        @Override
        public void onClick(View v) {
            if (equation.isEmpty() || display.isEmpty())
                insertNum(input, false, true, true);
            else if (display.charAt(display.length() - 1) == 'E' ||
                    display.charAt(display.length() - 1) == '^' ||
                    display.charAt(display.length() - 1) == '.') ;
            else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                    equation.charAt(equation.length() - 1) == ')' ||
                    Numbers.lastInputIsConstant(display))
                insertNum("*" + input, false, true, true);
            else
                insertNum(input, false, true, true);

            count++;
            outputEq.setText(display);
        }
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

    public void insertNum(String insert, boolean addCurrNum, boolean addEquation, boolean addDisplay) {
        if (addCurrNum)
            currNum += insert;
        if (addEquation)
            equation += insert;
        if (addDisplay)
            display += insert;
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
}