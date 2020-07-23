package com.slac.slac;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slac.quiz.Artwork;

import java.util.ArrayList;


public class Quiz extends AppCompatActivity
{
    private static final String TAG = "ViewDatabase";
    private Artwork currentArtwork;
    private int q;
    private int score;
    private int x;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView tv = (TextView) findViewById(R.id.QuestionText);

        this.x = (int) Math.ceil(Math.random() * 4);



        Intent i = getIntent();

        q = Integer.parseInt(i.getStringExtra("Q"));
        score = Integer.parseInt(i.getStringExtra("Score"));
        currentArtwork = new Artwork(
                Integer.parseInt(i.getStringExtra("BeaconID")),
                i.getStringExtra("Author"),
                i.getStringExtra("Name"),
                Integer.parseInt(i.getStringExtra("Year")),
                i.getStringExtra("Place")
                );

        Button answer1 = (Button) findViewById(R.id.Answer1);
        Button answer2 = (Button) findViewById(R.id.Answer2);
        Button answer3 = (Button) findViewById(R.id.Answer3);
        Button answer4 = (Button) findViewById(R.id.Answer4);

        switch (q)
        {
            case 1:
                tv.setText("chi è l'autore dell'opera " + currentArtwork.getName() + "?");
                if (x ==1 )
                    answer1.setText(currentArtwork.getAuthor());
                else
                    answer1.setText("Michelangelo");

                if (x == 2 )
                    answer2.setText(currentArtwork.getAuthor());
                else
                    answer2.setText("Botticelli");

                if (x == 3 )
                    answer3.setText(currentArtwork.getAuthor());
                else
                    answer3.setText("Giotto");

                if (x == 4 )
                    answer4.setText(currentArtwork.getAuthor());
                else
                    answer4.setText("Raffaello");
                break;
            case 2:
                tv.setText("In che anno è stata fatta?");

                if (x ==1 )
                    answer1.setText(Integer.toString(currentArtwork.getYearOfCreation()));
                else
                    answer1.setText(Integer.toString(1908));

                if (x == 2 )
                    answer2.setText(Integer.toString(currentArtwork.getYearOfCreation()));
                else
                    answer2.setText(Integer.toString(1763));

                if (x == 3 )
                    answer3.setText(Integer.toString(currentArtwork.getYearOfCreation()));
                else
                    answer3.setText(Integer.toString(1597));

                if (x == 4 )
                    answer4.setText(Integer.toString(currentArtwork.getYearOfCreation()));
                else
                    answer4.setText(Integer.toString(1859));

                break;

            case 3:
                tv.setText("Dove è stata fatta?");

                if (x ==1 )
                    answer1.setText(currentArtwork.getPlaceOfCreation());
                else
                    answer1.setText("Parigi");

                if (x == 2 )
                    answer2.setText(currentArtwork.getPlaceOfCreation());
                else
                    answer2.setText("Londra");

                if (x == 3 )
                    answer3.setText(currentArtwork.getPlaceOfCreation());
                else
                    answer3.setText("Venezia");

                if (x == 4 )
                    answer4.setText(currentArtwork.getPlaceOfCreation());
                else
                    answer4.setText("Colonia");
                break;
        }



        answer1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Quiz.this.onClick(1);
            }
        });

        answer2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Quiz.this.onClick(2);

            }
        });

        answer3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Quiz.this.onClick(3);

            }
        });

        answer4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Quiz.this.onClick(4);

            }
        });



        /*
        tv.setText("Opera nr." + currentArtwork.getBeaconID() + ",\n" + currentArtwork.getName() + ", di "+currentArtwork.getAuthor() +
                "\nCreata nell'anno " + currentArtwork.getYearOfCreation() + " a " + currentArtwork.getPlaceOfCreation());
        */
        /*
        tv.setText("Opera nr." + i.getStringExtra("BeaconID") + ",\n" + i.getStringExtra("Name") + ", di "+i.getStringExtra("Author") +
                "\nCreata nell'anno " + i.getStringExtra("year") + " a " + i.getStringExtra("Place"));
        */

    }


    public void onClick(int answerGiven)
    {
        if (answerGiven == x)
            score++;

        if (q<3)
        {
            q++;

            Intent intent = new Intent(this, Quiz.class);
            intent.putExtra("BeaconID", Integer.toString(currentArtwork.getBeaconID()));
            //intent.putExtra("numero opere", Integer.toString(artworks.size()));
            intent.putExtra("Score", Integer.toString(score));
            intent.putExtra("Q", Integer.toString(q));
            intent.putExtra("Name", currentArtwork.getName());
            intent.putExtra("Author", currentArtwork.getAuthor());
            intent.putExtra("Year", Integer.toString(currentArtwork.getYearOfCreation()));
            intent.putExtra("Place", currentArtwork.getPlaceOfCreation());
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, Endgame.class);

            intent.putExtra("Score", Integer.toString(score));

            startActivity(intent);


        }
    }

}