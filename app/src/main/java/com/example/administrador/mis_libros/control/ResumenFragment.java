package com.example.administrador.mis_libros.control;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.Libros;

public class ResumenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITUTLO = "titulo";
    private static final String AUTOR = "autor";
    private static final String RESUMEN = "resumen";

    public ResumenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters
     * @return A new instance of fragment ResumenFragment.
     */
    public static ResumenFragment newInstance(String t, String a, String r) {
        ResumenFragment fragment = new ResumenFragment();
        Bundle args = new Bundle();
        args.putString(TITUTLO, t);
        args.putString(AUTOR, a);
        args.putString(RESUMEN, r);
        fragment.setArguments(args);
        return fragment;
    }

    public static ResumenFragment newInstance(Libros l){
        ResumenFragment fragment = new ResumenFragment();
        Bundle args = new Bundle();
        args.putString(TITUTLO, l.getTitulo());
        args.putString(AUTOR, l.getAutor());
        args.putString(RESUMEN, l.getResumen());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_resumen,container,false);
        if(getArguments()!=null){
            Bundle args = getArguments();
            setLabel(result, R.id.tv_titulo_resumen,args.getString(TITUTLO));
            setLabel(result, R.id.tv_autor_resumen,args.getString(AUTOR));
            setLabel(result, R.id.tv_resumen,args.getString(RESUMEN));
        }
        return result;
    }

    private void setLabel(View v, int id, String str){
        /* Este metodo es necesario por si tenemos libros sin resumen,
           sin autor, etc., el fragment puede explotar */

        TextView tv=(TextView) v.findViewById(id);
        if(str!=null) tv.setText(str);
        else tv.setText("");
    }

}
