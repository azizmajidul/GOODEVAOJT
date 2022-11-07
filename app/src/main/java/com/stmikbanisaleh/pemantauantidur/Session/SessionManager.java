package com.stmikbanisaleh.pemantauantidur.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.stmikbanisaleh.pemantauantidur.Login;
import com.stmikbanisaleh.pemantauantidur.MainActivity;

public class SessionManager {

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static  final String PREF_NAME = "FRAGMENT";
    public static final String TOKEN = "acces_token";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static  final String LOGIN = "IS_LOGIN";
    public static  final String Telp = "tlp";
    public static final String Role = "role";


    public SessionManager(Context context){
        this.context= context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createSession(String nama, String email, String telp, String role ){
        editor.putBoolean(LOGIN, true);
        editor.putString(EMAIL, email);
        editor.putString(NAME, nama);
        editor.putString(Telp, telp);
        editor.putString(Role, role);
        editor.apply();
    }

    public boolean is_login(){
        return preferences.getBoolean(LOGIN, false);
    }
    public void chekLogin(){
        if(!this.is_login()){
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
            ((MainActivity)context).finish();
        }
    }

    public boolean saveStringData(String key, String value) {
        SharedPreferences.Editor editor=preferences.edit ();
        editor.putString (key, value);
        return editor.commit ();
    }

    public String getStringData(String key) {
        String value=preferences.getString (key, "");
        return value;
    }

}
