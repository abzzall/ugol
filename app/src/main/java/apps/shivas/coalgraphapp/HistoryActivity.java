package apps.shivas.coalgraphapp;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends ActionBarActivity implements View.OnClickListener{
    private static final int MAX_PAGES = 8;

    private float x1,y1,x2,y2, oldv, v, scale;
    private int width, height,dx,dy,xp,yp,sides,topBot;;

    private View popupView=null;
    private PopupWindow popupWindow=null;

    Button btRealPr,btIst,btRas,btPort;

    public void clickOnPhotoGalleryButton(View v) {
        Intent intent = new Intent(HistoryActivity.this, PhotoGalleryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Preparing the 2 images to be split
        SplitAnimation.prepareAnimation(this);
        scale = 1.0f;
        width = 640;
        height = 400;

        setContentView(R.layout.activity_history);

        // Animating the items to be open, revealing the new activity
        SplitAnimation.animate(this, 2000);


        btRealPr=(Button)findViewById(R.id.btRealPr);
        btIst=(Button)findViewById(R.id.btIst);
        //btRas=(Button)findViewById(R.id.btRas);
        btPort=(Button)findViewById(R.id.btPort);

        btRealPr.setOnClickListener(oncl);
        btIst.setOnClickListener(oncl);
        //btRas.setOnClickListener(oncl);
        btPort.setOnClickListener(oncl);
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.history_layout);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null){
                    if (popupView!=null){
                        WebView wv=(WebView) popupView.findViewById(R.id.webView);
                        if(wv!=null){
                            wv.loadUrl("about:blank");
                        }
                    }
                    popupWindow.dismiss();

                    popupWindow=null;

                }
            }
        });
    }
    View.OnClickListener oncl=new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            if (popupWindow!=null){
                if (popupView!=null){
                    WebView wv=(WebView) popupView.findViewById(R.id.webView);
                    if(wv!=null){
                        wv.loadUrl("about:blank");
                    }
                }
                popupWindow.getContentView().destroyDrawingCache();
                Util.unbindDrawables(popupWindow.getContentView());
                System.gc();
                popupWindow.dismiss();


            }
            LayoutInflater layoutInflater
                    = (LayoutInflater)getBaseContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView =new ViewWithTouch(HistoryActivity.this); //(ViewWithTouch) layoutInflater.inflate(R.layout.pop_up,null);
            popupWindow = new PopupWindow(
                    popupView,640,400);
            popupWindow.setTouchable(true);
            //popupWindow.setOutsideTouchable(true);
            //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.test_img));
            //RelativeLayout rel = (RelativeLayout) findViewById(R.id.history_layout);
        /*View.OnTouchListener onTouchListener=new RelativeLayout.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent m) {

                return handleTouch(m);
                //return true;
            }

            boolean handleTouch(MotionEvent m) {
                //Number of touches
                int pointerCount = m.getPointerCount();
                if (pointerCount == 2) {

                    int action = m.getActionMasked();
                    int actionIndex = m.getActionIndex();
                    String actionString;
                    switch (action) {
                        case MotionEvent.ACTION_MOVE:
                            x1 = m.getX(0);
                            y1 = m.getY(0);
                            x2 = m.getX(1);
                            y2 = m.getY(1);
                            if (oldv == 0) {
                                oldv = (float) Math.sqrt(Math.abs(x2 - x1) * Math.abs(x2 - x1) + Math.abs(y2 - y1) * Math.abs(y2 - y1));
                            } else {
                                v = (float) Math.sqrt(Math.abs(x2 - x1) * Math.abs(x2 - x1) + Math.abs(y2 - y1) * Math.abs(y2 - y1));
                                if ((v - oldv) >= 2) {
                                    sizeUpAnimation(8, 5);
                                } else {
                                    if ((oldv - v) >= 2) {
                                        sizeUpAnimation(-8, -5);
                                    }
                                }
                                oldv = v;
                            }
                            break;
                    }
                    //pointerCount = 0;
                    return true;
                }
                return false;
            }

        };*/

            // popupView.dispatchTouchEvent(MotionEvent event){

            //}
            Button btnMove=(Button)popupView.findViewById(R.id.btnMove);
            btnMove.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getPointerCount() == 1) {
                        switch (event.getAction()) {

                            case MotionEvent.ACTION_MOVE:
                                if (dx == 0 & dy == 0) {
                                    dx = (int) event.getRawX();
                                    dy = (int) event.getRawY();
                                } else {
                                    xp = (int) event.getRawX();
                                    if (xp > 1280) {
                                        xp = 1280;
                                    }
                                    if (xp < 0) {
                                        xp = 0;
                                    }

                                    yp = (int) event.getRawY();
                                    if (yp > 800) {
                                        yp = 800;
                                    }
                                    if (yp < 0) {
                                        yp = 0;
                                    }
                                    sides = (xp - dx);
                                    topBot = (yp - dy);


                                    popupWindow.update(sides, topBot, -1, -1, false);
                                }
                                break;

//                            case MotionEvent.ACTION_DOWN:
//                                dx = (int) event.getRawX();
//                                dy = (int) event.getRawY();
                            //break;
                            case MotionEvent.ACTION_UP:
                                //dx = 0;
                                //dy = 0;
                                break;
                        }
                    }
                    return false;
                }
            });



            final LayoutInflater li = getLayoutInflater();
            final RelativeLayout rlPopUp=(RelativeLayout)popupView.findViewById(R.id.rlPopUp);

            if(rlPopUp.getChildCount()!=0){
                rlPopUp.removeAllViews();
            }

            switch(v.getId()){
                case R.id.btRealPr:{
                    btnMove.setText("Руководство");
                    View empty = li.inflate(R.layout.ruklistlayout, null);
                    rlPopUp.addView(empty);
                    ImageView img1=(ImageView)rlPopUp.findViewById(R.id.imageView12),
                            img2=(ImageView)rlPopUp.findViewById(R.id.imageView22),
                            img3=(ImageView)rlPopUp.findViewById(R.id.imageView32),
                            img4=(ImageView)rlPopUp.findViewById(R.id.imageView42);

                    View.OnClickListener selListener= new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            rlPopUp.removeAllViews();
                            View view=li.inflate(R.layout.ruklayout, null);
                            rlPopUp.addView(view);
                            int imgId=R.drawable.rukovod;
                            int name_img_id=R.drawable.test_img;
                            String Descr="Medium Text\nТекст про выбранного человеке который был выбран в предыдущем окне";

                            switch (view2.getId()){
                                case R.id.imageView12:{
                                    name_img_id=R.drawable.simonov;
                                    Descr+="\nБыл выбран Симонов";
                                    break;
                                }
                                case R.id.imageView22:{
                                    name_img_id=R.drawable.krasner;
                                    Descr+="\nБыл выбран Краснер";
                                    break;
                                }
                                case R.id.imageView32:{
                                    name_img_id=R.drawable.korban;
                                    Descr+="\nБыл выбран Корбан";
                                    break;
                                }
                                case R.id.imageView42:{

                                    Descr+="\nБыл выбран Фишер";
                                    name_img_id=R.drawable.fisher;
                                    break;
                                }
                            }
                            ImageView imgName=(ImageView)rlPopUp.findViewById(R.id.imgName),
                                    imgFoto=(ImageView)rlPopUp.findViewById(R.id.imgFoto)
                            ;
                            imgName.setImageResource(name_img_id);
                            imgFoto.setImageResource(imgId);
                            TextView tv=(TextView)rlPopUp.findViewById(R.id.tvDescr);
                            tv.setText(Descr);
                            Button btBack=(Button)rlPopUp.findViewById(R.id.ruk_back);
                            btBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view1) {
                                    oncl.onClick(v);
                                }
                            });
                        }
                    };
                    img1.setOnClickListener(selListener);
                    img2.setOnClickListener(selListener);
                    img3.setOnClickListener(selListener);
                    img4.setOnClickListener(selListener);
                    break;
                }
                case R.id.btIst:{
                    btnMove.setText("Книга");
                    break;
                }
                case R.id.btRas:{
                    btnMove.setText("Фото галерея");
                    List<View> pages = new ArrayList<View>();

                    View page = li.inflate(R.layout.page, null);
                    ImageView image = (ImageView) page.findViewById(R.id.imagePage);
                    Drawable img = getResources().getDrawable(R.drawable.amur1);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.amur2);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.amur3);
                    image.setImageDrawable(img);
                    pages.add(page);

                    SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
                    ViewPager viewPager = new ViewPager(HistoryActivity.this);
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setCurrentItem(1);
                    rlPopUp.addView(viewPager);
                    break;
                }
                case R.id.btPort:{
                    btnMove.setText("Документы");
                    View empty = li.inflate(R.layout.web, null);
                    rlPopUp.addView(empty);;
                    WebView mWebView=(WebView)popupView.findViewById(R.id.webView);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    String videoID = "Huitusc9Vok";
                    mWebView.loadUrl("http://www.youtube.com/embed/" + videoID + "?autoplay=1&vq=small");
                    mWebView.setWebChromeClient(new WebChromeClient());
                    break;
                }


            }
            //rlPopUp.setOnTouchListener(onTouchListener);
            popupWindow.showAtLocation(HistoryActivity.this.findViewById(R.id.history_layout), Gravity.CENTER, 0, 0);



        }
    };
    @Override
    public void onBackPressed() {
        if (popupWindow!=null){
            if (popupView!=null){
                WebView wv=(WebView) popupView.findViewById(R.id.webView);
                if(wv!=null){
                    wv.loadUrl("about:blank");
                }
            }

            popupWindow.dismiss();
            popupWindow=null;
            return;

        }
        super.onBackPressed();
    }
    @Override
    protected void onStop() {
        // If we're currently running the entrance animation - cancel it
        SplitAnimation.cancel();

        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void sizeUpAnimation(int dW, int dH) {
        if(dW > 0) {
            if (width <= 960 & height <= 600) {

                width = popupWindow.getWidth();
                height = popupWindow.getHeight();
                width += dW;
                height += dH;

                popupWindow.update(width, height);
            }
        } else {
            if(dW < 0) {
                if(width >= 320 & height >= 200) {
                    width = popupWindow.getWidth();
                    height = popupWindow.getHeight();
                    width += dW;
                    height += dH;

                    popupWindow.update(width, height);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            case R.id.btRas:{
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.map,null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,500,500);

                Button btnDismiss = (Button)popupView.findViewById(R.id.btEx);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});

                popupWindow.showAtLocation(popupView,Gravity.CENTER,0,0);

                break;
            }
            case  R.id.btIst:{

                ShowPopUp(v.getId());
                break;
            }

        }*/
        ShowPopUp(v.getId());
    }
    public void ShowPopUp(int viewId){
        btIst.setEnabled(false);
        btPort.setEnabled(false);
        btRas.setEnabled(false);
        btRealPr.setEnabled(false);
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewWithTouch popupView =new ViewWithTouch(this); //(ViewWithTouch) layoutInflater.inflate(R.layout.pop_up,null);
        popupWindow = new PopupWindow(
                popupView,640,400);
        popupWindow.setTouchable(true);
        //popupWindow.setOutsideTouchable(true);
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.test_img));
        //RelativeLayout rel = (RelativeLayout) findViewById(R.id.history_layout);
        /*View.OnTouchListener onTouchListener=new RelativeLayout.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent m) {

                return handleTouch(m);
                //return true;
            }

            boolean handleTouch(MotionEvent m) {
                //Number of touches
                int pointerCount = m.getPointerCount();
                if (pointerCount == 2) {

                    int action = m.getActionMasked();
                    int actionIndex = m.getActionIndex();
                    String actionString;
                    switch (action) {
                        case MotionEvent.ACTION_MOVE:
                            x1 = m.getX(0);
                            y1 = m.getY(0);
                            x2 = m.getX(1);
                            y2 = m.getY(1);
                            if (oldv == 0) {
                                oldv = (float) Math.sqrt(Math.abs(x2 - x1) * Math.abs(x2 - x1) + Math.abs(y2 - y1) * Math.abs(y2 - y1));
                            } else {
                                v = (float) Math.sqrt(Math.abs(x2 - x1) * Math.abs(x2 - x1) + Math.abs(y2 - y1) * Math.abs(y2 - y1));
                                if ((v - oldv) >= 2) {
                                    sizeUpAnimation(8, 5);
                                } else {
                                    if ((oldv - v) >= 2) {
                                        sizeUpAnimation(-8, -5);
                                    }
                                }
                                oldv = v;
                            }
                            break;
                    }
                    //pointerCount = 0;
                    return true;
                }
                return false;
            }

        };*/

       // popupView.dispatchTouchEvent(MotionEvent event){

        //}




        LayoutInflater li = getLayoutInflater();
        RelativeLayout rlPopUp=(RelativeLayout)popupView.findViewById(R.id.rlPopUp);

        if(rlPopUp.getChildCount()!=0){
            rlPopUp.removeAllViews();
        }

        switch(viewId){
            case R.id.btRas:{
                View empty = li.inflate(R.layout.empty_layout, null);
                rlPopUp.addView(empty);
                break;
            }
            case R.id.btIst:{
                List<View> pages = new ArrayList<View>();

                View page = li.inflate(R.layout.page, null);
                ImageView image = (ImageView) page.findViewById(R.id.imagePage);
                Drawable img = getResources().getDrawable(R.drawable.ph1);
                image.setImageDrawable(img);
                pages.add(page);

                page = li.inflate(R.layout.page, null);
                image = (ImageView) page.findViewById(R.id.imagePage);
                img = getResources().getDrawable(R.drawable.ph2);
                image.setImageDrawable(img);
                pages.add(page);

                page = li.inflate(R.layout.page, null);
                image = (ImageView) page.findViewById(R.id.imagePage);
                img = getResources().getDrawable(R.drawable.ph3);
                image.setImageDrawable(img);
                pages.add(page);

                page = li.inflate(R.layout.page, null);
                image = (ImageView) page.findViewById(R.id.imagePage);
                img = getResources().getDrawable(R.drawable.ph4);
                image.setImageDrawable(img);
                pages.add(page);

                SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
                ViewPager viewPager = new ViewPager(this);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(1);
                rlPopUp.addView(viewPager);

                //viewPager.setOnTouchListener(onTouchListener);
                //?????????? ????? ??????? ????
                //View view=li.inflate(R.layout.layout_ist, null, false);
                //rlPopUp.addView(view);

                break;
            }


        }
        //rlPopUp.setOnTouchListener(onTouchListener);
        popupWindow.showAtLocation(this.findViewById(R.id.history_layout),Gravity.CENTER, 0, 0);



    }
    /*private class my_adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return MAX_PAGES;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View new_view=null;

            LayoutInflater inflater = getLayoutInflater();
            new_view = inflater.inflate(R.layout.page, null);
            TextView num = (TextView) new_view.findViewById(R.id.page_number);
            num.setText(Integer.toString(position));

            container.addView(new_view);

            return new_view;
        }

    }
    */public class SamplePagerAdapter extends PagerAdapter{

        List<View> pages = null;

        public SamplePagerAdapter(List<View> pages){
            this.pages = pages;
        }

        @Override
        public Object instantiateItem(View collection, int position){
            View v = pages.get(position);
            ((ViewPager) collection).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(View collection, int position, Object view){
            ((ViewPager) collection).removeView((View) view);
        }

        @Override
        public int getCount(){
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object){
            return view.equals(object);
        }

        @Override
        public void finishUpdate(View arg0){
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1){
        }

        @Override
        public Parcelable saveState(){
            return null;
        }

        @Override
        public void startUpdate(View arg0){
        }
    }
    public class ViewWithTouch extends RelativeLayout{
        LayoutInflater mInflater;
        public ViewWithTouch(final Context context) {
            super(context);
            mInflater = LayoutInflater.from(context);
            init();
        }

        public ViewWithTouch(final Context context, final AttributeSet attrs) {
            super(context, attrs);
            mInflater = LayoutInflater.from(context);
            init();
        }

        public ViewWithTouch(final Context context, final AttributeSet attrs,final  int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mInflater = LayoutInflater.from(context);
            init();
        }


        public void init()
        {
            mInflater.inflate(R.layout.pop_up_with_move, this, true);

        }
        @Override
        public boolean dispatchTouchEvent (MotionEvent ev) {
            // Do your calcluations
            //Number of touches

            int pointerCount = ev.getPointerCount();
            if (pointerCount == 2) {

                int action = ev.getActionMasked();
                int actionIndex = ev.getActionIndex();
                String actionString;
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        x1 = ev.getX(0);
                        y1 = ev.getY(0);
                        x2 = ev.getX(1);
                        y2 = ev.getY(1);
                        if (oldv == 0) {
                            oldv = (float) Math.sqrt(Math.abs(x2 - x1) * Math.abs(x2 - x1) + Math.abs(y2 - y1) * Math.abs(y2 - y1));
                        } else {
                            v = (float) Math.sqrt(Math.abs(x2 - x1) * Math.abs(x2 - x1) + Math.abs(y2 - y1) * Math.abs(y2 - y1));
                            if ((v - oldv) >= 2) {
                                sizeUpAnimation(8, 5);
                            } else {
                                if ((oldv - v) >= 2) {
                                    sizeUpAnimation(-8, -5);
                                }
                            }
                            oldv = v;
                        }
                        break;
                }
                //pointerCount = 0;
                return true;
            }

            return super.dispatchTouchEvent(ev);
        }
    }
}
