package com.kogi.socialnetworks.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;


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

    /**
     * Obtiene una preferencia booleana de la aplicación
     *
     * @param context : Contexto de la actividad/fragment
     * @param name    : Nombre de la preferencia
     * @return boolean : Valor de la preferencia
     */
    public static boolean getBooleanPreference(Context context, String name) {
        return context.getSharedPreferences(null, Context.MODE_PRIVATE)
                .getBoolean(name, false);
    }

    /**
     * Cambia el valor de una preferencia de tipo booleano
     *
     * @param context : Contexto de la Actividad/Fragment
     * @param name    : Nombre de la preferencia
     * @param value   : Valor a asignar
     */
    public static void setBooleanPreference(Context context, String name, boolean value) {
        context.getSharedPreferences(null, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(name, value)
                .apply();
    }

    /**
     * almacena un string en las preferencias
     *
     * @param context : Contexto de la Actividad/Fragment
     * @param name    : Nombre de la preferencia
     * @param value   : Valor a asignar
     */
    public static void setStringPreference(Context context, String name, String value) {
        context.getSharedPreferences(null, Context.MODE_PRIVATE)
                .edit()
                .putString(name, value)
                .apply();
    }

}
