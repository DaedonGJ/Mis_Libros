package com.example.administrador.mis_libros.vista;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.Libros;
import java.util.ArrayList;

public class MiAdapter_OLD extends ArrayAdapter<Libros> {

    //Atributos
    private Activity actividad=null;
    private ArrayList<Libros> datos=null;

    public MiAdapter_OLD(Activity context, ArrayList<Libros> modelo){
        super(context, R.layout.layout_fila, modelo);
        this.actividad=context;
        this.datos=modelo;
    }//constructor

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View fila=convertView;
        LibroHolder holder=null;
        if(fila==null){
            //no hay filas suficientes y tengo que instanciar
            fila=actividad.getLayoutInflater().inflate(R.layout.layout_fila,null);
            holder=new LibroHolder(fila);

            //la vista nueva guarda su referencia en el tag del holder
            fila.setTag(holder);
        }else{
            //la fila es reciclada: ya existe y solo hay que metele los datos nuevos
            //recuperamos el holder con evitando los findViewById innecesarios
            holder= (LibroHolder) fila.getTag();
        }
        //cojo el libro del modelo de datos
        Libros l =datos.get(position);
        holder.populateFrom(l);
        return fila;
    }
}//class
