package com.kogi.socialnetworks.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogi.socialnetworks.R;

/**
 *  Fragmento que carga la funcionalidad de Instagram
 */
public class InstagramFragment extends Fragment {

    public InstagramFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instagram_fragment, container, false);
    }

}
