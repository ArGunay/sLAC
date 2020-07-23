

package com.slac.slac;



import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slac.quiz.Artwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.UUID;

public class Map extends AppCompatActivity{

    private FirebaseDatabase mFirebaseDatabase;


    private DatabaseReference myRef;

    private ArrayList<Artwork> artworks;

    private static final java.util.Map<String, List<String>> PLACES_BY_BEACONS;

    // those are the list of the beacons with a message
    static
    {
        java.util.Map<String, List<String>> placesByBeacons = new HashMap<>();

        // Beacon number 1
        placesByBeacons.put("18043:14894", new ArrayList<String>(){{
            add("beacon verde aqua");
        }});
        // Beacon Number 2
        placesByBeacons.put("41126:52000", new ArrayList<String>(){{
            add("beacon viola D43");

        }});

        // Beacon number 3
        placesByBeacons.put("25101:58924", new ArrayList<String>() {{
            add("beacon azure");
        }});

        // Beacon Test
        placesByBeacons.put("14434:566", new ArrayList<String>(){{
            add("beacon viola2 D33");

        }});

//        placesByBeacons.put("648:22", new ArrayList<String>() {{
//            add("beacon azure");
//            add("beacon verde aqua");
//            add("beacon viola D43");
//        }});
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }

    private BeaconManager beaconManager;
    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        artworks = new ArrayList<>();

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        myRef = mFirebaseDatabase.getReference().child("artworks");


        myRef.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds1 : dataSnapshot.getChildren())
                {
                    int currentBeaconID = 0;
                    String currentArtworkName = "";
                    String currentArtworkAuthor = "";
                    int currentYear = 0;
                    String currentPlace = "";


                    for (DataSnapshot ds : ds1.getChildren() )
                    {
                        switch(ds.getKey())
                        {
                            case "beaconID":
                                currentBeaconID = Integer.parseInt(ds.getValue(String.class));
                                break;
                            case "author":
                                currentArtworkAuthor = ds.getValue(String.class);
                                break;
                            case "name":
                                currentArtworkName = ds.getValue(String.class);
                                break;
                            case "year":
                                currentYear = Integer.parseInt(ds.getValue(String.class));
                                break;
                            case "place":
                                currentPlace = ds.getValue(String.class);
                                break;

                        }

                    }


                    Artwork uInfo = new Artwork(currentBeaconID, currentArtworkAuthor, currentArtworkName, currentYear, currentPlace);

                    Map.this.artworks.add(uInfo);
                    Log.d("Artworks","" + Map.this.artworks.size());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });



        ImageButton b1 = (ImageButton) findViewById(R.id.buttonB1);
        ImageButton b2 = (ImageButton) findViewById(R.id.buttonB2);
        ImageButton b3 = (ImageButton) findViewById(R.id.buttonB3);

        b1.setVisibility(View.INVISIBLE);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToQuiz(25101);
            }
        });
        b2.setVisibility(View.INVISIBLE);
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToQuiz(41126);
            }
        });
        b3.setVisibility(View.INVISIBLE);
        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToQuiz(18043);
            }
        });

        beaconManager = new BeaconManager(this);
        //beaconManager.setBackgroundScanPeriod(1000, 0);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list)
            {
                Log.d("beacon rilevati: " + list.size(), " ");

                if (!list.isEmpty())
                {
                    Utils utils = new Utils();


                    Beacon nearestBeacon = list.get(0);

                    List<String> places = placesNearBeacon(nearestBeacon);

                    ImageView pin1 = (ImageView) findViewById(R.id.pin1);
                    ImageView pin2 = (ImageView) findViewById(R.id.pin2);
                    ImageView pin3 = (ImageView) findViewById(R.id.pin3);

                    ImageButton b1 = (ImageButton) findViewById(R.id.buttonB1);
                    ImageButton b2 = (ImageButton) findViewById(R.id.buttonB2);
                    ImageButton b3 = (ImageButton) findViewById(R.id.buttonB3);


                    Log.d("BeacNear", ": "+nearestBeacon);
                    Log.d("BeacRSSI", ": " + nearestBeacon.getRssi());
                    Log.d("BeacMajor", ": "+ nearestBeacon.getMajor());
                    //Log.d("BeacPlaces",": " + places.get(0));
                    Log.d("BeacMac",": " + nearestBeacon.getMacAddress());

                    if (utils.computeAccuracy(nearestBeacon) <= 1.5)
                    {
                        Log.d("Beacon located",": ");

                        switch (nearestBeacon.getMajor())
                        {
                            case 25101:
                                //Toast.makeText(Map.this, "Located beacon "+25101, Toast.LENGTH_SHORT).show();

                                pin1.animate().alpha(1).setDuration(100);
                                pin2.animate().alpha(0).setDuration(300);
                                pin3.animate().alpha(0).setDuration(300);

                                b1.setVisibility(View.VISIBLE);
                                b2.setVisibility(View.INVISIBLE);
                                b3.setVisibility(View.INVISIBLE);

                                break;

                            case 41126:
                                //Toast.makeText(Map.this, "Located beacon "+41126, Toast.LENGTH_SHORT).show();

                                pin1.animate().alpha(0).setDuration(300);
                                pin2.animate().alpha(1).setDuration(100);
                                pin3.animate().alpha(0).setDuration(300);

                                b1.setVisibility(View.INVISIBLE);
                                b2.setVisibility(View.VISIBLE);
                                b3.setVisibility(View.INVISIBLE);

                                break;

                            case 18043:

                                //Toast.makeText(Map.this, "Located beacon "+18043, Toast.LENGTH_SHORT).show();

                                pin1.animate().alpha(0).setDuration(300);
                                pin2.animate().alpha(0).setDuration(300);
                                pin3.animate().alpha(1).setDuration(100);

                                b1.setVisibility(View.INVISIBLE);
                                b2.setVisibility(View.INVISIBLE);
                                b3.setVisibility(View.VISIBLE);

                                break;

                            default:
                                //Toast.makeText(Map.this, "Beacon not identified", Toast.LENGTH_SHORT).show();

                                pin1.animate().alpha(0).setDuration(300);
                                pin2.animate().alpha(0).setDuration(300);
                                pin3.animate().alpha(0).setDuration(300);

                                b1.setVisibility(View.INVISIBLE);
                                b2.setVisibility(View.INVISIBLE);
                                b3.setVisibility(View.INVISIBLE);

                        }
                    }
                    else
                    {
                        pin1.animate().alpha(0).setDuration(300);
                        pin2.animate().alpha(0).setDuration(100);
                        pin3.animate().alpha(0).setDuration(300);
                        b1.setVisibility(View.INVISIBLE);
                        b2.setVisibility(View.INVISIBLE);
                        b3.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
    }

    protected void goToQuiz(int beaconId)
    {
        Toast.makeText(Map.this, "goingToQuiz from " + beaconId, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Quiz.class);
        Artwork currentArtwork = null;
        for (Artwork at : artworks)
        {
            if (at.getBeaconID()==beaconId)
            {
                currentArtwork = at;
            }
        }

        intent.putExtra("BeaconID", Integer.toString(beaconId));
        //intent.putExtra("numero opere", Integer.toString(artworks.size()));

        intent.putExtra("Q", Integer.toString(1));
        intent.putExtra("Score", Integer.toString(0));
        intent.putExtra("Name", currentArtwork.getName());
        intent.putExtra("Author", currentArtwork.getAuthor());
        intent.putExtra("Year", Integer.toString(currentArtwork.getYearOfCreation()));
        intent.putExtra("Place", currentArtwork.getPlaceOfCreation());
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }


}
