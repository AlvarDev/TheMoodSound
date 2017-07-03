package com.alvardev.listento.bases;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;

/**
 * Created by alvardev on 21/05/17.
 * To manage all Activities
 */

public class BaseAppCompatActivity extends AppCompatActivity {

    private static final String NAME_PREFERENCE = "com.alvardev.listento.bases.preferences";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    /**
     * To change Activity
     *
     * @param context Activity/Fragment origin
     * @param cls     Activity destination
     * @param view    (ImageView) that receive the animation
     * @param bundle  data sent to Activity
     *                <p>
     *                NOTE: if you send an View (ImageView) to the other Activity,
     *                you should use validateTransitions(View view) in the new Activity (onCreate method)
     **/
    protected void moveToActivity(Context context, Class<?> cls, View view, Bundle bundle, boolean explode) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtra("extra", bundle);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(explode){
                getWindow().setExitTransition(new Explode());
            }
            ActivityOptions options = view != null ?
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context, view, "view") :
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
        }


    }

    protected void explodeToActivity(Context context, Class<?> cls, View view, Bundle bundle){
        moveToActivity(context, cls, view, bundle, true);
    }

    protected void explodeToActivity(Context context, Class<?> cls, View view) {
        moveToActivity(context, cls, view, null, true);
    }

    protected void explodeToActivity(Context context, Class<?> cls) {
        moveToActivity(context, cls, null, null, true);
    }


    /*****/
    protected void keepViewToActivity(Context context, Class<?> cls, View view, Bundle bundle){
        moveToActivity(context, cls, view, bundle, false);
    }

    protected void keepViewToActivity(Context context, Class<?> cls, View view) {
        moveToActivity(context, cls, view, null, false);
    }

    protected void keepViewToActivity(Context context, Class<?> cls) {
        moveToActivity(context, cls, null, null, false);
    }

    protected void saveCurrentUser(String user) {
        SharedPreferences preferences = this.getSharedPreferences(NAME_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentUser", user);
        editor.apply();
    }

    protected String getCurrentUser() {
        SharedPreferences preferences = this.getSharedPreferences(NAME_PREFERENCE, MODE_PRIVATE);
        return preferences.getString("currentUser", "");
    }

}