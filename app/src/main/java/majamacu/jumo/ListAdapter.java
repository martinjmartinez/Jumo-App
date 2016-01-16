package majamacu.jumo;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by marti on 11/9/2015.
 */
public class ListAdapter extends ArrayAdapter<Jugador> {


    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Jugador> items) {
        super(context, resource, items);
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {


         View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row, null);
        }

        Jugador p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.nombrelista);
            TextView tt2 = (TextView) v.findViewById(R.id.puntosLista);
            ImageView tt3 = (ImageView) v.findViewById(R.id.icon);
            ImageView tt4 = (ImageView) v.findViewById(R.id.delete);
            ImageView tt5 = (ImageView) v.findViewById(R.id.sexicon);

            if (tt1 != null) {
                tt1.setText(p.getNombre());
            }

            if (tt2 != null) {
                tt2.setText("" + p.getPuntos());
            }

            if (tt3 != null) {
                tt3.setImageResource(p.getImagen());
            }

            if(tt5 !=null){
                if(p.isHombre()==true)
                    tt5.setImageResource(R.drawable.m);
                else
                    tt5.setImageResource(R.drawable.f);

            }

            notifyDataSetChanged();

            tt4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View a) {
                    //do something
                    JugadaNormal e= new JugadaNormal();
                    e.setPrev(getItem(position));
                    remove(getItem(position));
                    notifyDataSetChanged();




                }
            });
        }


        return v;



    }

}