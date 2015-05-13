package nyc.c4q.sufeiiz.scientificcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;


public class MainActivity extends ActionBarActivity {

    private TextView mainOutput;
    private String equation, current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainOutput = (TextView) findViewById(R.id.main_output);
        if (savedInstanceState == null) {
            mainOutput.setText("");
            equation = "";
            current = "";
        } else {
            mainOutput.setText(savedInstanceState.getString("output"));
        }

        final Button ac = (Button) findViewById(R.id.ac);
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
        final Button add = (Button) findViewById(R.id.add);
        final Button minus = (Button) findViewById(R.id.minus);
        final Button multiply = (Button) findViewById(R.id.multiply);
        final Button divide = (Button) findViewById(R.id.divide);
        final Button module = (Button) findViewById(R.id.module);
        final Button negative = (Button) findViewById(R.id.negative);
        final Button dot = (Button) findViewById(R.id.dot);
        final Button paren = (Button) findViewById(R.id.paren);
        final Button equal = (Button) findViewById(R.id.equal);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "0";
                equation += "0";
                mainOutput.setText(current);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "1";
                equation += "1";
                mainOutput.setText(current);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "2";
                equation += "2";
                mainOutput.setText(current);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "3";
                equation += "3";
                mainOutput.setText(current);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "4";
                equation += "4";
                mainOutput.setText(current);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "5";
                equation += "5";
                mainOutput.setText(current);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "6";
                equation += "6";
                mainOutput.setText(current);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "7";
                equation += "7";
                mainOutput.setText(current);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "8";
                equation += "8";
                mainOutput.setText(current);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current += "9";
                equation += "9";
                mainOutput.setText(current);
            }
        });

        // clear everything
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainOutput.setText("");
                equation = "";
                current = "";
            }
        });

        // for +-*/% if last added input is not a number (is a sign), ignore

        //TODO saved answer??
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Character.isDigit(equation.charAt(equation.length()-1)));
                else {
                    equation += "+";
                    current = "";
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Character.isDigit(equation.charAt(equation.length() - 1)));
                else {
                    equation += "-";
                    current = "";
                }
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Character.isDigit(equation.charAt(equation.length() - 1)));
                else {
                    equation += "*";
                    current = "";
                }
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Character.isDigit(equation.charAt(equation.length()-1)))
                    add.clearFocus();
                else {
                    equation += "/";
                    current = "";
                }
            }
        });
        module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Character.isDigit(equation.charAt(equation.length()-1)));
                else {
                    equation += "%";
                    current = "";
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
                    equation += current;
                    mainOutput.setText(current);
                } else {
                    equation = equation.substring(0, equation.length() - current.length());
                    current = "-" + current;
                    equation += current;
                    mainOutput.setText(current);
                }
            }
        });

        // if number already contains ".", ignore
        // if user omits 0 before ".", include 0
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current.contains("."));
                    // do nothing
                else if (current.equals("")) {
                    current += "0.";
                    equation += "0.";
                    mainOutput.setText(current);
                } else {
                    equation += ".";
                    current += ".";
                    mainOutput.setText(current);
                }
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equation.equals(""))
                    mainOutput.setText("");
                else {
                    BigDecimal answer = new Expression(equation).eval();
                    current = String.valueOf(answer.doubleValue());
                    equation = current;

                    // returns long if answer is a whole number, otherwise return double
                    if (current.substring(current.length()-2).equals(".0"))
                        mainOutput.setText(String.valueOf(answer.longValue()));
                    else
                        mainOutput.setText(current);
                }
            }
        });

        //TODO how to open and close paren?
        paren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equation += "()";
                current = "";
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("output", mainOutput.getText().toString());
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
