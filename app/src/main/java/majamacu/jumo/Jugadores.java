package majamacu.jumo;


import android.content.Context;

import android.content.Intent;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;


import android.widget.RelativeLayout;

import android.widget.Toast;


public class Jugadores extends AppCompatActivity {


    EditText nombreJugador;
    int maxSorbos;
    int[] iconosJugadores = {R.drawable.playericon, R.drawable.playericon2, R.drawable.playericon3, R.drawable.playericon4, R.drawable.playericon5,
            R.drawable.playericon6, R.drawable.playericon7, R.drawable.playericon8, R.drawable.playericon9, R.drawable.playericon10};
    Button btnhappy;
    Button btnLito;
    Button btnJumo;

    private static final Integer[] emoticons = {R.drawable.mf, R.drawable.masculino, R.drawable.femenino};

    int jugador = 0;
    int cantRetos = 0;
    Init appState;
    boolean isHombre,selec=false;




    ListView listaJugadores;
    String itemSeleccionadoSorbos,itemSeleccionadoSexo;
    ListAdapter adapter2;
    LayoutInflater layoutInflater;
    View popupView4;
    PopupWindow popupWindow4;
    ImageView sexbtn;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jugadores);

        appState= ((Init)getApplicationContext());


        nombreJugador = (EditText) findViewById(R.id.nombreedit);
        listaJugadores = (ListView) findViewById(R.id.listView);

        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView4 = layoutInflater.inflate(R.layout.sexos, null);
        popupWindow4 = new PopupWindow(popupView4, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        sexbtn = (ImageView)findViewById(R.id.sexo);

        btnhappy = (Button)findViewById(R.id.btnHapy);
        btnLito = (Button)findViewById(R.id.btnLito);
        btnJumo = (Button)findViewById(R.id.btnJumo);








        adapter2 = new ListAdapter(this, R.layout.row, appState.getMyList());
        adapter2.notifyDataSetChanged();
        listaJugadores.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        if(appState.isHappy()&&!appState.isJumo()&&!appState.isLito()){
            maxSorbos=20;
            btnhappy.setBackgroundColor(getResources().getColor(R.color.verde));
            btnLito.setBackgroundColor(getResources().getColor(R.color.amarillo));
            btnJumo.setBackgroundColor(getResources().getColor(R.color.amarillo));

        }else if(!appState.isHappy()&&!appState.isJumo()&&appState.isLito()){
            maxSorbos=40;
            btnhappy.setBackgroundColor(getResources().getColor(R.color.amarillo));
            btnLito.setBackgroundColor(getResources().getColor(R.color.azul));
            btnJumo.setBackgroundColor(getResources().getColor(R.color.amarillo));

        }else if(!appState.isHappy()&&appState.isJumo()&&!appState.isLito()){
            maxSorbos=60;
            btnhappy.setBackgroundColor(getResources().getColor(R.color.amarillo));
            btnLito.setBackgroundColor(getResources().getColor(R.color.amarillo));
            btnJumo.setBackgroundColor(getResources().getColor(R.color.rojo));

        }




    }

    public void sexos(View view){
        popupWindow4.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        popupWindow4.setOutsideTouchable(true);
        popupWindow4.setFocusable(true);
        popupWindow4.showAsDropDown(sexbtn, ((int) sexbtn.getX()), ((int) sexbtn.getY()));

        popupWindow4.update();

    }
    public void hombre(View view){
        selec=true;
        isHombre=true;
        sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.masculino));
        popupWindow4.dismiss();
        popupWindow4.update();

    }
    public void mujer(View view){
        selec=true;
        isHombre=false;
        sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.femenino));
        popupWindow4.dismiss();
        popupWindow4.update();

    }




    public void agregar(View view) {

        if (nombreJugador.getText().toString().isEmpty() ) {
            Toast.makeText(getApplicationContext(), R.string.toastMenuElseAdd, Toast.LENGTH_SHORT).show();
        }
        else if(selec==false){
            Toast.makeText(getApplicationContext(), R.string.selectsex, Toast.LENGTH_SHORT).show();

        } else{
            appState.getMyList().add(new Jugador(nombreJugador.getText().toString(), 0, iconosJugadores[jugador],isHombre));
            Toast.makeText(getApplicationContext(), R.string.toastMenuAdd, Toast.LENGTH_SHORT).show();
            nombreJugador.setText("");
            sexbtn.setImageDrawable(getResources().getDrawable(R.drawable.mf));
            selec=false;
            jugador++;
            adapter2.notifyDataSetChanged();
            if (jugador >= iconosJugadores.length)
                jugador = 0;


            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }

    public void jugar(View view) {
        if (appState.getMyList().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.toastMenuElseEditTextJugador, Toast.LENGTH_SHORT).show();

        } else if (appState.getMyList().size() < 2) {
            Toast.makeText(getApplicationContext(), R.string.solono, Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(Jugadores.this, JugadaNormal.class);
            intent.putExtra("iconosJugadores", iconosJugadores);
            intent.putExtra("maxSorbos", maxSorbos);
            //this.finish();
            Jugadores.this.startActivityForResult(intent, 0);

        }


    }


    public void onBackPressed() {

        this.finish();

    }

    @Override
    protected void onPostResume() {
        adapter2.notifyDataSetChanged();
        super.onPostResume();
    }

    public void Happy(View view){
        maxSorbos=20;
        appState.setHappy(true);
        appState.setLito(false);
        appState.setJumo(false);
        btnhappy.setBackgroundColor(getResources().getColor(R.color.verde));
        btnLito.setBackgroundColor(getResources().getColor(R.color.amarillo));
        btnJumo.setBackgroundColor(getResources().getColor(R.color.amarillo));

    }

    public void Lito(View view){

        maxSorbos=40;
        appState.setLito(true);
        appState.setHappy(false);
        appState.setJumo(false);
        btnhappy.setBackgroundColor(getResources().getColor(R.color.amarillo));
        btnLito.setBackgroundColor(getResources().getColor(R.color.azul));
        btnJumo.setBackgroundColor(getResources().getColor(R.color.amarillo));
    }

    public void Jumo(View view){
        maxSorbos=60;
        appState.setHappy(false);
        appState.setJumo(true);
        appState.setLito(false);
        btnhappy.setBackgroundColor(getResources().getColor(R.color.amarillo));
        btnLito.setBackgroundColor(getResources().getColor(R.color.amarillo));
        btnJumo.setBackgroundColor(getResources().getColor(R.color.rojo));

    }



}
