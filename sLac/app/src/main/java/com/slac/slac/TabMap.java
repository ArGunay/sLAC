package com.slac.slac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by User on 04/05/17.
 */

public class TabMap extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_map, container, false);

        ImageButton button = (ImageButton) rootView.findViewById(R.id.image_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToMap();
            }
        });

        return rootView;
    }


    public void goToMap()
    {
        Intent intent = new Intent(getActivity(), Map.class);
        startActivity(intent);

    }
}
