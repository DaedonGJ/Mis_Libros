package com.example.administrador.mis_libros.vista;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.Libros;

public class LibroHolder {

    /* Clase que contiene una vista de tipo Libro*/

    //Atributos: elementos de la fila
    private TextView tv_titulo=null;
    private TextView tv_autor=null;
    private LinearLayout ll_fila=null;

    public LibroHolder(View fila){

        //inicializamos los elementos de la fila
        tv_titulo=(TextView)fila.findViewById(R.id.fila_tv1);
        tv_autor=(TextView)fila.findViewById(R.id.fila_tv2);
        ll_fila=(LinearLayout)fila.findViewById(R.id.fila_textos);
    }
    public void populateFrom(Libros l){
        /* Rellenamos la fila siguiendo la logica de la aplicacion */

        tv_titulo.setText(l.getTitulo());
        tv_autor.setText(l.getAutor());

        if(l.getGenero().equals(Libros.Genero.NARRATIVA))
            ll_fila.setBackgroundColor(Color.WHITE);
        else if(l.getGenero().equals(Libros.Genero.POESIA))
            ll_fila.setBackgroundColor(Color.CYAN);
        else
            ll_fila.setBackgroundColor(Color.YELLOW);
    }//populateFrom

}
