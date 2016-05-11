package majamacu.jumo;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by marti on 5/10/2015.
 */
public class Init extends Application {

    private ArrayList<Jugador> myList = new ArrayList<>();
    private boolean personal=true,cachondo=false,grupal=true,espanol=false,happy=true,lito=false,jumo=false;
    Locale myLocale;


    public boolean isHappy() {
        return happy;
    }

    public void setHappy(boolean happy) {
        this.happy = happy;
    }

    public boolean isJumo() {
        return jumo;
    }

    public void setJumo(boolean jumo) {
        this.jumo = jumo;
    }

    public boolean isLito() {
        return lito;
    }

    public void setLito(boolean lito) {
        this.lito = lito;
    }

    public boolean isEspanol() {
        return espanol;
    }

    public void setEspanol(boolean espanol) {
        this.espanol = espanol;
    }

    public ArrayList<Jugador> getMyList() {
        return myList;
    }

    public void setMyList(ArrayList<Jugador> myList) {
        this.myList = myList;
    }

    public boolean isCachondo() {
        return cachondo;
    }

    public void setCachondo(boolean cachondo) {
        this.cachondo = cachondo;
    }

    public boolean isGrupal() {
        return grupal;
    }

    public void setGrupal(boolean grupal) {
        this.grupal = grupal;
    }

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("UzNevc1EDATPoTkqFR1H7KRr7PKD5ZKtxKsj2YOv")
                .clientKey("7UtxRUlifcOGONvh1f8S6jySem30XlTgdDGdGhb1")
                .server("http://jumo.herokuapp.com/parse/")
                .enableLocalDataStore().build()
        );

        if (Locale.getDefault().getLanguage().equals("es")) {
            setLocale("es");
            setEspanol(true);
        } else {
            setLocale("en");
            setEspanol(false);
        }
    }
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}

