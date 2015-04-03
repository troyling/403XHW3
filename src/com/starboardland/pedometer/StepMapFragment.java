package com.starboardland.pedometer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by ChandlerWu on 4/3/15.
 */
public class StepMapFragment extends SupportMapFragment{
    private static final String ARG_STEP_ID = "STEP_ID";

    private GoogleMap mGoogleMap;

    public static StepMapFragment newInstance(long setpID) {
        Bundle args = new Bundle();
        args.putLong(ARG_STEP_ID, setpID);
        StepMapFragment sf = new StepMapFragment();
        sf.setArguments(args);
        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        // Stash a reference to the GoogleMap
        mGoogleMap = getMap();
        // Show the user's location
        mGoogleMap.setMyLocationEnabled(true);

        return v;
    }
}
