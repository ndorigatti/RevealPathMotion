package it.ndorigatti.android.revealpathmotion.transitions;

import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.animation.AccelerateInterpolator;

import java.util.List;

public final class TransitionUtils {

    /**
     * Returns a modified enter transition that excludes the navigation bar and status
     * bar as targets during the animation. This ensures that the navigation bar and
     * status bar won't appear to "blink" as they fade in/out during the transition.
     */
    public static Transition makeEnterTransition() {
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        return fade;
    }

    public static Transition makeSharedElementEnterTransition(List<String> textTransitionNames,
                                                              List<String> imageNames) {
        TransitionSet set = new TransitionSet();
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);

        Transition recolor = new Recolor();
        Transition changeBounds = new ChangeBounds();
        Transition textSize = new TextSizeTransition();

        for (String textTName : textTransitionNames) {
            changeBounds.addTarget(textTName);
            textSize.addTarget(textTName);
            recolor.addTarget(textTName);
        }

        set.addTransition(recolor);
        set.addTransition(changeBounds);
        set.addTransition(textSize);


        Transition imageTransf = new ChangeImageTransform();
        Transition imageClip = new ChangeClipBounds();
        Transition changeTransform = new ChangeTransform();
        for (String imgName : imageNames) {
            changeBounds.addTarget(imgName);
            imageTransf.addTarget(imgName);
            imageClip.addTarget(imgName);
            changeTransform.addTarget(imgName);
        }

        changeBounds.setInterpolator(new AccelerateInterpolator());

        set.addTransition(imageTransf);
        set.addTransition(imageClip);
        set.addTransition(changeTransform);


        ///set.setDuration(3000);
        return set;
    }

    private TransitionUtils() {
    }
}
