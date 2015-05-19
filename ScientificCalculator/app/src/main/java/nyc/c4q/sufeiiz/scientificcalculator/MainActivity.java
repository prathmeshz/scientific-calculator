package nyc.c4q.sufeiiz.scientificcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    private TextView mainOutput, ansOutput;
    private String equation, currNum, lastAns, lastNum, display;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainOutput = (TextView) findViewById(R.id.output_calc);
        ansOutput = (TextView) findViewById(R.id.output_answer);
        if (savedInstanceState == null) {
            mainOutput.setText("");
            ansOutput.setText("");
            display = "";
            equation = "";
            currNum = "";
            lastNum = "";
            lastAns = "0";
            count = 0;
        } else {
            ansOutput.setText(savedInstanceState.getString("lastAns"));
            mainOutput.setText(savedInstanceState.getString("display"));
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

        // Numbers OnClickListener
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "0";
                equation += "0";
                display += "0";
                mainOutput.setText(display);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "1";
                equation += "1";
                display += "1";
                mainOutput.setText(display);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "2";
                equation += "2";
                display += "2";
                mainOutput.setText(display);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "3";
                equation += "3";
                display += "3";
                mainOutput.setText(display);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "4";
                equation += "4";
                display += "4";
                mainOutput.setText(display);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "5";
                equation += "5";
                display += "5";
                mainOutput.setText(display);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "6";
                equation += "6";
                display += "6";
                mainOutput.setText(display);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "7";
                equation += "7";
                display += "7";
                mainOutput.setText(display);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "8";
                equation += "8";
                display += "8";
                mainOutput.setText(display);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display.isEmpty());
                else if (display.charAt(display.length() - 1) == 's') {
                    equation += "*";
                    display += "×";
                }
                currNum += "9";
                equation += "9";
                display += "9";
                mainOutput.setText(display);
            }
        });

        // if number already contains ".", ignore
        // if user omits 0 before ".", include 0
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currNum.contains(".")) ;
                else if (currNum.equals("")) {
                    currNum += "0.";
                    equation += "0.";
                    display += "0.";
                } else {
                    equation += ".";
                    currNum += ".";
                    display += ".";
                }
                mainOutput.setText(display);
            }
        });

        // clear everything
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equation = "";
                currNum = "";
                lastNum = "";
                display = "";
                ansOutput.setText("");
                mainOutput.setText(display);
            }
        });

        // TODO fix backspace for sincostan once that's implemented
        // if equation is empty, do nothing
        // else if the last input was %, convert to decimal to find length of converted num
        // else delete last char in currNum, equation, and display
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else if (display.charAt(display.length() - 1) == '%') {
                    display = display.substring(0, display.length() - 1); // removes "%"

                    // find length of converted decimal and remove from equation; add back current num
                    String converted = Numbers.percentage(currNum, lastNum);
                    equation = equation.substring(0, equation.length() - converted.length());
                    equation += currNum;
                } else {
                    if (currNum.length() > 0)
                        currNum = currNum.substring(0, currNum.length()-1);
                    if (equation.length() > 0)
                        equation = equation.substring(0, equation.length()-1);
                    if (display.length() > 0)
                        display = display.substring(0, display.length()-1);
                }
                mainOutput.setText(display);
            }
        });

        // if equation is empty and current is not empty, add current to equation
        // else if only equation is empty, ignore (needed so there's no out of range error)
        // else if last input was operator or (, replace
        // else just add operator
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() && !lastAns.isEmpty()) {
                    lastNum = lastAns;
                    equation = lastAns + "+";
                    display = lastAns + "+";
                } else if (equation.isEmpty());
                else if (Numbers.lastInputIsOperator(equation)) {
                    equation = equation.substring(0, equation.length()-1) + "+";
                    display = display.substring(0, display.length()-1) + "+";
                } else {
                    equation += "+";
                    lastNum = currNum;
                    currNum = "";
                    display += "+";
                }
                mainOutput.setText(display);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() && !lastAns.isEmpty()) {
                    lastNum = lastAns;
                    equation = lastAns + "-";
                    display = lastAns + "-";
                } else if (equation.isEmpty());
                else if (Numbers.lastInputIsOperator(equation)) {
                    equation = equation.substring(0, equation.length()-1) + "-";
                    display = display.substring(0, display.length()-1) + "-";
                } else {
                    equation += "-";
                    lastNum = currNum;
                    currNum = "";
                    display += "-";
                }
                mainOutput.setText(display);
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() && !lastAns.isEmpty()) {
                    lastNum = lastAns;
                    equation = lastAns + "*";
                    display = lastAns + "×";
                } else if (equation.isEmpty());
                else if (Numbers.lastInputIsOperator(equation)) {
                    equation = equation.substring(0, equation.length()-1) + "*";
                    display = display.substring(0, display.length()-1) + "×";
                } else {
                    equation += "*";
                    lastNum = currNum;
                    currNum = "";
                    display += "×";
                }
                mainOutput.setText(display);
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() && !lastAns.isEmpty()) {
                    lastNum = lastAns;
                    equation = lastAns + "/";
                    display = lastAns + "÷";
                } else if (equation.isEmpty());
                else if (Numbers.lastInputIsOperator(equation)) {
                    equation = equation.substring(0, equation.length()-1) + "/";
                    display = display.substring(0, display.length()-1) + "÷";
                } else {
                    equation += "/";
                    lastNum = currNum;
                    currNum = "";
                    display += "÷";
                }
                mainOutput.setText(display);
            }
        });

        // if equation is empty, ignore
        // else if last input was operator or ( or %, ignore
        // else convert percentage to number
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else if (Numbers.lastInputIsOperator(equation) ||
                        Numbers.lastInputIsPercent(equation));
                else {
                    equation = equation.substring(0, equation.length() - currNum.length());
                    String converted = Numbers.percentage(currNum, lastNum);

                    equation += converted;
                    display += "%";
                    mainOutput.setText(display);
                }
            }
        });

        // if equation is empty, add - accordingly
        // else if last input was %, find length of converted number and add - accordingly
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
                mainOutput.setText(display);
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
                        equation.charAt(equation.length() - 1) == ')') {
                    equation += "*(";
                    display += "×(";
                } else {
                    display += "(";
                    equation += "(";
                }
                count++;
                mainOutput.setText(display);
            }
        });

        // decrease count for every closed parentheses
        // if equation if empty or count is less than zero or last input was operator, ignore
        // else just close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() || count <= 0 ||
                        Numbers.lastInputIsOperator(equation));
                else {
                    equation += ")";
                    display += ")";
                    count--;
                    mainOutput.setText(display);
                }
            }
        });

        // save last answer (whenever = is clicked)
        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastNum = currNum;
                currNum = lastAns;
                equation += lastAns;
                display += "Ans";
                mainOutput.setText(display);
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
                if (equation.equals(""))
                    mainOutput.setText("");
                else {
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
                        mainOutput.setText("Error");
                    }

                    lastNum = "";
                    currNum = "";
                    equation = "";
                    display = "";
                    ansOutput.setText(lastAns);
                }
            }
        });
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
