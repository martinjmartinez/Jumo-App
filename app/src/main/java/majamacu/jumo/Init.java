package majamacu.jumo;

import android.app.Application;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by marti on 5/10/2015.
 */
public class Init extends Application {

    private ArrayList<Jugador> myList = new ArrayList<>();
    private boolean personal=true,cachondo=true,grupal=true,espanol=false,happy=true,lito=false,jumo=false;
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

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "UzNevc1EDATPoTkqFR1H7KRr7PKD5ZKtxKsj2YOv", "HSXAgBv7ZknpFYaVamQ8KJYZF4A33hWbixP9RZiO");
    }
}

