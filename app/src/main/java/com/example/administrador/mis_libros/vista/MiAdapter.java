package com.example.administrador.mis_libros.vista;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.DBHandler;
import com.example.administrador.mis_libros.modelo.Libros;

import java.util.ArrayList;

public class MiAdapter extends CursorAdapter {
    static class ViewHolder{
        //el holder es una clase que memoriza
        public TextView titulo;
        public TextView autor;
        public LinearLayout ll;

    }
    //Atributos
    private Activity actividad=null;
    private DBHandler dbh=null;
    private Cursor datos=null;

    public MiAdapter(Activity context, Cursor c){
        super(context,c,false);
        //para auto recarge
        this.actividad=context;
        this.dbh=new DBHandler(actividad);
        this.datos=c;
    }//constructor

    @NonNull

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

       //inflar el layaout para la fila nueva
        View fila = actividad.getLayoutInflater().inflate(R.layout.layout_fila,parent,false);
        //memoriamos los ids de esta fila en el holder
        ViewHolder vh= new ViewHolder();
        vh.autor=fila.findViewById(R.id.fila_tv2);
        vh.titulo=fila.findViewById(R.id.fila_tv1);
        vh.ll=fila.findViewById(R.id.fila_textos);
        //adjuntamos el holder a la fila
        fila.setTag(vh);
        populateFrom(vh,cursor,dbh);
        //devolver la fila nueva
        return fila;
    }

    private void populateFrom(ViewHolder vh, Cursor cursor, DBHandler dbh) {
        vh.titulo.setText(cursor.getString(1));
        vh.autor.setText(cursor.getString(2));
        vh.ll.setBackgroundColor(Color.RED);
        int gen = Integer.parseInt(cursor.getString(4));
        switch(gen){
            case 1:
                vh.ll.setBackgroundColor(Color.YELLOW);
                break;
            case 2:
                vh.ll.setBackgroundColor(Color.CYAN);
                break;
            default:
                vh.ll.setBackgroundColor(Color.GRAY);
                break;
        }

        //falta terminar persoanlizar el el layout
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //RECICLAR la  fila que viene por parámto
        //recuperar los id de los widged de la fila
        ViewHolder vh= (ViewHolder) view.getTag();
        populateFrom(vh,cursor,dbh);
    }
}//class
