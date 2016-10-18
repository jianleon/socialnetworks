package com.kogi.socialnetworks.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.widgets.InstagramLoginButton;
import com.kogi.socialnetworks.R;
import com.kogi.socialnetworks.Utils.BaseApplication;

/**
 * Clase encargada del inicio de sesión de Twitter e Instagram
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    /**
     * Inicialización del botón de Login de Instagram
     */
    @Override
    public void onStart() {
        super.onStart();
        String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.COMMENTS};

        InstagramLoginButton instagramLoginButton = (InstagramLoginButton) getActivity().findViewById(R.id.instagramLoginButton);
        instagramLoginButton.setInstagramLoginCallback(instagramLoginCallbackListener);
        instagramLoginButton.setScopes(scopes);
    }

    /**
     * Callback que recibe la respuesta de Autenticación en Instagram
     */
    InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
        @Override
        public void onSuccess(IGSession session) {
            BaseApplication.INSTAGRAM_USER_TOKEN = session.getAccessToken();
            BaseApplication.INSTAGRAM_USER_LOGGUED = true;
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "Oh Crap!!! Canceled.",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(InstagramException error) {
            Toast.makeText(getActivity(), "User does not trust you :(\n " + error.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    };
}
