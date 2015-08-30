package com.lucassky.fay.utils;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;

import com.lucassky.fay.R;

import java.lang.reflect.Field;
import java.util.List;


/**
 * Created by 朋 on 2015/8/30.
 */
public class TextUitl {
    // 加入话题 好友 URL的连结
    public static char strarray[];

    public static void addURLSpan(Context activity, String str, TextView textView) {
        try{
            SpannableString ss = new SpannableString(str);
            strarray = str.toCharArray();
            int l = str.length() - 10;
            for (int i = 0; i < l; i++) {
                if (strarray[i] == 'h' && strarray[i + 1] == 't'
                        && strarray[i + 2] == 't' && strarray[i + 3] == 'p'
                        && strarray[i + 4] == ':' && strarray[i + 5] == '/'
                        && strarray[i + 6] == '/') {
                    StringBuffer sb = new StringBuffer("http://");
                    for (int j = i + 7; true; j++) {
                        if (strarray[j] != ' ')
                            sb.append(strarray[j]);
                        else {
                            Log.d("http", sb.toString());
                            ss.setSpan(new URLSpan(sb.toString()), i, j,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            i = j;
                            break;
                        }
                    }
                }
            }
            // 处理话题
            l = str.length();
            StringBuffer sb = null;
            boolean start = false;
            int startIndex = 0;
            for (int i = 0; i < l; i++) {
                if (strarray[i] == '@') {
                    start = true;
                    sb = new StringBuffer("weibo://weibo.view/");
                    startIndex = i;
                } else {
                    if (start) {
                        if (strarray[i] == ':') {
                            ss.setSpan(new URLSpan(sb.toString()), startIndex, i,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb = null;
                            start = false;
                        } else {
                            sb.append(strarray[i]);
                        }
                    }
                }

            }
            // 处理 话题
            start = false;
            startIndex = 0;
            for (int i = 0; i < l; i++) {
                if (strarray[i] == '#') {
                    if (!start) {
                        start = true;
                        sb = new StringBuffer("weibo://weibo.view/");
                        startIndex = i;
                    } else {
                        sb.append('#');
                        ss.setSpan(new URLSpan(sb.toString()), startIndex, i + 1,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb = null;
                        start = false;
                    }
                } else {
                    if (start) {
                        sb.append(strarray[i]);
                    }
                }
            }

            //处理显示表情
//            String content = str;
//            int len = 0;
//            int starts = 0;
//            int end = 0;
//            while(len < content.length()){
//                if(content.indexOf("[", starts) != -1 && content.indexOf("]", end) != -1){
//                    starts = content.indexOf("[", starts);
//                    end = content.indexOf("]", end);
//                    String phrase = content.substring(starts,end + 1);
//                    String imageName = "";
//                    List<Emotions> list = BlogHomeActivity.emotions;
//                    for (Emotions emotions : list) {
//                        if (emotions.getPhrase().equals(phrase)) {
//                            imageName = emotions.getImageName();
//                        }
//                    }
//
//                    try {
//                        Field f = (Field)R.drawable.class.getDeclaredField(imageName);
//                        int i= f.getInt(R.drawable.class);
//                        Drawable drawable = activity.getResources().getDrawable(i);
//                        if (drawable != null) {
//                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
//                            ss.setSpan(span, starts,end + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                        }
//                    } catch (SecurityException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchFieldException e) {
//                        e.printStackTrace();
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//
//                    }
//                    starts = end;
//                    len = end;
//                    end++;
//                }else{
//                    starts++;
//                    end++;
//                    len = end;
//                }
//            }

            textView.setText(ss); // 设定TextView话题和url和好友 连接
            strarray = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
