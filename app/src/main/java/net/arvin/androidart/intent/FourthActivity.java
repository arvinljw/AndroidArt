package net.arvin.androidart.intent;

import android.content.Intent;
import android.os.Bundle;

public class FourthActivity extends SecondActivity {

    @Override
    protected String getActivityName() {
        return "FourthActivity";
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
    }
}
