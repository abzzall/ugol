package apps.shivas.coalgraphapp;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abz on 7/6/2015.
 */
public class Util {
    static public void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        } if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }

}
}
