package apps.shivas.coalgraphapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TerminalActivity extends ActionBarActivity {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    private float x1,y1,x2,y2, oldv, v, scale;
    private int width, height,dx,dy,xp,yp,sides,topBot;
    boolean bl=true;
    private ArrayList<PopUpWindowWithScaling> popupWindow;

    Stack<Integer>cur=new Stack<>();
    //ViewWithTouch popupView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        popupWindow=new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);
        btn1=(Button)findViewById(R.id.Button1);
      btn2=(Button)findViewById(R.id.Button2);
      btn3=(Button)findViewById(R.id.Button3);
       btn4=(Button)findViewById(R.id.Button4);
      btn5=(Button)findViewById(R.id.Button5);
     btn6=(Button)findViewById(R.id.Button6);
      btn7=(Button)findViewById(R.id.Button7);
        btn1.setOnClickListener(oncl);
        btn2.setOnClickListener(oncl);
        btn3.setOnClickListener(oncl);
        btn4.setOnClickListener(oncl);
        btn5.setOnClickListener(oncl);
        btn6.setOnClickListener(oncl);
        btn7.setOnClickListener(oncl);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.termLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (!popupWindow.isEmpty()){
                    popupWindow.get(popupWindow.size()-1).dismiss();
                    popupWindow.remove(popupWindow.size()-1);
                    return;

                }
            }
        });

    }

        @Override
    public void onBackPressed() {
        if (!popupWindow.isEmpty()){
            popupWindow.get(popupWindow.size()-1).dismiss();
            popupWindow.remove(popupWindow.size()-1);
            return;

        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Util.unbindDrawables(findViewById(R.id.termLayout));
        System.gc();
        super.onDestroy();


    }
    View.OnClickListener oncl=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater li=getLayoutInflater();
            //popupWindow.add(new PopUpWindowWithScaling(TerminalActivity.this));
            PopUpWindowWithScaling popup=new PopUpWindowWithScaling(TerminalActivity.this);
            RelativeLayout rlPopUp=popup.rlPopUp;
            switch(v.getId()){
                case R.id.Button1:{
                    popup.setTitle("Фото побережья до начала работ");
                    View empty = li.inflate(R.layout.empty_layout, null);
                    rlPopUp.addView(empty);
                    ImageView img=(ImageView)rlPopUp.findViewById(R.id.photo);
                    img.setImageResource(R.drawable.poberezhie_do_nach);
                    break;
                }
                case R.id.Button2:{
                    popup.setTitle("Видеоролик");
                    View empty = li.inflate(R.layout.web, null);
                    rlPopUp.addView(empty);;
                    WebView mWebView=(WebView)rlPopUp.findViewById(R.id.webView);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    String videoID = "7wg3tBR2MF0";
                    mWebView.loadUrl("http://www.youtube.com/embed/" + videoID + "?autoplay=1&vq=small");
                    mWebView.setWebChromeClient(new WebChromeClient());
                    break;
                }
                case R.id.Button3:{
                    popup.setTitle("1 ОЧЕРЕДЬ - 2001г. ушло первое студио \"Амур\"");
                    popup.setTitleSize(20);
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
                    ViewPager viewPager = new ViewPager(TerminalActivity.this);
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setCurrentItem(1);
                    rlPopUp.addView(viewPager);
                    break;
                }
                case R.id.Button4:{
                    popup.setTitle("3 ОЧЕРЕДЬ");
                    break;
                }
                case R.id.Button5:{
                    popup.setTitle("2 ОЧЕРЕДЬ - (приезд Путина) январь 2006г.");
                    popup.setTitleSize(25);
                    List<View> pages = new ArrayList<View>();

                    View page = li.inflate(R.layout.page, null);
                    ImageView image = (ImageView) page.findViewById(R.id.imagePage);
                    Drawable img = getResources().getDrawable(R.drawable.putin0);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.putin1);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.putin2);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.putin3);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.putin4);
                    image.setImageDrawable(img);
                    pages.add(page);
                    SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
                    ViewPager viewPager = new ViewPager(TerminalActivity.this);
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setCurrentItem(1);
                    rlPopUp.addView(viewPager);
                    break;
                }
                case R.id.Button6:{
                    popup.setTitle("Май 2003г. - экспортные отгрузки на постоянной основе");
                    popup.setTitleSize(20);

                    List<View> pages = new ArrayList<View>();

                    View page = li.inflate(R.layout.page, null);
                    ImageView image = (ImageView) page.findViewById(R.id.imagePage);
                    Drawable img = getResources().getDrawable(R.drawable.may1);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.may2);
                    image.setImageDrawable(img);
                    pages.add(page);

                    page = li.inflate(R.layout.page, null);
                    image = (ImageView) page.findViewById(R.id.imagePage);
                    img = getResources().getDrawable(R.drawable.may3);
                    image.setImageDrawable(img);
                    pages.add(page);

                    SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
                    ViewPager viewPager = new ViewPager(TerminalActivity.this);
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setCurrentItem(0);
                    rlPopUp.addView(viewPager);
                /*
                    VideoView vd=(VideoView)rlPopUp.findViewById(R.id.videoView);
                    vd.setMediaController(new MediaController(TerminalActivity.this));
                    vd.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kara));
                    vd.requestFocus(0);
                    vd.start();*/
                    break;
                }
                case R.id.Button7:{
                    popup.setTitle("Фото побережья 1999г.");
                    View empty = li.inflate(R.layout.empty_layout, null);
                    rlPopUp.addView(empty);
                    ImageView img=(ImageView)rlPopUp.findViewById(R.id.photo);
                    img.setImageResource(R.drawable.poberezhie99);
                    break;
                }
            }
            popup.showAtLocation(TerminalActivity.this.findViewById(R.id.termLayout), Gravity.CENTER, 0, 0);
            popupWindow.add(popup);


                //rlPopUp.setOnTouchListener(onTouchListener);
            //popupWindow.get(0).showAtLocation(TerminalActivity.this.findViewById(R.id.termLayout), Gravity.CENTER, 0, 0);




    };
    };
    public class SamplePagerAdapter extends PagerAdapter {

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
}
