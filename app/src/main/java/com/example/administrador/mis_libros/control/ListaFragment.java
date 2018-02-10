package com.example.administrador.mis_libros.control;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.DBHandler;
import com.example.administrador.mis_libros.modelo.Libros;
import com.example.administrador.mis_libros.vista.MiAdapter;

/**
 * Created by Administrador on 30/11/2017.
 */

public class ListaFragment extends Fragment {

    //atributos
    protected ListView lv;
    //protected ArrayList<Libros> datos;
    protected static Libros l;//no necesita ser pasado a la actividad porque la Activity es la que maneja el fragment
    private  OnInsertarListener listener_insertar = null;
    protected static DBHandler dbh;
    protected Cursor datos;
    protected MiAdapter aa;
    //protected ArrayAdapter<Libros> aa;
    protected OnLibroSeleccionadoListener listener_seleccion=null;

    //constructor vacio
    public ListaFragment(){
        super();
    }

    public long insertar(Libros l) {
        //inserta
        long id =dbh.insert(l);
        //refrescar la lista
        new GetDatos().execute();
        return id;
    }
    public Libros geLibro(long id){
        Cursor item = dbh.getItem(id);
        item.moveToFirst();
        Libros l = new Libros(item.getString(1),item.getString(2));
        l.setResumen(item.getString(3));

        int g= Integer.parseInt(item.getString(4));

        switch (g){
            case 1:l.setGenero(Libros.Genero.TEATRO);
            break;
            case 2:l.setGenero(Libros.Genero.POESIA);
                break;
            default:l.setGenero(Libros.Genero.NARRATIVA);
                break;
        }
        return l;
    }
    public interface OnInsertarListener {
        public void onInsertar();
    }
    public interface OnLibroSeleccionadoListener{
        public void onLibroSeleccionado(long id);
    }
    @Override
    public void onAttach (Context activity) {

        /* Obligamos a que la activity tenga implementados los metodos de
           callback para los eventos que se asigna a los widgets de la interfaz
         */
        super.onAttach(activity);
        try {
            listener_insertar = (OnInsertarListener) activity;
            listener_seleccion =(OnLibroSeleccionadoListener) activity;
        } catch (ClassCastException e) {
            System.out.print(e.getStackTrace());
        }
    }
    @Override
    public void onDetach () {
        super.onDetach();
        listener_insertar = null;
        listener_seleccion = null;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View result=inflater.inflate(R.layout.fragment_lista_layout, container, false);
        //abrir la bd
        this.dbh= new DBHandler(getActivity());
        dbh.abrir();
        //datos de prueba
        rellenarDatos();
       // ArrayAdapter<Libros> aa = new ArrayAdapter<Libros>(getActivity(), android.R.layout.simple_list_item_1, datos);
        aa = new MiAdapter(getActivity(), datos);
        //aa.sort(new LibroPorAutor());
        lv=(ListView) result.findViewById(R.id.lista);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
              //  listener_seleccion.onLibroSeleccionado(position);
                listener_seleccion.onLibroSeleccionado(id);
            }
        });

        Button btn = (Button) result.findViewById(R.id.b_nuevo);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
               /*listener_insertar toma la referencia de form activity en el metodo onAttach*/
                listener_insertar.onInsertar();
            }
        });

        getActivity().registerForContextMenu(lv);
        return result;
    }//onCreateView

    private void rellenarDatos() {

       insertar(new Libros("Don Quijote", "Cervantes","En un lugar de la macha...", Libros.Genero.NARRATIVA));
       insertar(new Libros("Lazarillo de Tormes", "Anonimo","Era un chico huerfano", Libros.Genero.NARRATIVA));

    }
    /*
    Vamos a consultar la BD  de forma asíncrona;
    es decri, en segundi okaano.Para conseguirlo
    creamos una clase AsyncTask interna y específica
     */
    protected class GetDatos extends AsyncTask<Integer,Integer,Cursor>{
        //tipo de entrada ,tipo de proceso, tipo salida

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Integer... integers) {
            return dbh.list();
        }

        @Override
        protected void onPostExecute(Cursor resultado) {
            super.onPostExecute(resultado);
            datos=resultado;
            ///pasar el nuevolistad o al adapter
            aa.swapCursor(resultado);
            //comunicar al adapter que debe refrescarse
            aa.notifyDataSetChanged();


        }
    }
}
