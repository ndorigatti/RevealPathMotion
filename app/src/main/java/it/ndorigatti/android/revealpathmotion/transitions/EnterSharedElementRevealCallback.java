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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Map;

import it.ndorigatti.android.revealpathmotion.R;
/**
 * Created by Nicola on 15/08/2015.
 */
public class EnterSharedElementRevealCallback extends SharedElementCallback {
    private static final String TAG = "EnterSharedReveal";

    private Activity mActivity;
    View mSnapshot;

    public EnterSharedElementRevealCallback(Activity activity){
        this.mActivity=activity;
    }

    @Override
    public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots, false);
        if (mSnapshot != null) {
            mSnapshot.setVisibility(View.VISIBLE);
        }
        mActivity.findViewById(R.id.image_thumbnail).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots,
                true);
        if (mSnapshot != null) {
            mSnapshot.setVisibility(View.INVISIBLE);
        }
        mActivity.findViewById(R.id.image_thumbnail).setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        mActivity.findViewById(R.id.image_thumbnail).setVisibility(View.INVISIBLE);
    }

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

}
