package com.example.administrador.mis_libros.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import com.example.administrador.mis_libros.R;
import com.example.administrador.mis_libros.modelo.LibroPorAutor;
import com.example.administrador.mis_libros.modelo.Libros;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements ListaFragment.OnInsertarListener,ListaFragment.OnLibroSeleccionadoListener,
                        FromFragment.OnLibroInsertadoListener{

    // Constantes y etiquetas
    public static final int INSERTAR_CODE = 0;
    public static final int EDIT_CODE = 1;

    public static final String TITULO="titulo";
    public static final String AUTOR="autor";
    public static final String RESUMEN="resumen";
    public static final String GENERO="genero";

    // Atributos
    ListaFragment lf;
    NoSeleccionadoFragment nsf;
    ResumenFragment rf;
    FromFragment ff=null;
    Libros l;

    int posicion;//psocion el el listView
    long rowId;//posicon el la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posicion=-1; //ningun libro seleccionado
        rowId=-1L;
        lf = new ListaFragment();
        nsf=new NoSeleccionadoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contenedor_primera,lf);



        if(grande()){
            if(savedInstanceState!=null){
                //Reconstruccion desde 0... noSeleccionadoFragment
                transaction.add(R.id.contenedor_segunda,nsf);
            } else {
                //Toast.makeText(this, "Recuperar");
                //recuperamos el libro e instanciar el fragment
                posicion=savedInstanceState.getInt("posicion");
                l=new Libros(savedInstanceState.getString(TITULO),savedInstanceState.getString((AUTOR)));
                l.setResumen(savedInstanceState.getString(RESUMEN));
                //l=lf.aa.getItem(posicion);
                rf=ResumenFragment.newInstance(l);
                transaction.add(R.id.contenedor_segunda,rf);

            }
            //Recuperar el fragment que hubiera
          //  l=lf.aa.getItem(posicion);
            rf= ResumenFragment.newInstance(l);
            transaction.add(R.id.contenedor_segunda,rf);
        }



        transaction.commit();
    }//onCreate

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action Bar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }//onCreateOptionMenu

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_nuevo: insertarLibro();
                break;
            case R.id.menu_salir: finish();
                break;
            case R.id.b_autor:
                porAutor();break;
            case R.id.b_titulo:
                porTitulo();break;
        }
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    private void porAutor() {
        //lf.aa.sort(new LibroPorAutor());
    }

    private void porTitulo() {
        //ordenar el modelo de datos por título
        //orden natural de ls libros
       // lf.aa.sort(new LibroPorAutor());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_fila, menu);
    }//onCreateContextMenu

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        posicion=info.position;
        rowId=info.id;

        switch (item.getItemId()){
            case R.id.menu_editar: editar();
                break;
            case R.id.menu_borrar: borrar();
                break;
        }
        return super.onContextItemSelected(item);
    }//onContextItemSelected

    private void editar(){
        /* Obtener los datos que ya tiene el libro para cargarlos en el Form
           y modificarlos para ellos usamos la info sacada del metodo
           onContextItemSelected... posicion, rowId
         */
       //l=lf.datos.get(posicion);
        Toast.makeText(this, "Editar  "+l.getTitulo(), Toast.LENGTH_LONG).show();

        // Ahora tenemos que invocar el form pero relleno con estos datos
        if(grande()){
            ff=FromFragment.newInstance(l);
            cargarSegundoFragment(ff);
        }else {//abrir Intent, pasar parametros, abrir Activity
            Intent i=new Intent(this, FromActivity.class);
            i.putExtra(TITULO, l.getTitulo());
            i.putExtra(AUTOR, l.getAutor());
            i.putExtra(RESUMEN, l.getResumen());
            i.putExtra(GENERO, l.getGenero().ordinal());

            startActivityForResult(i, EDIT_CODE);
        }

        Toast.makeText(this, "Editar fila "+posicion, Toast.LENGTH_LONG).show();
    }
    private void borrar(){
        Toast.makeText(this, "borrar fila "+posicion, Toast.LENGTH_LONG).show();
      ///  lf.aa.remove(lf.aa.getItem(posicion));
    }

    private void insertarLibro() {
        // Si estamos en tablet
        if(grande())
            Toast.makeText(this, "insertando", Toast.LENGTH_LONG).show();
        else { //movil
            Intent i = new Intent(this, FromActivity.class);
            startActivityForResult(i, INSERTAR_CODE);
        }
    }//insertarLibro

    private boolean grande(){
        return (findViewById(R.id.contenedor_segunda)!= null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle b = data.getExtras();
        Libros l = new Libros (b.getString(TITULO), b.getString(AUTOR));

        l.setResumen(b.getString(RESUMEN));

        switch (b.getInt(GENERO, 0)) {
            case 1:
                l.setGenero(Libros.Genero.TEATRO);
                break;
            case 2:
                l.setGenero(Libros.Genero.POESIA);
                break;
            default:
                break;
        }
        if(requestCode==INSERTAR_CODE){
            lf.insertar(l);
        }else {
           // lf.aa.remove(lf.aa.getItem(posicion));
           // lf.aa.insert(l,posicion);
        }
    }

    @Override
    public void onInsertar() {
        posicion=-1; //recordar que estamos insertando
        if(grande()) {
            //Toast.makeText(this, "insertando", Toast.LENGTH_LONG).show();
            ff = FromFragment.newInstance(null);
            cargarSegundoFragment(ff);
            l=new Libros();
        } else { //movil
            Intent i = new Intent(this, FromActivity.class);
            startActivityForResult(i, INSERTAR_CODE);
        }
    }

    @Override
    public void onLibroSeleccionado(long id) {
        //en principio, solo para contexto tablet
       // Libros l = lf.datos.get(id);
        l=lf.geLibro(id);
        if(grande()){
            //instanciar un fragment resumen con el libro
            rf=ResumenFragment.newInstance(l);

            //cargar el fragment
            cargarSegundoFragment(rf);
        }else{ //contexto movil
            Intent i = new Intent(this,ResumenFragment.class);
            //Necesitamos una activity que cargue el fragment con el resumen
            i.putExtra(TITULO, l.getTitulo());
            i.putExtra(AUTOR, l.getAutor());
            i.putExtra(RESUMEN, l.getGenero());

            startActivity(i);
        }
    } //onLibroSeleccionado

    private void cargarSegundoFragment(Fragment f){
        FragmentTransaction transactionn = getSupportFragmentManager().beginTransaction();
        transactionn.replace(R.id.contenedor_segunda,f);
        transactionn.commit();
    }

    @Override
    public void onLibroInsertado() {
        /* Como el layout del fragment esta enganchado al segundo FragmentLayout
           de Main, puede acceder directamente a sus elementos
         */
        EditText et_titulo=(EditText) findViewById(R.id.et_titulo);
        EditText et_autor=(EditText) findViewById(R.id.et_autor);
        EditText et_resumen=(EditText) findViewById(R.id.et_resumen);
        l.setTitulo(et_titulo.getText().toString());
        l.setAutor(et_autor.getText().toString());
        l.setResumen(et_resumen.getText().toString());

        if(posicion==0){

           lf.insertar(l);
        }
        else{
           // lf.aa.remove(lf.aa.getItem(posicion));
           // lf.aa.insert(l, posicion);
        }

        //insertar el libro en el adapter
      //  lf.aa.add(l);

        cargarSegundoFragment(nsf);
    }

    @Override
    public void setGenero(int checkedId) {
        //recoge el evento del radiogroup
        switch (checkedId){
            case R.id.radioButton2: l.setGenero(Libros.Genero.TEATRO);
                break;
            case R.id.radioButton3: l.setGenero(Libros.Genero.POESIA);
                break;
            default: l.setGenero(Libros.Genero.NARRATIVA);
                break;
        }
    }
    public void onSaveInstanceState(Bundle b){
        if(rowId!=-1){
           // l=lf.aa.getItem(posicion);
            b.putInt("posicion", posicion);
            b.putString(TITULO, l.getTitulo());
            b.putString(AUTOR, l.getAutor());
            b.putString(RESUMEN, l.getResumen());
            b.putInt(GENERO, l.getGenero().ordinal());

        }
    }
}
