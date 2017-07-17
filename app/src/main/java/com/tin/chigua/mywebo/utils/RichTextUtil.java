package com.tin.chigua.mywebo.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

import com.tin.chigua.mywebo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hasee on 5/17/2017.
 */

public class RichTextUtil {

    private static final int WEB_CODE = 0;
    private static final int MENTION_CODE = 1;
    private static final int TOPIC_CODE = 2;
    private static final int EMOJI_CODE = 3;
    private static final int FULL_TEXT_CODE = 3;


    private static String regex = "(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
    private static final Pattern WEB_PATTERN = Pattern.compile(regex);
    private static final Pattern MENTION_PATTERN = Pattern.compile("@[\\u4e00-\\u9fa5a-zA-Z0-9_-]+");
    private static final Pattern TOPIC_PATTERN = Pattern.compile("#[\\u4e00-\\u9fa5a-zA-Z0-9_-]+#");
    private static final Pattern EMOJI_PATTERN = Pattern.compile("\\[[\\w\\u4e00-\\u9fa5\\w]+\\]");
    private static final Pattern FULL_TEXT_PATERN = Pattern.compile("全文");


    public static SpannableString getSpanString(Context context,String text){

        final SpannableString spannableString = new SpannableString(text);
        if(!TextUtils.isEmpty(text)){
            final int linkColor = ContextCompat.getColor(context, R.color.webo_blue);
            Matcher webMatcher = WEB_PATTERN.matcher(spannableString);
            setTextColor(context,spannableString,webMatcher,linkColor,WEB_CODE);

            Matcher mentionMatcher = MENTION_PATTERN.matcher(spannableString);
            setTextColor(context,spannableString,mentionMatcher,linkColor,MENTION_CODE);

            Matcher topicMathcer = TOPIC_PATTERN.matcher(spannableString);
            setTextColor(context,spannableString,topicMathcer,linkColor,TOPIC_CODE);

            Matcher emojiMatcher = EMOJI_PATTERN.matcher(spannableString);
//            setTextColor();

            Matcher fullTextMatcher = FULL_TEXT_PATERN.matcher(spannableString);
            setTextColor(context,spannableString,topicMathcer,linkColor,FULL_TEXT_CODE);
        }
        return spannableString;
    }

    private static void setTextColor(final Context context, final SpannableString string, Matcher matcher, final int color, final int code){
        while (matcher.find()){
            final String str = matcher.group();
            URLSpan urlSpan = new URLSpan(str);

            string.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    LUtils.logD(context,str);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setColor(color);
                    switch (code){
                        case 0:
                            break;
                        default:
                            break;
                    }
                }
            },matcher.start(),matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

}
