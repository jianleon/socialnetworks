package com.kogi.socialnetworks.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.objects.IGMedia;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGUser;
import com.kogi.socialnetworks.R;
import com.kogi.socialnetworks.Utils.Helpers;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 *  Fragmento que carga la funcionalidad de Instagram
 */
public class InstagramFragment extends Fragment {

    public static final String TAG = InstagramFragment.class.getSimpleName();
    ImageView imgProfilePhoto;
    TextView txtSignOut;
    TextView txtFullName;
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
        txtSignOut = (TextView) getActivity().findViewById(R.id.sign_out);
        txtFullName = (TextView) getActivity().findViewById(R.id.user_name);
        txtMediaCount = (TextView) getActivity().findViewById(R.id.user_media_count);
        txtFollowersCount = (TextView) getActivity().findViewById(R.id.user_followers);
        txtFollowingCount = (TextView) getActivity().findViewById(R.id.user_following);

        txtSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstagramEngine.getInstance(getActivity()).logout(getActivity(), RESULT_OK);
                Helpers.setBooleanPreference(getActivity(), "INSTAGRAM_IS_LOGUED", false);
                Helpers.replaceFragment(getActivity(), R.id.content_main, new LoginFragment());
            }
        });
    }

    /**
     * Callback que recibe la información del usuario que se ha logueado
     */
    InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
        @Override
        public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {
            loadGeneralInfo(responseObject);
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.v(TAG, "Exception:" + exception.getMessage());
        }
    };

    InstagramAPIResponseCallback<ArrayList<IGMedia>> instagramMediaResponseCallback = new InstagramAPIResponseCallback<ArrayList<IGMedia>>() {
        @Override
        public void onResponse(ArrayList<IGMedia> responseObject, IGPagInfo pageInfo) {
            Log.e(TAG, "Id: " + responseObject.size());
        }

        @Override
        public void onFailure(InstagramException exception) {
            Log.e(TAG, "Exception: " + exception.getMessage());
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
        RequestCreator creator = Picasso.with(getActivity()).load(info.getProfilePictureURL());
        creator.into(imgProfilePhoto);

        txtFullName.setText(info.getFullName());
        txtMediaCount.setText(String.format("%s Media", info.getMediaCount()));
        txtFollowersCount.setText(String.format("%s Followers", info.getFollowedByCount()));
        txtFollowingCount.setText(String.format("%s Following", info.getFollowsCount()));

        InstagramEngine.getInstance(getActivity()).getUserLikedMedia(instagramMediaResponseCallback);
    }

}
