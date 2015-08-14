package it.ndorigatti.android.revealpathmotion.transitions;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import it.ndorigatti.android.revealpathmotion.R;

public class EnterSharedElementTextCallback extends SharedElementCallback {
    private static final String TAG = "EnterSharedElementCbk";

    private final float mStartTextSize, mStartSubSize;
    private final float mEndTextSize, mEndSubSize;
    //
    private final int mSubStartColor, mSubEndColor;

    public EnterSharedElementTextCallback(Context context) {
        Resources res = context.getResources();
        mStartTextSize = res.getDimensionPixelSize(R.dimen.starting_title_size);
        mEndTextSize = res.getDimensionPixelSize(R.dimen.end_title_size);
        mSubStartColor = res.getColor(R.color.secondary_text_default_material_light);
        mSubEndColor = res.getColor(R.color.primary_text_default_material_light);
        mStartSubSize = res.getDimensionPixelSize(R.dimen.starting_sub_size);
        mEndSubSize = res.getDimensionPixelSize(R.dimen.end_sub_size);
    }

    @Override
    public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        Log.i(TAG, "onSharedElemntStart");

        int titidx = 0;
        int subidx = 1;

        for (int i = 0; i < sharedElementNames.size(); i++) {
            if (sharedElementNames.get(i).equals("bigtitle")) {
                titidx = i;
                continue;
            }
            if (sharedElementNames.get(i).equals("subtitle")) {
                subidx = i;
            }
        }

        TextView textView = (TextView) sharedElements.get(titidx);
        TextView textView2 = (TextView) sharedElements.get(subidx);
        // Setup the TextView's start values.
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mStartTextSize);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PX, mStartSubSize);
        textView2.setTextColor(mSubStartColor);


    }

    @Override
    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        Log.i(TAG, "onSharedElemntEnd");

        int titidx = 0;
        int subidx = 1;

        for (int i = 0; i < sharedElementNames.size(); i++) {
            if (sharedElementNames.get(i).equals("bigtitle")) {
                titidx = i;
                continue;
            }
            if (sharedElementNames.get(i).equals("subtitle")) {
                subidx = i;
            }
        }

        TextView textView = (TextView) sharedElements.get(titidx);
        TextView textView2 = (TextView) sharedElements.get(subidx);
        setTextViewSize(textView, mEndTextSize);
        setTextViewSize(textView2, mEndSubSize);
        textView2.setTextColor(mSubEndColor);
    }

    private void setTextViewSize(TextView textView, float size) {
        // Record the TextView's old width/height.
        int oldWidth = textView.getMeasuredWidth();
        int oldHeight = textView.getMeasuredHeight();

        // Setup the TextView's end values.
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        // Re-measure the TextView (since the text size has changed).
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthSpec, heightSpec);

        // Record the TextView's new width/height.
        int newWidth = textView.getMeasuredWidth();
        int newHeight = textView.getMeasuredHeight();

        // Layout the TextView in the center of its container, accounting for its new width/height.
        int widthDiff = newWidth - oldWidth;
        int heightDiff = newHeight - oldHeight;
        textView.layout(textView.getLeft() , textView.getTop(),
               textView.getRight() + widthDiff , textView.getBottom() + heightDiff );

    }
}
