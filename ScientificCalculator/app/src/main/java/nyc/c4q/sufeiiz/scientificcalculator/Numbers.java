package nyc.c4q.sufeiiz.scientificcalculator;

import android.view.View;
import android.widget.TextView;

/**
 * Created by sufeizhao on 5/5/15.
 */
public class Numbers {

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int buttonId = v.getId();
            String num = null;

            switch (buttonId) {
                case R.id.zero:
                    num = "0";
                    break;
                case R.id.one:
                    num = "1";
                    break;
                case R.id.two:
                    num = "2";
                    break;
                case R.id.three:
                    num = "3";
                    break;
                case R.id.four:
                    num = "4";
                    break;
                case R.id.five:
                    num = "5";
                    break;
                case R.id.six:
                    num = "6";
                    break;
                case R.id.seven:
                    num = "7";
                    break;
                case R.id.eight:
                    num = "8";
                    break;
                case R.id.nine:
                    num = "9";
                    break;
                default:
                    throw new UnsupportedOperationException("should never happen");
            }

        }
    };
}