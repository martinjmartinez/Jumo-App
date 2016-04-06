package majamacu.jumo;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ironsource.mobilcore.AdUnitEventListener;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.UserProperties;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Random;

public class JugadaNormal extends AppCompatActivity {

    //variables para el mainlayout
    TextSwitcher tituloText;
    TextSwitcher nombreText;
    TextView puntosJugador;
    TextSwitcher text;
    ImageView iconoplayer;
    private static final Integer[] emoticons = {R.drawable.mf, R.drawable.masculino, R.drawable.femenino};

    //variables para los popup del main layout
    LayoutInflater layoutInflater;
    View popupView, popupView2, popupView3,popupView4;
    PopupWindow popupWindow, popupWindow2, popupWindow3,popupWindow4;
    ImageView addPlayer, tablero,sexbtn;
    ImageView add;
    ListView listajugadores;
    EditText nombreNuevo;
    TextView nombreGanador;
    View papa;
    LinearLayout sexoView;
    Button next,skip;
    boolean isHombre,selec;
    boolean continua;
    Vibrator vibrator;

    InterstitialAd anuncio;

    //jugador en juego
    int jugador = 0;

    //numero que indica el color del jugador
    int jugador2 = 0;

    //lista de todos los jugadores
    Init appState;

    //el jugador que estaba jugando y el que esta en juego
    Jugador prev, now;
    boolean todos,cachondo;
    String titulo, descripcion;
    int puntos, cantMaxSorbos;
    int[] iconosJugadores;
    ArrayList<Integer>retos;
    String baseDatos;


    int reto;
    int cantRetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileCore.init(this, "HU5WQV7V4FT1GUNWA11OOKSRX1YL", new UserProperties().setGender(UserProperties.Gender.BOTH).setAgeRange(18, 45), MobileCore.LOG_TYPE.DEBUG, MobileCore.AD_UNITS.INTERSTITIAL, MobileCore.AD_UNITS.STICKEEZ);
        setContentView(R.layout.activity_main);

        appState= ((Init)getApplicationContext());
        anuncio = new InterstitialAd(this);
        anuncio.setAdUnitId("ca-app-pub-1101630221960337/1721735241");
        requestNewInterstitial();

        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        retos=new ArrayList<Integer>();

        if(appState.myLocale.getLanguage().equals("es")){
            if(appState.isPersonal() && appState.isGrupal() && !appState.isCachondo()){
                baseDatos="Retos";
            }else if(appState.isPersonal() && !appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosPersonales";
            }else if(!appState.isPersonal() && appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosTodos";
            }else if(!appState.isPersonal() && !appState.isGrupal() && appState.isCachondo()){
                baseDatos="RetosCachondos";
            }/*else if(appState.isPersonal() && appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosPG";
            }else if(appState.isPersonal() && !appState.isGrupal() && appState.isCachondo()){
                baseDatos="RetosPC";
            }else if(!appState.isPersonal() && appState.isGrupal() && appState.isCachondo()) {
                baseDatos = "RetosGC";
            }*/
            else{
                baseDatos="Retos";
            }

        }else{

            if(appState.isPersonal() && appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosEn";
            }else if(appState.isPersonal() && !appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosPersonalesEn";
            }else if(!appState.isPersonal() && appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosTodosEn";
            }else if(!appState.isPersonal() && !appState.isGrupal() && appState.isCachondo()){
                baseDatos="RetosCachondosEn";
            }/*else if(appState.isPersonal() && appState.isGrupal() && !appState.isCachondo()){
                baseDatos="RetosPGEn";
            }else if(appState.isPersonal() && !appState.isGrupal() && appState.isCachondo()){
                baseDatos="RetosPCEn";
            }else if(!appState.isPersonal() && appState.isGrupal() && appState.isCachondo()) {
                baseDatos = "RetosGCEn";
            }*/
            else{
                baseDatos="RetosEn";
            }
        }

        continua = false;

        //se recogen los datos enviados por el menuactivity
        iconosJugadores = getIntent().getIntArrayExtra("iconosJugadores");
        cantMaxSorbos = getIntent().getIntExtra("maxSorbos", 0);
        jugador = getIntent().getIntExtra("jugador", 0);




        //inicializacion popup
        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = layoutInflater.inflate(R.layout.agregarjugador, null);
        popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        popupView2 = layoutInflater.inflate(R.layout.tablero, null);
        popupWindow2 = new PopupWindow(popupView2, ViewGroup.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        popupView3 = layoutInflater.inflate(R.layout.ganador, null);
        popupWindow3 = new PopupWindow(popupView3, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        popupView4 = layoutInflater.inflate(R.layout.sexos, null);
        popupWindow4 = new PopupWindow(popupView4, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        jugador2 = appState.getMyList().size();
        papa = (View) findViewById(R.id.papa);
        next=(Button)findViewById(R.id.next);
        skip=(Button)findViewById(R.id.skip);
        addPlayer = (ImageView) findViewById(R.id.agregar);
        sexbtn = (ImageView)popupView.findViewById(R.id.sexo);
        listajugadores = (ListView) popupView2.findViewById(R.id.listView);
        tablero = (ImageView) findViewById(R.id.tablero);
        add = (ImageView) popupView.findViewById(R.id.agregar2);
        nombreNuevo = (EditText) popupView.findViewById(R.id.nombrenuevo);
        nombreGanador = (TextView) popupView3.findViewById(R.id.nombre);
        tituloText = (TextSwitcher) findViewById(R.id.titulo);
        nombreText = (TextSwitcher) findViewById(R.id.jugador);
        puntosJugador = (TextView) findViewById(R.id.sorbos);
        text = (TextSwitcher) findViewById(R.id.descripcion1);
        iconoplayer = (ImageView) findViewById(R.id.iconoplayer);
        sexoView = (LinearLayout)popupView.findViewById(R.id.sexolay);


//codigo para los textswitcher
        TextView texto = new TextView(this);
        TextView texto2 = new TextView(this);
        texto.setTextAppearance(getApplicationContext(),
                R.style.AudioFileInfoOverlayText);
        texto.setGravity(Gravity.CENTER);
        texto2.setGravity(Gravity.CENTER);
        texto2.setTextAppearance(getApplicationContext(),
                R.style.AudioFileInfoOverlayText);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/manteka.ttf");
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




        now = appState.getMyList().get(jugador);
        prev = appState.getMyList().get(jugador);

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


        if(retos.size()<10){
            retos.clear();
            resetlista(cantRetos);
            getInfo();
        }else
            getInfo();

        if (!appState.getMyList().isEmpty()) {
            jugador = (jugador) % appState.getMyList().size();
            now = appState.getMyList().get(jugador);
            if (now.equals(prev)&& todos==false) {
                appState.getMyList().get(jugador).setPuntos(appState.getMyList().get(jugador).getPuntos() + puntos);


                //SI HAY UN GANADOR
                if (appState.getMyList().get(jugador).getPuntos() >= cantMaxSorbos) {
                    nombreGanador.setText(appState.getMyList().get(jugador).getNombre());
                    popupWindow3.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    popupWindow3.setOutsideTouchable(true);
                    popupWindow3.setFocusable(true);
                    popupWindow3.showAtLocation(papa, Gravity.CENTER_HORIZONTAL, -10, 0);

                }
            }

            if (todos==false)
                jugador = (jugador + 1) % appState.getMyList().size();
            setPrev(appState.getMyList().get(jugador));


        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(JugadaNormal.this);
            builder.setTitle(R.string.titleAmigos).setMessage(R.string.messageAmigos).setPositiveButton(getString(R.string.positiveAmigos), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            agregarJugador(null);
                            dialog.dismiss();
                        }
                    }).setNegativeButton(getString(R.string.returnMenu), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    JugadaNormal.this.finish();
                    dialog.dismiss();
                }
            });
            builder.show();
        }


    }


    public void getInfo() {

        reto = randomInteger(0, retos.size()-1);
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
                    todos = object.getBoolean("Todos");
                    jugada(titulo, descripcion, puntos, todos);

                    //continua=true;
                }
            }
        });

        // return continua;
    }

    public void jugada(String titulo, String descripcion, int puntos,boolean todos) {


        tituloText.setInAnimation(this, R.anim.push_down_in);
        tituloText.setOutAnimation(this, R.anim.push_down_out);
        text.setInAnimation(this, R.anim.fadein);
        tituloText.setText(titulo);
        TextView  theme =(TextView)tituloText.getChildAt(tituloText.getDisplayedChild());
        if(puntos<=3){

            theme.setTextColor(getResources().getColor(R.color.azul));

        }else if(puntos>3 && puntos<=6){
            theme.setTextColor(getResources().getColor(R.color.verde));

        }else if(puntos>6){
            vibrator.vibrate(1000);
            theme.setTextColor(getResources().getColor(R.color.rojo));


        }
        text.setOutAnimation(this, R.anim.fadeout);
        nombreText.setInAnimation(this, R.anim.fadein);
        nombreText.setOutAnimation(this, R.anim.fadeout);




        if(todos==false && cachondo==false ) {
            skip.setVisibility(View.VISIBLE);

            if(!appState.getMyList().isEmpty()) {
                nombreText.setText(appState.getMyList().get(jugador).getNombre());
                iconoplayer.setImageResource(appState.getMyList().get(jugador).getImagen());
            }

            next.setBackground(getResources().getDrawable(R.drawable.mybutton));
            LinearLayout.LayoutParams paramsSkip = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2);
            paramsSkip.setMargins(0, 0, 8, 0);
            skip.setLayoutParams(paramsSkip);
            skip.setText(R.string.btnGamePasar);
            LinearLayout.LayoutParams paramsNext = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2);
            paramsNext.setMargins(8, 0, 0, 0);
            next.setLayoutParams(paramsNext);

        }else if(todos==false && cachondo==true){
            skip.setVisibility(View.VISIBLE);

            if(!appState.getMyList().isEmpty()) {
                nombreText.setText(appState.getMyList().get(jugador).getNombre());
                iconoplayer.setImageResource(appState.getMyList().get(jugador).getImagen());
            }

            next.setBackgroundColor(getResources().getColor(R.color.rosa));
            skip.setText(R.string.bebe);
            LinearLayout.LayoutParams paramsSkip = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2);
            paramsSkip.setMargins(0, 0, 8, 0);
            skip.setLayoutParams(paramsSkip);
            LinearLayout.LayoutParams paramsNext = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2);
            paramsNext.setMargins(8, 0, 0, 0);
            next.setLayoutParams(paramsNext);

        }
        else{

            skip.setVisibility(View.INVISIBLE);
            next.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            next.setBackground(getResources().getDrawable(R.drawable.mybutton3));
            skip.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT));
            nombreText.setText(getText(R.string.Todos));
            iconoplayer.setImageResource(R.drawable.todos);
        }



        puntosJugador.setText("+" + puntos);
        text.setText(descripcion);

        int chance = randomInteger(0, 100);

        if (chance > 85) {
            int adModorMobileC = randomInteger(0, 100);
            if (adModorMobileC < 80) {
                if (anuncio.isLoaded()) {
                    anuncio.show();
                }
            } else {
                MobileCore.showInterstitial(JugadaNormal.this, MobileCore.AD_UNIT_SHOW_TRIGGER.APP_START, null);
            }
        }
    }


    public void tablero(View view) {

        final ListAdapter adapter = new ListAdapter(this, R.layout.row, appState.getMyList());
        adapter.notifyDataSetChanged();
        TextView title = (TextView)popupView2.findViewById(R.id.title1234);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/manteka.ttf");
        title.setTypeface(custom_font);
        popupWindow2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setFocusable(true);
        popupWindow2.showAsDropDown(addPlayer, ((int) addPlayer.getX()), ((int) addPlayer.getY()));
        listajugadores.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

    public void sexos(View view){
        sexoView.setVisibility(View.VISIBLE);



    }

    public void hombre(View view){
        selec=true;
        isHombre=true;
        sexoView.setVisibility(View.INVISIBLE);
        sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.masculino));
        popupWindow4.dismiss();
        popupWindow4.update();

    }
    public void mujer(View view){
        selec=true;
        isHombre=false;
        sexoView.setVisibility(View.INVISIBLE);
        sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.femenino));
        popupWindow4.dismiss();
        popupWindow4.update();

    }




    public void agregarJugador(View view) {


        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(addPlayer, ((int) addPlayer.getX()), ((int) addPlayer.getY()));
        // Create an ArrayAdapter using the string array and a default spinner layout
        if(popupWindow.isOutsideTouchable()){
            sexoView.setVisibility(View.INVISIBLE);
            sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.mf));

        }



        nombreNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexoView.setVisibility(View.INVISIBLE);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nombreNuevo.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.toastGameElseAdd, Toast.LENGTH_SHORT).show();
                } else if(selec==false){
                    Toast.makeText(getApplicationContext(), R.string.selectsex, Toast.LENGTH_SHORT).show();}
                    else {

                    jugador2++;
                    if (jugador2 >= iconosJugadores.length)
                        jugador2 = 0;

                    appState.getMyList().add(new Jugador(nombreNuevo.getText().toString(), 0, iconosJugadores[jugador2],isHombre));
                    jugador = (jugador + 1) % appState.getMyList().size();
                    nombreNuevo.setText("");

                    sexoView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), R.string.toastGameAdd, Toast.LENGTH_SHORT).show();
                    sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.mf));
                    selec=false;
                    popupWindow.dismiss();
                }

            }
        });

        popupWindow.update();


    }

    public void palomo(View view) {
        if (!appState.getMyList().isEmpty()) {
            jugador = (jugador) % appState.getMyList().size();
            now = appState.getMyList().get(jugador);
            if (now.equals(prev)) {
                appState.getMyList().get(jugador).setPuntos(appState.getMyList().get(jugador).getPuntos() - puntos);
            }
            jugador = (jugador + 1) % appState.getMyList().size();
            setPrev(appState.getMyList().get(jugador));
            getInfo();
        } else {
            this.finish();
        }
    }

    public int randomInteger(int min, int max) {

        Random rand = new Random();

        // nextInt excludes the top value so we have to add 1 to include the top value


        return rand.nextInt((max - min) + 1) + min;
    }

    public void setPrev(Jugador prev) {

        this.prev = prev;
    }

   private void setAdUnitsEventListener() {
        MobileCore.setAdUnitEventListener(new AdUnitEventListener() {

            @Override
            public void onAdUnitEvent(MobileCore.AD_UNITS adUnit, EVENT_TYPE eventType) {

                switch (adUnit) {
                    case INTERSTITIAL:
                        if (EVENT_TYPE.AD_UNIT_READY == eventType) {
                            MobileCore.showInterstitial(JugadaNormal.this, MobileCore.AD_UNIT_SHOW_TRIGGER.APP_START, null);
                        }
                        break;
                    case STICKEEZ:
                        if (AdUnitEventListener.EVENT_TYPE.AD_UNIT_READY == eventType) {
                            MobileCore.showStickee(JugadaNormal.this);
                        }
                        break;
                }

            }
        });

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

    public void volver(View view) {
        JugadaNormal.this.finish();

    }


    public void resetlista(int cantRetos){
        for(int i=1;i<=cantRetos;i++){
            retos.add(i);
        }
    }

}

