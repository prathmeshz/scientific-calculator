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

    private TextView mainOutput;
    private String equation, current, lastAns, lastCurr, display;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainOutput = (TextView) findViewById(R.id.main_output);
        if (savedInstanceState == null) {
            mainOutput.setText("");
            display = "";
            equation = "";
            current = "";
            lastCurr = "";
            lastAns = "";
            count = 0;
        } else {
            mainOutput.setText(savedInstanceState.getString("current"));
            display = savedInstanceState.getString("display");
            equation = savedInstanceState.getString("equation");
            current = savedInstanceState.getString("current");
            lastCurr = savedInstanceState.getString("lastCurr");
            lastAns = savedInstanceState.getString("lastAns");
            count = savedInstanceState.getInt("count");
        }

        final Button clr = (Button) findViewById(R.id.clr);
        final Button ans = (Button) findViewById(R.id.ans);
        final Button negative = (Button) findViewById(R.id.negative);
        final Button backspace = (Button) findViewById(R.id.backspace);
        final Button open = (Button) findViewById(R.id.open);
        final Button close = (Button) findViewById(R.id.close);
        final Button add = (Button) findViewById(R.id.add);
        final Button minus = (Button) findViewById(R.id.minus);
        final Button multiply = (Button) findViewById(R.id.multiply);
        final Button divide = (Button) findViewById(R.id.divide);
        final Button percent = (Button) findViewById(R.id.percent);
        final Button zero = (Button) findViewById(R.id.zero);
        final Button one = (Button) findViewById(R.id.one);
        final Button two = (Button) findViewById(R.id.two);
        final Button three = (Button) findViewById(R.id.three);
        final Button four = (Button) findViewById(R.id.four);
        final Button five = (Button) findViewById(R.id.five);
        final Button six = (Button) findViewById(R.id.six);
        final Button seven = (Button) findViewById(R.id.seven);
        final Button eight = (Button) findViewById(R.id.eight);
        final Button nine = (Button) findViewById(R.id.nine);
        final Button dot = (Button) findViewById(R.id.dot);
        final Button equal = (Button) findViewById(R.id.equal);

        // Numbers OnClickListener
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "0";
                equation += "0";
                display += "0";
                mainOutput.setText(display);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "1";
                equation += "1";
                display += "1";
                mainOutput.setText(display);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "2";
                equation += "2";
                display += "2";
                mainOutput.setText(display);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "3";
                equation += "3";
                display += "3";
                mainOutput.setText(display);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "4";
                equation += "4";
                display += "4";
                mainOutput.setText(display);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "5";
                equation += "5";
                display += "5";
                mainOutput.setText(display);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "6";
                equation += "6";
                display += "6";
                mainOutput.setText(display);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "7";
                equation += "7";
                display += "7";
                mainOutput.setText(display);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "8";
                equation += "8";
                display += "8";
                mainOutput.setText(display);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "9";
                equation += "9";
                display += "9";
                mainOutput.setText(display);
            }
        });

        // clear everything
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equation = "";
                current = "";
                lastCurr = "";
                display = "";
                mainOutput.setText(display);
            }
        });
        // TODO fix backspace percentage
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else {
                    current = current.substring(0, current.length()-1);
                    display = current.substring(0, display.length()-1);
                    mainOutput.setText(display);
                }
            }
        });
        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCurr = current;
                current = lastAns;
                equation += lastAns + "*";
                display += lastAns;
                mainOutput.setText(display);
            }
        });

        // for +-*/% if last added input is not a number (is a sign), ignore
        // if last input was "(", ignore
        final char[] operators = {'+', '-', '×', '÷', '%', '('};
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else if (Arrays.asList(operators).contains(equation.charAt(equation.length() - 1))) {
                    equation = equation.substring(0, equation.length()-1) + "+";
                    display = display.substring(0, display.length()-1) + "+";
                } else {
                    equation += "+";
                    lastCurr = current;
                    current = "";
                    display += "+";
                }
                mainOutput.setText(display);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else if (Arrays.asList(operators).contains(equation.charAt(equation.length() - 1))) {
                    equation = equation.substring(0, equation.length()-1) + "-";
                    display = display.substring(0, display.length()-1) + "-";
                }
                else {
                    equation += "-";
                    lastCurr = current;
                    current = "";
                    display += "-";
                }
                mainOutput.setText(display);
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else if (Arrays.asList(operators).contains(equation.charAt(equation.length() - 1))) {
                    equation = equation.substring(0, equation.length()-1) + "*";
                    display = display.substring(0, display.length()-1) + "×";
                }
                else {
                    equation += "*";
                    lastCurr = current;
                    current = "";
                    display += "×";
                }
                mainOutput.setText(display);
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty());
                else if (Arrays.asList(operators).contains(equation.charAt(equation.length() - 1))) {
                    equation = equation.substring(0, equation.length()-1) + "/";
                    display = display.substring(0, display.length()-1) + "÷";
                }
                else {
                    equation += "/";
                    lastCurr = current;
                    current = "";
                    display += "÷";
                }
                mainOutput.setText(display);
            }
        });
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty()) ;
                else if (Arrays.asList(operators).contains(equation.charAt(equation.length() - 1)));
                else {
                    equation = equation.substring(0, equation.length() - current.length());
                    double newCurr = (Double.valueOf(current) / 100);

                    if (lastCurr.isEmpty()) {
                        current = String.valueOf(newCurr);
                    } else {
                        newCurr = Double.valueOf(lastCurr) * newCurr;
                        current = String.valueOf(newCurr);
                    }

                    lastCurr = current;
                    equation += current;
                    display += "%";
                    mainOutput.setText(display);
                }
            }
        });

        // if current is negative, turn it to a positive
        // if current is positive, add negative sign to the front of the last input number
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.contains("-")) {
                    equation = equation.substring(0, equation.length() - current.length());
                    current = current.substring(1);
                } else {
                    equation = equation.substring(0, equation.length() - current.length());
                    current = "-" + current;
                }
                equation += current;
                display += current;
                mainOutput.setText(display);
            }
        });

        // if number already contains ".", ignore
        // if user omits 0 before ".", include 0
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.contains(".")) ;
                else if (current.equals("")) {
                    current += "0.";
                    equation += "0.";
                    display += "0.";
                } else {
                    equation += ".";
                    current += ".";
                    display += ".";
                }
                mainOutput.setText(display);
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        equation += ")";
                    }
                }
                if (equation.equals(""))
                    mainOutput.setText("");
                else {
                    BigDecimal answer = new Expression(equation).eval();
                    lastAns = String.valueOf(answer.doubleValue());

                    // returns long if answer is a whole number, otherwise return double
                    if (lastAns.substring(lastAns.length() - 2).equals(".0"))
                        lastAns = String.valueOf(answer.longValue());

                    lastCurr = "";
                    current = "";
                    equation = "";
                    display = "";
                    mainOutput.setText(lastAns);
                }
            }
        });

        //TODO open close?
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
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.isEmpty() || count <= 0 ||
                        Arrays.asList(operators).contains(equation.charAt(equation.length() - 1)));
                else {
                    equation += ")";
                    display += ")";
                    count--;
                    mainOutput.setText(display);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("current", current);
        outState.putString("equation", equation);
        outState.putString("lastAns", lastAns);
        outState.putString("display", display);
        outState.putString("lastCurr", lastCurr);
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
