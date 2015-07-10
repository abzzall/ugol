package apps.shivas.coalgraphapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by abz on 7/10/2015.
 */
public class PopUpWindowWithScaling extends PopupWindow {

 //   private int width;
    ViewWithTouch popupview;
    public RelativeLayout rlPopUp;
    public PopUpWindowWithScaling(Context cnt){
        super();
        setWidth(640);
        setHeight(400);
        popupview=new ViewWithTouch(cnt);
        //super(popupview,640,400);

        //super(popupview,640,400);
        this.setContentView(popupview);
        rlPopUp=popupview.rlPopUp;
        this.setTouchable(true);
        //this.showAtLocation();

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupview==null){
                    WebView wv=(WebView) rlPopUp.findViewById(R.id.webView);
                    if(wv!=null){
                        wv.loadUrl("about:blank");
                    }

                }
                Util.unbindDrawables(popupview);

            }
        });

    }

    @Override
    protected void finalize() throws Throwable {
        this.dismiss();
        super.finalize();
        System.gc();
    }

    public void setTitle(String title){
        popupview.btnMove.setText(title);
    }

    public void setTitleSize(int i) {
        popupview.btnMove.setTextSize(i);
    }


    public class ViewWithTouch extends RelativeLayout {
        Button btnMove;
        LayoutInflater mInflater;
        private double x1;
        private double y1;
        private double x2;
        private double y2;
        private int width;
        private int height;
        private double oldv;
        private double v;
        private int dx;
        private int dy;
        private int xp;
        private int yp;
        private int sides;
        private int topBot;
        public RelativeLayout rlPopUp;
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

        void sizeUpAnimation(int dW, int dH) {
            if(dW > 0) {
                if (width <= 960 & height <= 600) {

                    width = PopUpWindowWithScaling.this.getWidth();
                    height = PopUpWindowWithScaling.this.getHeight();
                    width += dW;
                    height += dH;

                    PopUpWindowWithScaling.this.update(width, height);
                }
            } else {
                if(dW < 0) {
                    if(width >= 320 & height >= 200) {
                        width = this.getWidth();
                        height = this.getHeight();
                        width += dW;
                        height += dH;

                        PopUpWindowWithScaling.this.update(width, height);
                    }
                }
            }
        }

        public void init()
        {
            mInflater.inflate(R.layout.pop_up_with_move, this, true);
            btnMove=(Button)findViewById(R.id.btnMove);
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


                                    PopUpWindowWithScaling.this.update(sides, topBot, -1, -1, false);
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
            rlPopUp=(RelativeLayout)findViewById(R.id.rlPopUp);



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
