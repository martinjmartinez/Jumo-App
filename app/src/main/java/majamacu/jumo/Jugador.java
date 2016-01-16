package majamacu.jumo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marti on 10/9/2015.
 */
public class Jugador implements Parcelable {




    String nombre;
    int puntos;
    int imagen;
    boolean isHombre;

    public boolean isHombre() {
        return isHombre;
    }

    public void setIsHombre(boolean isHombre) {
        this.isHombre = isHombre;
    }

    public Jugador(String nombre, int puntos,int imagen,boolean isHombre) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.imagen=imagen;
        this.isHombre=isHombre;
    }

    public Jugador(Parcel source) {
        nombre = source.readString();
        puntos = source.readInt();
        imagen = source.readInt();

    }

    public int describeContents() {
        return this.hashCode();
    }



    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nombre);
        dest.writeInt(puntos);
        dest.writeInt(imagen);

    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
