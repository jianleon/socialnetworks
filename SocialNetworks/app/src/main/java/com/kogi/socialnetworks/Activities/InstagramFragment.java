package com.kogi.socialnetworks.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGUser;
import com.kogi.socialnetworks.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 *  Fragmento que carga la funcionalidad de Instagram
 */
public class InstagramFragment extends Fragment {

    ImageView imgProfilePhoto;
    TextView txtMediaCount;
    TextView txtFollowersCount;
    TextView txtFollowingCount;

    public InstagramFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        InstagramEngine.getInstance(getActivity()).getUserDetails(instagramUserResponseCallback);
        return inflater.inflate(R.layout.instagram_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        imgProfilePhoto = (ImageView) getActivity().findViewById(R.id.user_profile_photo);
        txtMediaCount = (TextView) getActivity().findViewById(R.id.user_media_count);
        txtFollowersCount = (TextView) getActivity().findViewById(R.id.user_followers);
        txtFollowingCount = (TextView) getActivity().findViewById(R.id.user_following);
    }

    InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
        @Override
        public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {
            loadGeneralInfo(responseObject);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v("SampleActivity", "Exception:" + exception.getMessage());
        }
    };

    /**
     * Carga la información más general del usuario en la UI
     *
     * * Foto de perfil
     * * Archivos media
     * * Cantidad de Seguidores
     * * Cantidad de persona que el usuario sigue
     *
     *
     * @param info  Objeto que contiene la información del usuario
     */
    private void loadGeneralInfo(IGUser info) {
        Picasso picasso = Picasso.with(getActivity());
        RequestCreator creator = picasso.load(info.getProfilePictureURL());
        creator.into(imgProfilePhoto);

        txtMediaCount.setText(String.format("%s Media", info.getMediaCount()));
        txtFollowersCount.setText(String.format("%s Followers", info.getFollowedByCount()));
        txtFollowingCount.setText(String.format("%s Following", info.getFollowsCount()));
    }

}
