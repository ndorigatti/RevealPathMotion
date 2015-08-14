package it.ndorigatti.android.revealpathmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.Window;

public class StartingActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        findViewById(R.id.container).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //v.findViewById(R.id.card_row_thumb_feedback).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        StartingActivity.this,
                        Pair.create(v.findViewById(R.id.title_big), "bigtitle"),
                        Pair.create(v.findViewById(R.id.subtitle), "subtitle"),
                        Pair.create(v.findViewById(R.id.card_row_thumbnail), "thumbnail"),
                        Pair.create(v.findViewById(R.id.card_row_thumb_feedback), "hello")//CircularRevealImage
                         );

        Intent intent = new Intent(StartingActivity.this, EndingActivity.class);
        intent.putExtra("bigtitle", "Gazzetta Dello Sport");
        intent.putExtra("subtitle", "Sabato 13 Dicembre, 2014");
        intent.putExtra("status", false);
        ActivityCompat.startActivity(StartingActivity.this, intent, options.toBundle());
    }
}
