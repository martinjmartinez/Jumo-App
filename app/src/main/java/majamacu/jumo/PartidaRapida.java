package majamacu.jumo;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;

import android.widget.ImageView;

import android.widget.TextSwitcher;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Random;

public class PartidaRapida extends AppCompatActivity {

    //variables para el mainlayout
    TextSwitcher tituloText;
    TextSwitcher nombreText;
    TextView puntosJugador;
    TextSwitcher text;
    ImageView iconoplayer;
    Vibrator vibrator;
    //variables para los popup del main layout
    View papa;
    Button next;
    boolean continua;

    InterstitialAd anuncio;

    String titulo, descripcion;
    int puntos;
    ArrayList<Integer> retos;
    String baseDatos;

    App app;

    int reto;
    int cantRetos;
    Init appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        app = new App();
        appState = ((Init) getApplicationContext());
        anuncio = new InterstitialAd(this);
        anuncio.setAdUnitId("ca-app-pub-1101630221960337/1721735241");
        requestNewInterstitial();

        retos = new ArrayList<Integer>();

        if (appState.myLocale.getLanguage().equals("es")) {
            baseDatos = "RetosTodos";
        } else {
            baseDatos = "RetosTodosEn";
        }

        continua = false;

        //inicializacion popup


        papa = (View) findViewById(R.id.papa);
        next = (Button) findViewById(R.id.next);
        tituloText = (TextSwitcher) findViewById(R.id.titulo);
        nombreText = (TextSwitcher) findViewById(R.id.jugador);
        puntosJugador = (TextView) findViewById(R.id.sorbos);
        text = (TextSwitcher) findViewById(R.id.descripcion1);
        iconoplayer = (ImageView) findViewById(R.id.iconoplayer);


//codigo para los textswitcher
        TextView texto = new TextView(this);
        TextView texto2 = new TextView(this);
        texto.setTextAppearance(getApplicationContext(),
                R.style.AudioFileInfoOverlayText);
        texto.setGravity(Gravity.CENTER);
        texto2.setGravity(Gravity.CENTER);
        texto2.setTextAppearance(getApplicationContext(),
                R.style.AudioFileInfoOverlayText);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");
        texto.setTypeface(custom_font);
        texto2.setTypeface(custom_font);
        tituloText.addView(texto);
        tituloText.addView(texto2);

        TextView texto3 = new TextView(this);
        TextView texto4 = new TextView(this);
        texto3.setTextSize(20f);
        texto3.setTextAppearance(getApplicationContext(),
                R.style.AudioFileInfoOverlayText3);
        texto4.setTextAppearance(getApplicationContext(),
                R.style.AudioFileInfoOverlayText3);
        texto3.setGravity(Gravity.CENTER);
        texto4.setGravity(Gravity.CENTER);
        texto4.setTextColor(Color.BLACK);
        texto4.setTextSize(20f);
        texto3.setTextColor(Color.BLACK);
        text.addView(texto3);
        text.addView(texto4);

        TextView texto5 = new TextView(this);
        TextView texto6 = new TextView(this);
        texto5.setTextSize(20f);
        texto6.setTextColor(Color.BLACK);
        texto5.setGravity(Gravity.CENTER_VERTICAL);
        texto6.setGravity(Gravity.CENTER_VERTICAL);
        texto5.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
        texto6.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
        texto6.setTextSize(20f);
        texto5.setTextColor(Color.BLACK);
        nombreText.addView(texto5);
        nombreText.addView(texto6);


        contar();
        resetlista(cantRetos);
        anuncio.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

            }
        });
        getInfo();


    }


    public void siguiente(View view) {


        if (retos.size() < 10) {
            retos.clear();
            resetlista(cantRetos);
            getInfo();
        } else
            getInfo();

    }


    public void getInfo() {

        reto = randomInteger(0, retos.size() - 1);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(baseDatos);
        query.fromLocalDatastore();
        query.whereEqualTo("Id", retos.get(reto));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    getInfo();
                } else {
                    titulo = object.getString("Titulo");
                    descripcion = object.getString("Descripcion");
                    puntos = object.getInt("puntos");
                    retos.remove(reto);
                    jugada(titulo, descripcion, puntos);

                    //continua=true;
                }
            }
        });

        // return continua;
    }

    public void jugada(String titulo, String descripcion, int puntos) {


        tituloText.setInAnimation(this, R.anim.push_down_in);
        tituloText.setOutAnimation(this, R.anim.push_down_out);
        TextView  theme =(TextView)tituloText.getChildAt(tituloText.getDisplayedChild());
        if(puntos<=3){

            theme.setTextColor(getResources().getColor(R.color.azul));

        }else if(puntos>3 && puntos<=6){
            theme.setTextColor(getResources().getColor(R.color.verde));

        }else if(puntos>6){
            vibrator.vibrate(1000);
            theme.setTextColor(getResources().getColor(R.color.rojo));


        }
        text.setInAnimation(this, R.anim.fadein);
        text.setOutAnimation(this, R.anim.fadeout);
        nombreText.setInAnimation(this, R.anim.fadein);
        nombreText.setOutAnimation(this, R.anim.fadeout);
        nombreText.setText(getText(R.string.Todos));
        iconoplayer.setImageResource(R.drawable.todos);

        tituloText.setText(titulo);

        puntosJugador.setText("+" + puntos);
        text.setText(descripcion);

        int chance = randomInteger(0, 100);

        if (chance > 90) {


                if (anuncio.isLoaded()) {
                    anuncio.show();
                }

        }
    }



    public int randomInteger(int min, int max) {

        Random rand = new Random();

        // nextInt excludes the top value so we have to add 1 to include the top value


        return rand.nextInt((max - min) + 1) + min;
    }




    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .setGender(AdRequest.GENDER_UNKNOWN)
                .build();

        anuncio.loadAd(adRequest);
    }

    public void contar() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(baseDatos);
        query.fromLocalDatastore();
        try {
            cantRetos = query.count();
        } catch (ParseException e) {
            contar();
        }


    }


    public void resetlista(int cantRetos) {
        for (int i = 1; i <= cantRetos; i++) {
            retos.add(i);
        }
    }


}

