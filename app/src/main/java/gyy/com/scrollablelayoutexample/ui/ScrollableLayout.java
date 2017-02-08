package gyy.com.scrollablelayoutexample.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 可滑动组件
 * Created by GuanYangYi on 2017/2/8.
 */

public class ScrollableLayout extends RelativeLayout implements View.OnClickListener{
    private OnItemClicked onItemClicked;
    private TextView slideView;
    private int lastPos = 1;
    private LinearLayout linearLayout;


    public void setOnItemClicke(OnItemClicked onItemClicked){
        this.onItemClicked = onItemClicked;
    }

    public ScrollableLayout(Context context) {
        super(context);
        init(context,null,0);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    //对组件进行初始化
    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        slideView = new TextView(context);
        slideView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.holo_purple));
        slideView.setAlpha(0.5f);
        post(new Runnable() {
            @Override
            public void run() {
                linearLayout  = (LinearLayout) getChildAt(0);
                TextView re = (TextView) linearLayout.getChildAt(0);
                LayoutParams params = new LayoutParams(re.getWidth(),re.getHeight());
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                addView(slideView,params);
                for (int i=0;i<linearLayout.getChildCount();i++) linearLayout.getChildAt(i).setOnClickListener(ScrollableLayout.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int newPos = 0;
        for (int i=1;i<linearLayout.getChildCount();i++){
            if ( linearLayout.getChildAt(i) == v ){
                newPos = i;
            }
        }
        if ( newPos !=lastPos && newPos!=0 ){
            slideView.animate().translationXBy( (newPos-lastPos)*slideView.getWidth()).setDuration(500);
            if ( onItemClicked!= null ){
                onItemClicked.onClick(v.getId());
            }
            lastPos = newPos;
        }
    }


    public interface OnItemClicked{
        void onClick(int itemId);
    }
}
