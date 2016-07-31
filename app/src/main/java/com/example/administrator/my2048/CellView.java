package com.example.administrator.my2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

/**
 *
 */
public class CellView extends TextView {
    //单元数值
    private int number = 0;  //0为空cell

    public int getNumber() {
        return number;
    }

    public CellView(Context context) {
        super(context);
        initialise();

    }
    //gridview 设置了列数 就自动匹配，然后获取宽度，设置宽度长度一样 就可以了
    //问题是onMeasure 和 gridView 加载数据的先后顺序？
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = this.getMeasuredWidth();
        setTextSize(TypedValue.COMPLEX_UNIT_PX,w/3);
        setMeasuredDimension(w, w);
    }

    public void setNumber(int number) {
        this.number = number;
        //设置数值
        if (!(number == 0)) {
            setText(String.valueOf(number));
        } else
            setText("");
        initialise();
    }


    //颜色初始化自匹配
    private void initialise() {
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.parseColor("#CDC1B4"));
        //原始字体颜色
        setTextColor(Color.parseColor("#FFFFFF"));
        //1,2，4，8，16...分别对应的颜色
        String[] color = {"#EEE4DA", "#EDE0C8", "#F2B179",
                "#F59563", "#F67C5F", "#F65E3B", "#EDCF72",
                "#EDCC61", "#EDC850", "#EDC53F", "#EDC53F"};
        //根据传入值改变颜色
        for (int i = 1; i < 12; i++) {
            if (Math.pow(2, i) == number) {
                setBackgroundColor(Color.parseColor(color[i - 1]));
                //2和4的字体颜色为白色
                if (number == 2 || number == 4)
                    setTextColor(Color.parseColor("#776E65"));
            }
        }

    }

}
