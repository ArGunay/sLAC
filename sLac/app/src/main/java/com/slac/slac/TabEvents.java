package com.slac.slac;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slac.rss.ReadRss;


public class TabEvents extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        RecyclerView recyclerView;
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_tabs, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ReadRss readRss = new ReadRss(getActivity(), recyclerView);
        readRss.execute();

        return view;
    }

}
