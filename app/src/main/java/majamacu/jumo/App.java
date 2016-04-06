package majamacu.jumo;


import android.content.Intent;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by marti on 9/9/2015.
 */

public class App extends AppCompatActivity {
    String baseDatos, baseDatos2, baseDatos3, baseDatos4, baseDatos5, baseDatos6;

    Init appState;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        appState = ((Init) getApplicationContext());

        baseDatos = "Retos";
        baseDatos2 = "RetosTodos";
        baseDatos3 = "RetosPersonales";
        baseDatos4 = "RetosEn";
        baseDatos5 = "RetosTodosEn";
        baseDatos6 = "RetosPersonalesEn";


        ParseQuery<ParseObject> query = ParseQuery.getQuery(baseDatos);

        query.whereEqualTo("esReto", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(object);
                } else {

                }
            }
        });

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery(baseDatos2);

        query2.whereEqualTo("esReto", false);
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(object);
                } else {

                }
            }
        });

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery(baseDatos3);

        query3.whereEqualTo("esReto", false);
        query3.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(object);
                } else {

                }
            }
        });

        ParseQuery<ParseObject> query4 = ParseQuery.getQuery(baseDatos4);

        query4.whereEqualTo("esReto", false);
        query4.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(object);
                } else {

                }
            }
        });

        ParseQuery<ParseObject> query5 = ParseQuery.getQuery(baseDatos5);

        query5.whereEqualTo("esReto", false);
        query5.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(object);
                } else {

                }
            }
        });

        ParseQuery<ParseObject> query6 = ParseQuery.getQuery(baseDatos6);

        query6.whereEqualTo("esReto", false);
        query6.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(object);
                } else {

                }
            }
        });


        Thread timerThread = new Thread() {
            public void run() {
                try {

                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(App.this, Menu.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();


    }




}


