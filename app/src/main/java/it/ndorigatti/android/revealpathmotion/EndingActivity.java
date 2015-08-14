package it.ndorigatti.android.revealpathmotion;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.ndorigatti.android.revealpathmotion.transitions.EnterSharedElementTextCallback;
import it.ndorigatti.android.revealpathmotion.transitions.TransitionUtils;

public class EndingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);


        TextView bigtitle = (TextView) findViewById(R.id.title_big);
        bigtitle.setText(getIntent().getStringExtra("bigtitle"));
        //ViewCompat.setTransitionName(bigtitle, "bigtitle");

        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText(getIntent().getStringExtra("subtitle"));
        //ViewCompat.setTransitionName(subtitle, "subtitle");

        ImageView image = (ImageView) findViewById(R.id.thumbnail);
        ViewCompat.setTransitionName(image, "thumbnail");

        List<String> textNames = new ArrayList<>();
        textNames.add("bigtitle");
        textNames.add("subtitle");
        List<String> imgNames = new ArrayList<>();
        imgNames.add("thumbnail");
        getWindow().setEnterTransition(TransitionUtils.makeEnterTransition());
        getWindow().setSharedElementEnterTransition(TransitionUtils.makeSharedElementEnterTransition(textNames, imgNames));
        setEnterSharedElementCallback(new EnterSharedElementTextCallback(this));

        //TODO mix callbacks!
        setEnterSharedElementCallback(new SharedElementCallback() {
            View mSnapshot;

            @Override
            public void onSharedElementStart(List<String> sharedElementNames,
                                             List<View> sharedElements,
                                             List<View> sharedElementSnapshots) {
                addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots, false);
                if (mSnapshot != null) {
                    mSnapshot.setVisibility(View.VISIBLE);
                }
                findViewById(R.id.thumbnail).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames,
                                           List<View> sharedElements,
                                           List<View> sharedElementSnapshots) {
                addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots,
                        true);
                if (mSnapshot != null) {
                    mSnapshot.setVisibility(View.INVISIBLE);
                }
                findViewById(R.id.thumbnail).setVisibility(View.VISIBLE);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                findViewById(R.id.thumbnail).setVisibility(View.INVISIBLE);
            }

            private void addSnapshot(List<String> sharedElementNames,
                                     List<View> sharedElements,
                                     List<View> sharedElementSnapshots,
                                     boolean relayoutContainer) {
                if (mSnapshot == null) {
                    for (int i = 0; i < sharedElementNames.size(); i++) {
                        if ("hello".equals(sharedElementNames.get(i))) {
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
                                ViewGroup container = (ViewGroup) findViewById(R.id.img_container);
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
        });
//TODO set shared element enter and return transitions!

    }

    @Override
    public void onBackPressed() {
        this.finishAfterTransition();
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        findViewById(R.id.thumbnail).setVisibility(View.VISIBLE);
    }
}
