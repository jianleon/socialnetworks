package com.kogi.socialnetworks.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 *  Clase de utilidades para la aplicación
 */

public class Helpers {

    /**
     * Reemplazo de Fragments en una vista previamente cargada
     *
     * @param context   Referencia de la actividad/fragmento de donde proviene la acción
     * @param layoutId  ID de la vista donde se cargará el Fragment
     * @param fragment  Fragment a cargar en la vista
     */
    public static void replaceFragment(Activity context, int layoutId, Fragment fragment) {
        FragmentManager fragmentManager = context.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment);
        fragmentTransaction.commit();
    }

}
