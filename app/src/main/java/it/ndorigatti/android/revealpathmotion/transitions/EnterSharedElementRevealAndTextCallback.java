/*******************************************************************************
 *   Copyright (c) 2015 Nicola Dorigatti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ******************************************************************************/

package it.ndorigatti.android.revealpathmotion.transitions;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import it.ndorigatti.android.revealpathmotion.R;
/**
 * Created by Nicola on 15/08/2015.
 */
public class EnterSharedElementRevealAndTextCallback extends SharedElementCallback {

    private static final String TAG = "EnterShrdElRev&TxtCbk";

    private final int mEndingImageViewId = R.id.image_thumbnail;
    //Activity for resources lookup
    private Activity mActivity;
    //snapshot view for reveal
    View mSnapshot;

    //Text parameters
    private final float mStartTextSize, mStartSubSize;
    private final float mEndTextSize, mEndSubSize;
    //
    private final int mSubStartColor, mSubEndColor;

    public EnterSharedElementRevealAndTextCallback(Activity activity) {
        this.mActivity = activity;
        Resources res = mActivity.getResources();
        mStartTextSize = res.getDimensionPixelSize(R.dimen.starting_title_size);
        mEndTextSize = res.getDimensionPixelSize(R.dimen.end_title_size);
        mSubStartColor = res.getColor(R.color.secondary_text_default_material_light);
        mSubEndColor = res.getColor(R.color.primary_text_default_material_light);
        mStartSubSize = res.getDimensionPixelSize(R.dimen.starting_sub_size);
        mEndSubSize = res.getDimensionPixelSize(R.dimen.end_sub_size);
    }

    @Override
    public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        Log.i(TAG, "onSharedElementStart");
        //Add snapshot for reveal animation
        addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots, false);
        if (mSnapshot != null) {
            mSnapshot.setVisibility(View.VISIBLE);
        }
        mActivity.findViewById(mEndingImageViewId).setVisibility(View.INVISIBLE);

        // -------------------
        //Now do the code for the Text Animation
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
        Log.i(TAG, "onSharedElementEnd");
        //Add snapshot for reveal animation
        addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots, true);
        if (mSnapshot != null) {
            mSnapshot.setVisibility(View.INVISIBLE);
        }
        mActivity.findViewById(mEndingImageViewId).setVisibility(View.VISIBLE);

        // -------------------
        //Now do the code for the Text Animation
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

    //Override for reveal motion
    @Override
    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        mActivity.findViewById(mEndingImageViewId).setVisibility(View.INVISIBLE);
    }

    //Reveal method for snapshot view
    private void addSnapshot(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots, boolean relayoutContainer) {
        if (mSnapshot == null) {
            for (int i = 0; i < sharedElementNames.size(); i++) {
                if ("reveal".equals(sharedElementNames.get(i))) {
                    FrameLayout element = (FrameLayout) sharedElements.get(i);
                    mSnapshot = sharedElementSnapshots.get(i);
                    int width = mSnapshot.getWidth();
                    int height = mSnapshot.getHeight();
                    FrameLayout.LayoutParams layoutParams =
                            new FrameLayout.LayoutParams(width, height);
                    layoutParams.gravity = Gravity.CENTER;
                    int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                    int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                    mSnapshot.measure(widthSpec, heightSpec);
                    mSnapshot.layout(0, 0, width, height);
                    mSnapshot.setTransitionName("snapshot");
                    if (relayoutContainer) {
                        ViewGroup container = (ViewGroup) mActivity.findViewById(R.id.img_container);
                        int left = (container.getWidth() - width) / 2;
                        int top = (container.getHeight() - height) / 2;
                        element.measure(widthSpec, heightSpec);
                        element.layout(left, top, left + width, top + height);
                    }
                    element.addView(mSnapshot, layoutParams);
                    break;
                }
            }
        }
    }

    //Text animation method
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
        textView.layout(textView.getLeft(), textView.getTop(),
                textView.getRight() + widthDiff, textView.getBottom() + heightDiff);

    }

}
