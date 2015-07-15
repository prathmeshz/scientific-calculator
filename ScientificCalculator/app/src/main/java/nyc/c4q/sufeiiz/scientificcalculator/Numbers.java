package nyc.c4q.sufeiiz.scientificcalculator;

import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by sufeizhao on 5/5/15.
 */
public class Numbers extends MainActivity{

    // returns String of the percentage as a decimal
    public static String percentage(String num, String lastNum) {
        String converted;
        double newCurr = (Double.valueOf(num) / 100);

        if (lastNum.isEmpty()) {
            converted = String.valueOf(newCurr);
        } else {
            newCurr = Double.valueOf(lastNum) * newCurr;
            converted = String.valueOf(newCurr);
        }

        return converted;
    }

    // returns true if last input was +, -, *, /, (
    public static boolean lastInputIsOperator(String equation) {
        String[] operators = {"+", "-", "*", "/", "("};
        return Arrays.asList(operators).contains(String.valueOf(equation.charAt(equation.length() - 1)));
    }

    public static boolean lastInputIsConstant(String display) {
        return display.charAt(display.length() - 1) == 's' ||
                display.charAt(display.length() - 1) == 'π' ||
                display.charAt(display.length() - 1) == 'e';
    }

    public static boolean lastInputIsFunction(String display) {
        return display.charAt(display.length() - 1) == 'E' ||
                display.charAt(display.length() - 1) == '^' ||
                display.charAt(display.length() - 1) == '%';
    }

    public static boolean lastInputIsTrig(String display) {
        return display.endsWith("sin(") || display.endsWith("cos(") ||
                display.endsWith("tan(") || display.endsWith("log(");
    }

    public static boolean startsWithTrig(String display) {
        return display.startsWith("-ln") || display.startsWith("-log") ||
                display.startsWith("-sin") || display.startsWith("-cos") ||
                display.startsWith("-tan");
    }

    public static boolean equalsNontrig(String trig) {
        return trig.equals("LOG(") || trig.equals("LOG10(") || trig.equals("SQRT(");
    }

    public static boolean lastInputIsDigit(String display) {
        return Character.isDigit(display.charAt(display.length() - 1));
    }

    public static boolean lastInputIsError(String display) {
        return display.endsWith("^") || display.endsWith("ln(") || display.endsWith("√(");
    }

    // returns factorial answer
    static boolean negative = false;
    public static long factorial(int x) {
        if (x < 0) {
            x *= -1;
            negative = true;
        }
        if (x == 1) {
            if (negative) {
                return -1;
            } else {
                return 1;
            }
        } else if (x == 0) {
            return 1;
        } else {
            return x * factorial(x-1);
        }
    }
}