package com.slac.slac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by User on 04/05/17.
 */

public class InfoPage02 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /*TextView link = (TextView) getView().findViewById(R.id.textView6);
        link.setMovementMethod(LinkMovementMethod.getInstance());*/

        View rootView = inflater.inflate(R.layout.info_page02, container, false);
        return rootView;
    }

}
