package com.example.administrador.mis_libros.control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrador.mis_libros.R;

import static com.example.administrador.mis_libros.control.MainActivity.AUTOR;
import static com.example.administrador.mis_libros.control.MainActivity.RESUMEN;
import static com.example.administrador.mis_libros.control.MainActivity.TITULO;

/**
 * Created by Administrador on 25/01/2018.
 */

public class ResumenActivity extends AppCompatActivity{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_resumen_activity);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            ResumenFragment rf = ResumenFragment.newInstance(extras.getString(TITULO),extras.getString(AUTOR),extras.getString(RESUMEN));
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_resumen, rf).commit();
        }
    }
}
