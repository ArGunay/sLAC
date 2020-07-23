package com.slac.slac;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Endgame extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);
        ImageView doBetter = (ImageView) findViewById(R.id.dobetter);
        ImageView congrats = (ImageView)findViewById(R.id.congrats);
        doBetter.setVisibility(View.GONE);
        congrats.setVisibility(View.GONE);
        Button toMap = (Button) findViewById(R.id.toMap);

        Intent i = getIntent();

        int score = Integer.parseInt(i.getStringExtra("Score"));

        TextView tv = (TextView) findViewById(R.id.message);

        tv.setText("You've earned: " + score + " points!");

        if(score < 3 ){
            doBetter.setVisibility(View.VISIBLE);
        } else{
            congrats.setVisibility(View.VISIBLE);
        }

        toMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();

            }
        });
    }


}
