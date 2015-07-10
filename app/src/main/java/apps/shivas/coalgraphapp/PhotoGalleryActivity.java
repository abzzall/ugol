package apps.shivas.coalgraphapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;


public class PhotoGalleryActivity extends ActionBarActivity {

    private ImageView photos[];
    private PhotoParameters parameters[];
    private PhotoParameters mainParameters;
    private int main_index, away_index;
    private double x1, y1, x2, y2, vc, oldv;
    private float scale;
    private float trX, trY;

    private static double TENSION = 800;
    private static double DAMPER = 30; //friction

    private SpringSystem sys;
    private Spring mSpring, aSpring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        photos = new ImageView[14];
        parameters = new PhotoParameters[14];
        mainParameters = new PhotoParameters(420, 266);
        mainParameters.s_w = 450;
        mainParameters.s_h = 335;
        main_index = -1;

        photos[0] = (ImageView) findViewById(R.id.photo1Gallery);
        photos[1] = (ImageView) findViewById(R.id.photo2Gallery);
        photos[2] = (ImageView) findViewById(R.id.photo3Gallery);
        photos[3] = (ImageView) findViewById(R.id.photo4Gallery);
        photos[4] = (ImageView) findViewById(R.id.photo5Gallery);
        photos[5] = (ImageView) findViewById(R.id.photo6Gallery);
        photos[6] = (ImageView) findViewById(R.id.photo7Gallery);
        photos[7] = (ImageView) findViewById(R.id.photo8Gallery);
        photos[8] = (ImageView) findViewById(R.id.photo9Gallery);
        photos[9] = (ImageView) findViewById(R.id.photo10Gallery);
        photos[10] = (ImageView) findViewById(R.id.photo11Gallery);
        photos[11] = (ImageView) findViewById(R.id.photo12Gallery);
        photos[12] = (ImageView) findViewById(R.id.photo13Gallery);
        photos[13] = (ImageView) findViewById(R.id.photo14Gallery);

        sys = SpringSystem.create();
        mSpring = sys.createSpring();
        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setSpringConfig(config);
        mSpring.addListener(new PhotoMainListener());

        aSpring = sys.createSpring();
        aSpring.setSpringConfig(config);
        aSpring.addListener(new PhotoAwayListener());
    }

    private void init() {
        for(int i = 0; i < 14; i++) {
            parameters[i] = new PhotoParameters(photos[i].getX(), photos[i].getY());
        }
    }



    private void sizeUpAnimation(float ds) {
        ScaleAnimation scl = new ScaleAnimation(scale, scale + ds, scale, scale + ds);
        scale += ds;
        scl.setDuration(100);
        scl.setFillAfter(true);
        photos[main_index].startAnimation(scl);
    }

    public void clickOnPhotoInGallery(View v) {
        int index = 0;
        for(int i = 0; i < 14; i++) {
            if(v.equals(photos[i])) {
                index = i;
                break;
            }
        }

        if(main_index != -1) {
            updateValues();
            awayPhoto(main_index);
            setMainPhoto(index);
        } else {
            init();
            setMainPhoto(index);
        }
    }

    private void updateValues() {
        if(aSpring.getCurrentValue() > 0) {
            aSpring.removeAllListeners();
            aSpring.setCurrentValue(0.0f);
            aSpring.addListener(new PhotoAwayListener());
        }

        if(mSpring.getCurrentValue() > 0) {
            mSpring.removeAllListeners();
            mSpring.setCurrentValue(0.0f);
            mSpring.addListener(new PhotoMainListener());
        }
    }

    private void setMainPhoto(int index) {
        main_index = index;

        mSpring.setEndValue(1.0f);
    }

    private void awayPhoto(int index) {
        away_index = index;

        aSpring.setEndValue(1.0f);
    }

    class PhotoMainListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            float value = (float) spring.getCurrentValue();
            float x, y, scale;

            if (photos[main_index].getMeasuredWidth() > photos[main_index].getMeasuredHeight()) {
                scale = mainParameters.s_w / photos[main_index].getMeasuredWidth();
                y = mainParameters.s_y + photos[main_index].getMeasuredHeight() * (scale - 1) / 2;// + (mainParameters.s_h - photos[main_index].getMeasuredHeight())/2;
                x = mainParameters.s_x + photos[main_index].getMeasuredWidth() * (scale - 1) / 2;
            } else {
                scale = mainParameters.s_h / photos[main_index].getMeasuredHeight();
                y = mainParameters.s_y + photos[main_index].getMeasuredHeight() * (scale - 1) / 2;
                x = mainParameters.s_x + photos[main_index].getMeasuredWidth() * (scale - 1) / 2 + (mainParameters.s_w - photos[main_index].getMeasuredWidth()) / 2;
            }

            photos[main_index].setX(x * value);
            photos[main_index].setY(y * value);
            photos[main_index].setScaleX(scale * value);
            photos[main_index].setScaleY(scale * value);

        }

        @Override
        public void onSpringAtRest(Spring spring) {
            float value = (float) spring.getCurrentValue();
            away_index = main_index;
        }

        @Override
        public void onSpringActivate(Spring spring) {
            float value = (float) spring.getCurrentValue();
        }

        @Override
        public void onSpringEndStateChange(Spring spring) {
            float value = (float) spring.getCurrentValue();
        }
    }

    class PhotoAwayListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            float value = (float) spring.getCurrentValue();

            if(away_index != main_index) {
                photos[away_index].setX(parameters[away_index].s_x * value);
                photos[away_index].setY(parameters[away_index].s_y * value);
                photos[away_index].setScaleX(value);
                photos[away_index].setScaleY(value);
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }
}

class PhotoParameters {
    public float s_x;
    public float s_y;
    public float s_w;
    public float s_h;

    public PhotoParameters(float x, float y) {
        s_x = x;
        s_y = y;
    }
}
