package nyc.c4q.sufeiiz.scientificcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.math.BigDecimal;

public class MainActivity extends ActionBarActivity {

    private TextView outputEq, outputAns, mode;
    private String equation, currNum, lastAns, lastNum, display;
    private int count;
    private double π = 3.14159265359, e = 2.71828182846;
    private boolean degMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputEq = (TextView) findViewById(R.id.output_equation);
        outputAns = (TextView) findViewById(R.id.output_answer);
        if (savedInstanceState == null) {
            outputEq.setText("");
            outputAns.setText("");
            display = "";
            equation = "";
            currNum = "";
            lastNum = "";
            lastAns = "0";
            count = 0;
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

        final Button clr = (Button) findViewById(R.id.btn_clear);
        final Button ans = (Button) findViewById(R.id.btn_ans);
        final Button negative = (Button) findViewById(R.id.btn_int);
        final Button backspace = (Button) findViewById(R.id.btn_backspace);
        final Button open = (Button) findViewById(R.id.btn_paren_open);
        final Button close = (Button) findViewById(R.id.btn_paren_close);
        final Button add = (Button) findViewById(R.id.btn_add);
        final Button minus = (Button) findViewById(R.id.btn_sub);
        final Button multiply = (Button) findViewById(R.id.btn_mult);
        final Button divide = (Button) findViewById(R.id.btn_div);
        final Button percent = (Button) findViewById(R.id.btn_percent);
        final Button zero = (Button) findViewById(R.id.btn_0);
        final Button one = (Button) findViewById(R.id.btn_1);
        final Button two = (Button) findViewById(R.id.btn_2);
        final Button three = (Button) findViewById(R.id.btn_3);
        final Button four = (Button) findViewById(R.id.btn_4);
        final Button five = (Button) findViewById(R.id.btn_5);
        final Button six = (Button) findViewById(R.id.btn_6);
        final Button seven = (Button) findViewById(R.id.btn_7);
        final Button eight = (Button) findViewById(R.id.btn_8);
        final Button nine = (Button) findViewById(R.id.btn_9);
        final Button dot = (Button) findViewById(R.id.btn_decimal);
        final Button equal = (Button) findViewById(R.id.btn_equals);

        // Scientific buttons
        final Button factorial = (Button) findViewById(R.id.btn_factorial);
        final Button pi = (Button) findViewById(R.id.btn_pi);
        final Button e = (Button) findViewById(R.id.btn_e);
        final Button sqRoot = (Button) findViewById(R.id.btn_sq_root);
        final Button sq = (Button) findViewById(R.id.btn_sq);
        final Button sin = (Button) findViewById(R.id.btn_sin);
        final Button cos = (Button) findViewById(R.id.btn_cos);
        final Button tan = (Button) findViewById(R.id.btn_tan);
        final Button exp = (Button) findViewById(R.id.btn_exp);
        final Button ln = (Button) findViewById(R.id.btn_ln);
        final Button log = (Button) findViewById(R.id.btn_log);

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

        // operator clickListner
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
                else if (equation.isEmpty()) {
                    currNum += "0.";
                    equation += "0.";
                    display += "0.";
                } else {
                    if (currNum.equals("") || Numbers.lastInputIsOperator(equation)) {
                        currNum += "0.";
                        equation += "0.";
                        display += "0.";
                    } else {
                        equation += ".";
                        currNum += ".";
                        display += ".";
                    }
                }
                outputEq.setText(display);
            }
        });

        // clear everything
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equation = "";
                currNum = "";
                lastAns = "";
                lastNum = "";
                display = "";
                count = 0;
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
                    equation += currNum;
                    display += currNum;
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
                if (equation.isEmpty()) {
                    equation += "(";
                    display += "(";
                } else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                        equation.charAt(equation.length() - 1) == ')' ||
                        Numbers.lastInputIsConstant(display)) {
                    equation += "*(";
                    display += "×(";
                } else {
                    display += "(";
                    equation += "(";
                }
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
                    equation += ")";
                    display += ")";
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
                    lastNum = "";
                    currNum = "";
                    equation = "";
                    display = "";
                    count = 0;
                    outputEq.setText("Infinity");
                } else {
                    try {
                        BigDecimal answer = new Expression(equation).eval();

                        lastAns = String.valueOf(answer.doubleValue());
                        // returns long if answer is a whole number, otherwise return double
                        if (lastAns.substring(lastAns.length() - 2).equals(".0"))
                            lastAns = String.valueOf(answer.longValue());
                    } catch (Exception e) {
                        equation = "";
                        currNum = "";
                        lastNum = "";
                        display = "";
                        count = 0;
                        outputEq.setText("Error");
                    }
                    lastNum = "";
                    currNum = "";
                    equation = "";
                    display = "";
                    count = 0;
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
                            equation = "";
                            currNum = "";
                            lastNum = "";
                            display = "";
                            outputEq.setText("Error");
                        }
                    }
                }
            });

            // constants clickListener
            pi.setOnClickListener(new constantsClickListener('π'));
            e.setOnClickListener(new constantsClickListener('e'));

            // trig clickListener
            sin.setOnClickListener(new trigClickListener("SIN("));
            cos.setOnClickListener(new trigClickListener("COS("));
            tan.setOnClickListener(new trigClickListener("TAN("));

            ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (equation.isEmpty() || display.isEmpty()) {
                        equation += "LOG(";
                        display += "ln(";
                    }  else if (display.charAt(display.length() - 1) == 'E' ||
                            display.charAt(display.length() - 1) == '^' ||
                            display.charAt(display.length() - 1) == '.') ;
                    else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                            equation.charAt(equation.length() - 1) == ')' ||
                            Numbers.lastInputIsConstant(display)) {
                        equation += "*LOG(";
                        display += "×ln(";
                    } else {
                        equation += "LOG(";
                        display += "ln(";
                    }
                    count++;
                    outputEq.setText(display);
                }
            });
            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (equation.isEmpty()) {
                        equation += "LOG10(";
                        display += "log(";
                    }  else if (display.charAt(display.length() - 1) == 'E' ||
                            display.charAt(display.length() - 1) == '^' ||
                            display.charAt(display.length() - 1) == '.') ;
                    else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                            equation.charAt(equation.length() - 1) == ')' ||
                            Numbers.lastInputIsConstant(display)) {
                        equation += "*LOG10(";
                        display += "×log(";
                    } else {
                        equation += "LOG10(";
                        display += "log(";
                    }
                    count++;
                    outputEq.setText(display);
                }
            });
            sqRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (equation.isEmpty()) {
                        equation += "SQRT(";
                        display += "√(";
                    }  else if (display.charAt(display.length() - 1) == 'E' ||
                            display.charAt(display.length() - 1) == '^' ||
                            display.charAt(display.length() - 1) == '.') ;
                    else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                            equation.charAt(equation.length() - 1) == ')' ||
                            Numbers.lastInputIsConstant(display)) {
                        equation += "*SQRT(";
                        display += "×√(";
                    } else {
                        equation += "SQRT(";
                        display += "√(";
                    }
                    count++;
                    outputEq.setText(display);
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
                        equation += "*(10^";
                        display += "E";
                        count++;
                    }
                    outputEq.setText(display);

                    //2*(10^5)
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
                        equation += "^";
                        display += "^";
                    }
                    outputEq.setText(display);
                }
            });
        }
    }

    private class numClickListener implements View.OnClickListener {
        private int num;
        public numClickListener(int num) {
            this.num = num;
        }

        @Override
        public void onClick(View v) {
            if (display.isEmpty()) ;
            else if (Numbers.lastInputIsConstant(display)) {
                equation += "*";
                display += "×";
            }
            currNum += num;
            equation += num;
            display += num;
            outputEq.setText(display);
        }
    }

    // if equation is empty and current is not empty, add current to equation
    // else if only equation is empty, ignore (needed so there's no out of range error)
    // else if last input was operator or (, replace
    // else just add operator
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
            } else if (equation.isEmpty() || equation.endsWith("LOG(") ||
                    equation.endsWith("LOG10(") || equation.endsWith("SIN(") ||
                    equation.endsWith("COS(") || equation.endsWith("TAN(") ||
                    equation.endsWith("^")) ;
            else if (Numbers.lastInputIsOperator(equation)) {
                equation = equation.substring(0, equation.length() - 1) + operator;
                display = display.substring(0, display.length() - 1) + operator;
            } else {
                equation += operator;
                lastNum = currNum;
                currNum = "";
                display += operator;
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
            currNum = String.valueOf(constant);
            if (display.isEmpty()) {
                equation += currNum;
                display += constant;
            } else if (Numbers.lastInputIsConstant(display) ||
                    Character.isDigit(display.charAt(display.length() - 1)) ||
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
                if (equation.isEmpty()) {
                    equation += trig;
                    display += trig;
                } else if (display.charAt(display.length() - 1) == 'E' ||
                        display.charAt(display.length() - 1) == '^' ||
                        display.charAt(display.length() - 1) == '.') ;
                else if (Character.isDigit(equation.charAt(equation.length() - 1)) ||
                        equation.charAt(equation.length() - 1) == ')' ||
                        Numbers.lastInputIsConstant(display)) {
                    equation += "*" + trig;
                    display += "×" + trig;
                } else {
                    equation += trig;
                    display += trig;
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}