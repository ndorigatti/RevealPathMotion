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

package it.ndorigatti.android.revealpathmotion;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.ndorigatti.android.revealpathmotion.transitions.EnterSharedElementRevealAndTextCallback;
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

       // ImageView image = (ImageView) findViewById(R.id.thumbnail);
     //   ViewCompat.setTransitionName(image, "thumbnail");

        List<String> textNames = new ArrayList<>();
        textNames.add("bigtitle");
        textNames.add("subtitle");
        getWindow().setEnterTransition(TransitionUtils.makeEnterTransition());
        /** --------------------- ENTER TRANSITION ------------------------------ */
        //Get the "Text" transition
        Transition textTransition = TransitionUtils.makeSharedElementEnterTransition(textNames);
        textTransition.setDuration(900);
        //Get the "Reveal" transition
        Transition revealTransition = TransitionInflater.from(this).inflateTransition(R.transition.shared_element_enter);
        revealTransition.setDuration(800);
        //Create a set for both transitions
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        set.addTransition(revealTransition);
        set.addTransition(textTransition);
        //Set shared element enter transition
        getWindow().setSharedElementEnterTransition(set);
        /** --------------------- RETURN TRANSITION ------------------------------ */
        // Get the "Reveal" return transition
        Transition returnTransition = TransitionInflater.from(this).inflateTransition(R.transition.shared_element_return);
        returnTransition.setDuration(800);
        //Create a set for the return
        TransitionSet returnSet = new TransitionSet();
        returnSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        returnSet.addTransition(returnTransition);
        returnSet.addTransition(textTransition);
        getWindow().setSharedElementReturnTransition(returnSet);

        //---------------------- SHARED ELEMENT CALLBACK
        // setEnterSharedElementCallback(new EnterSharedElementTextCallback(this));// Use this for "TextSmooth" only
        // setEnterSharedElementCallback(new EnterSharedElementRevealCallback(this)); // Use this for "ImageReveal" only
        setEnterSharedElementCallback(new EnterSharedElementRevealAndTextCallback(this)); // this should animate both!
    }

    @Override
    public void onBackPressed() {
        this.finishAfterTransition();
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        findViewById(R.id.image_thumbnail).setVisibility(View.VISIBLE);
    }
}
