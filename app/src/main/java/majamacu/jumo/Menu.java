package majamacu.jumo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Menu extends AppCompatActivity {


    String baseDatos;
    public List<Jugador> myList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/JUMOAPP";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://instagram.com/jumoapp/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        findViewById(R.id.twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/JUMOAPP";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.webpage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://jumoapp.com";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        if (Locale.getDefault().getLanguage().equals("es")) {
            baseDatos = "Retos";
        } else {
            baseDatos = "RetosEn";
        }


    }


    public void jugarRapido(View view) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery(baseDatos);
        query.fromLocalDatastore();
        query.whereEqualTo("Id", 1);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                    builder.setTitle(R.string.title) //
                            .setMessage(R.string.message) //
                            .setPositiveButton(getString(R.string.positive), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    System.exit(0);
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                } else {


                    Intent intent = new Intent(Menu.this, PartidaRapida.class);
                    intent.putExtra("PartidaRapida", true);
                    Menu.this.startActivityForResult(intent, 0);

                }

            }

        });


    }

    public void jugar(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(baseDatos);
        query.fromLocalDatastore();
        query.whereEqualTo("Id", 1);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                    builder.setTitle(R.string.title) //
                            .setMessage(R.string.message) //
                            .setPositiveButton(getString(R.string.positive), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    System.exit(0);
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                } else {
                    Intent intent = new Intent(Menu.this, Jugadores.class);

                    Menu.this.startActivityForResult(intent, 0);
                }

            }

        });


    }

    public void opciones(View view) {

        Intent intent = new Intent(Menu.this, Opciones.class);
        Menu.this.startActivityForResult(intent, 0);

    }




    public void onBackPressed() {
        System.exit(0);

    }


}
