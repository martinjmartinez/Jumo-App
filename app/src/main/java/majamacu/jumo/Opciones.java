package majamacu.jumo;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ironsource.mobilcore.CallbackResponse;
import com.ironsource.mobilcore.MobileCore;

import java.util.Locale;


/**
 * Created by marti on 13/10/2015.
 */
public class Opciones extends AppCompatActivity {

    Switch retosCachondos;
    Switch retosPersonales;
    Switch retosGrupales;
    Init appState;
    Button espanol,ingles;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

        appState= ((Init)getApplicationContext());

        retosCachondos = (Switch)findViewById(R.id.switchCachondo);
        retosPersonales = (Switch)findViewById(R.id.switchRetosPersonales);
        retosGrupales = (Switch)findViewById(R.id.switch1);
        espanol=(Button)findViewById(R.id.btnespanol);
        ingles=(Button)findViewById(R.id.btningles);





        retosCachondos.setChecked(appState.isCachondo());
        retosGrupales.setChecked(appState.isGrupal());
        retosPersonales.setChecked(appState.isPersonal());

        if(appState.isEspanol()){
            espanol.setBackgroundColor(getResources().getColor(R.color.azul));
            ingles.setBackgroundColor(getResources().getColor(R.color.rojo));
        }else{
            ingles.setBackgroundColor(getResources().getColor(R.color.azul));
            espanol.setBackgroundColor(getResources().getColor(R.color.rojo));

        }



        retosGrupales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    appState.setGrupal(true);
                } else {
                    appState.setGrupal(false);

                }
            }
        });

        retosPersonales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    appState.setPersonal(true);
                }
                else{
                    appState.setPersonal(false);

                }
            }
        });

        retosCachondos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    appState.setCachondo(true);
                }
                else{
                    appState.setCachondo(false);

                }
            }
        });


    }

    public void volverMenu(View view){
        this.finish();
    }

    public void setEspanol(View view){
        setLocale("es");
        appState.setEspanol(true);

    }

    public void setLocale(String lang) {
        appState.myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = appState.myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Menu.class);
        startActivity(refresh);
        finish();
    }

    public void setIngles(View view){
        setLocale("en");
        appState.setEspanol(false);

    }



}
