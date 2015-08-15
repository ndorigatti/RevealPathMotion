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
                       // Pair.create(v.findViewById(R.id.card_row_thumbnail), "thumbnail"),
                        Pair.create(v.findViewById(R.id.card_row_thumb_feedback), "reveal")//CircularRevealImage
                         );

        Intent intent = new Intent(StartingActivity.this, EndingActivity.class);
        intent.putExtra("bigtitle", "Gazzetta Dello Sport");
        intent.putExtra("subtitle", "Sabato 13 Dicembre, 2014");
        ActivityCompat.startActivity(StartingActivity.this, intent, options.toBundle());
    }
}
