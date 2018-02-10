package com.example.administrador.mis_libros.control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrador.mis_libros.R;

/**
 * Created by Administrador on 16/01/2018.
 */
public class NoSeleccionadoFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_no_seleccionado, container,false);
    }
}
