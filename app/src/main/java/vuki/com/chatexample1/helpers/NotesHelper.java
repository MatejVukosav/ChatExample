package vuki.com.chatexample1.helpers;

import android.util.Log;

import vuki.com.chatexample1.BuildConfig;

/**
 * Created by mvukosav on 5.8.2016..
 */
public class NotesHelper {

    public static void log( String TAG, String body ) {
        Log.d( TAG, "" + body );
    }

    public static void logMessage( String tag, String body) {
        if( BuildConfig.DEBUG) {
            if( body != null ) {
                Log.d( tag, body );
            } else {
                Log.d( tag, "NULL" );
            }
        }
    }

}
