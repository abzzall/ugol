package apps.shivas.coalgraphapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SecondActivity extends ActionBarActivity {

    private ImageView images[];
    private TextView texts[];

    private Animation hideAnimation, knokAnimation, zeroAlpha, getAwayAnimation, dragAnimation, dragToScreen;
    //private ImageView back;
    //private View testView;
    //private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        images = new ImageView[13];
        texts = new TextView[12];
        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_test);
        hideAnimation.setFillAfter(true);
        knokAnimation = AnimationUtils.loadAnimation(this, R.anim.knok_test);
        knokAnimation.setFillAfter(true);
        zeroAlpha = AnimationUtils.loadAnimation(this, R.anim.zeroalpha);
        zeroAlpha.setFillAfter(true);
        getAwayAnimation = AnimationUtils.loadAnimation(this, R.anim.getaway);
        getAwayAnimation.setFillAfter(true);
        dragAnimation = AnimationUtils.loadAnimation(this, R.anim.drag);
        dragAnimation.setFillAfter(true);
        dragToScreen = AnimationUtils.loadAnimation(this, R.anim.drag_to_screen);
        dragToScreen.setFillAfter(true);

        images[0] = (ImageView) findViewById(R.id.bookImage);
        images[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplitAnimation.startActivity(SecondActivity.this, new Intent(SecondActivity.this, HistoryActivity.class));
            }
        });
        images[1] = (ImageView) findViewById(R.id.shipImage);
        images[2] = (ImageView) findViewById(R.id.directorImage);
        images[3] = (ImageView) findViewById(R.id.turnImage);
        images[4] = (ImageView) findViewById(R.id.albumImage);
        images[5] = (ImageView) findViewById(R.id.mechImage);
        images[6] = (ImageView) findViewById(R.id.workingImage);
        images[7] = (ImageView) findViewById(R.id.handsImage);
        images[8] = (ImageView) findViewById(R.id.peopleImage);
        images[9] = (ImageView) findViewById(R.id.backImage);
        images[10] = (ImageView) findViewById(R.id.photo1Image);
        images[11] = (ImageView) findViewById(R.id.photo2Image);
        images[12] = (ImageView) findViewById(R.id.photo3Image);

        texts[0] = (TextView) findViewById(R.id.bookText);
        texts[1] = (TextView) findViewById(R.id.shipText);
        texts[2] = (TextView) findViewById(R.id.directorText);
        texts[3] = (TextView) findViewById(R.id.terminalText);
        texts[4] = (TextView) findViewById(R.id.album1Text);
        texts[5] = (TextView) findViewById(R.id.album2Text);
        texts[6] = (TextView) findViewById(R.id.mechText);
        texts[7] = (TextView) findViewById(R.id.workingText);
        texts[8] = (TextView) findViewById(R.id.hands1Text);
        texts[9] = (TextView) findViewById(R.id.hands2Text);
        texts[10] = (TextView) findViewById(R.id.people1Text);
        texts[11] = (TextView) findViewById(R.id.people2Text);

    }
    public void clickOnAlbumButton(View v) {
        Intent intent = new Intent(SecondActivity.this, TransActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.spin_out, R.anim.spin_in);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.unbindDrawables(findViewById(R.id.mainLayout));
        System.gc();

    }


    public void clickOnTerminalButton1(View v) {
        startActivity(new Intent(this,TerminalActivity.class));
    }

    public void clickOnTerminalButton2(View v) {
        for(int i = 0; i < 13; i++) {
            images[i].startAnimation(dragAnimation);
            if(i < 12) {
                texts[i].startAnimation(dragAnimation);
            }
        }
      //  back.startAnimation(dragToScreen);
    }

    private void moveViewToScreenCenter( View view )
    {
        RelativeLayout root = (RelativeLayout) findViewById( R.id.mainLayout );
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int originalPos[] = new int[2];
        view.getLocationOnScreen( originalPos );

        int xDest = dm.widthPixels/2;
        xDest -= (view.getMeasuredWidth()/2);
        int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

        AnimationSet set = new AnimationSet(true);


        TranslateAnimation anim = new TranslateAnimation( 0, xDest - originalPos[0] , 0, yDest - originalPos[1] );
        anim.setDuration(1000);
        anim.setFillAfter( true );
        view.startAnimation(anim);
    }
}
