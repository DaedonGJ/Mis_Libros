package com.example.administrador.mis_libros.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.Libros;

import static com.example.administrador.mis_libros.control.MainActivity.AUTOR;
import static com.example.administrador.mis_libros.control.MainActivity.GENERO;
import static com.example.administrador.mis_libros.control.MainActivity.RESUMEN;
import static com.example.administrador.mis_libros.control.MainActivity.TITULO;

public class FromActivity extends AppCompatActivity implements FromFragment.OnLibroInsertadoListener{

    // Atributos
    FromFragment ff;
    Intent i;
    Libros l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_formactivity);

        i=getIntent();
        Bundle extras = i.getExtras();
        l=new Libros();

        if(extras!=null){//edicion
            //rellenar el form con
            l=new Libros(extras.getString(TITULO),extras.getString(AUTOR));
            l.setResumen(extras.getString(RESUMEN));
            int genero=extras.getInt(GENERO);
            switch (genero){
                case 1: l.setGenero(Libros.Genero.TEATRO); break;
                case 2: l.setGenero(Libros.Genero.POESIA); break;
                default: break;
            }
            //instanciar el fragment pasandole el libro
            ff=FromFragment.newInstance(l);
        } else { //si insercion instanciar el fragment sin libro
            ff = FromFragment.newInstance(null);
        }

        // cargar el fragment con el from en el contenedor
        getSupportFragmentManager().beginTransaction().add(
                R.id.contenedor_segunda, ff
        ).commit();
    }

    @Override
    public void onLibroInsertado() {
        /*en el intent no podemos pasar el libro completo*/
        rellenarLibro();

        i.putExtra(TITULO, l.getTitulo());
        i.putExtra(MainActivity.AUTOR, l.getAutor());
        i.putExtra(MainActivity.RESUMEN, l.getResumen());
        i.putExtra(MainActivity.GENERO, l.getGenero().ordinal());

        setResult(RESULT_OK, i);
        finish();
    }

    private  void  rellenarLibro () {
        /*recoge los datos introducidos en el form y los carga en el libro*/


        l = new Libros(((EditText)findViewById(R.id.et_titulo)).getText().toString(),
                ((EditText)findViewById(R.id.et_autor)).getText().toString());
        l.setResumen(((EditText)findViewById(R.id.et_resumen)).getText().toString());


        switch (((RadioGroup)findViewById(R.id.rg)).getCheckedRadioButtonId()) {
            /* Para poner el genero del libro usamos el metodo callback
               de la interfaz que tiene el dato recogido del evento
             */
            case 1:
                setGenero(1);
                break;
            case 2:
                setGenero(2);
                break;
            default:
                setGenero(0);
                break;
        }
    }

    @Override
    public void setGenero(int checkedId) {
        switch(checkedId){
            case R.id.radioButton3: l.setGenero(Libros.Genero.TEATRO);
                break;
            case R.id.radioButton2: l.setGenero(Libros.Genero.POESIA);
                break;
            default: l.setGenero(Libros.Genero.NARRATIVA);

        }
    }
}
