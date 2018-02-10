package com.example.administrador.mis_libros.control;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.Libros;


public class FromFragment extends Fragment{

    public interface OnLibroInsertadoListener{
        public void onLibroInsertado();
        public void setGenero(int checkedId);
    }

    // atributos
    protected static Libros l;//no necesita ser pasado a la actividad porque la Activity es la que maneja el fragment
    private  OnLibroInsertadoListener listener_insertado = null;

    // contructor vacio
    public FromFragment(){}

    public static FromFragment newInstance (Libros libro) {
        FromFragment fragment = new FromFragment();
        l = libro;
        return fragment;
    }

    public void onAttach(Context activity){
        super.onAttach(activity);
        try{
            listener_insertado=(OnLibroInsertadoListener) activity;
        }catch(ClassCastException e){
            System.out.print(e.getStackTrace());
        }
    }//onAttach

    public void onDetach(){
        super.onDetach();
        listener_insertado=null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(
                R.layout.fragment_from_layaout,
                container,
                false);

        if(l!=null){//Estamos Editando

            ((EditText)result.findViewById(R.id.et_titulo)).setText(l.getTitulo());
            ((EditText)result.findViewById(R.id.et_autor)).setText(l.getAutor());
            ((EditText)result.findViewById(R.id.et_resumen)).setText(l.getResumen());
            RadioGroup rg=(RadioGroup)result.findViewById(R.id.rg);

            //Toast.makeText(this, "Editar  "+l.getTitulo(), Toast.LENGTH_LONG).show();

            switch (l.getGenero().ordinal()){
                case 0:
                    rg.check(R.id.radioButton1); break;
                case 1:
                    rg.check(R.id.radioButton2); break;
                case 2:
                    rg.check(R.id.radioButton3); break;
            }
        }

        RadioGroup rg = (RadioGroup) result.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
               listener_insertado.setGenero(checkedId);
            }
        }
        ); //setOnCheckedChangeListener

        Button b = result.findViewById(R.id.button);
        b.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener_insertado.onLibroInsertado();
                }
            }
        );//setOnClickListener

        EditText etitulo=(EditText)result.findViewById(R.id.et_titulo);
        EditText eautor=(EditText)result.findViewById(R.id.et_autor);
        EditText eresumen=(EditText)result.findViewById(R.id.et_resumen);
        RadioGroup egenero=(RadioGroup)result.findViewById(R.id.rg);
        l=new Libros(etitulo.getText().toString(),eautor.getText().toString());


        return result;
    }
}