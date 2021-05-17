package usuario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usuarioapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import data.UsuarioDb;
import data.UsuarioProyecto;
import addeditusuario.AddEditUsuarioActivity;
import usuariodetalle.UsuarioDetalleActivity;
/**
 * Vista para listar a los proyectos disponibles
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 */
public class UsuarioFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_USUARIO = 2;

    private UsuarioDb usuarioDb;
    private ListView mUsuarioList;
    private UsuarioCursorAdapter mUsuarioAdapter;
    private FloatingActionButton mAddButton;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UsuarioFragment newInstance(){
        return new UsuarioFragment();
    }
    /**
     * obtiene la regerencia de la lista y setea un nvo adaptador de datos
     * @param inflater
     * @param  container contenedor
     * @param savedInstanceState
     * @return root regresa la vista
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_usuario, container, false);

        // Referencias UI
        mUsuarioList = (ListView) root.findViewById(R.id.usuario_list);
        mUsuarioAdapter = new UsuarioCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mUsuarioList.setAdapter(mUsuarioAdapter);

        // Instancia de helper
        usuarioDb = new UsuarioDb(getActivity());

        // Eventos
        mUsuarioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mUsuarioAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(UsuarioProyecto.UsuarioEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });
        // Carga de datos
        loadUsuarios();

        return root;
    }
    /**
     * Metodo que se utiliza para refrescar a las screen de insercion que hayan provocado
     * algun cambio
     * @param requestCode Codigo que indica ADD o DELETE,
     * @param resultCode Codigo que infica el tipo de respuesta
     * @param data codigo que contiene la informacion
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditUsuarioActivity.REQUEST_ADD_USUARIO:
                    showSuccessfullSavedMessage();
                    loadUsuarios();
                    break;
                case REQUEST_UPDATE_DELETE_USUARIO:
                    loadUsuarios();
                    break;
            }
        }
    }

    private void loadUsuarios() {
        // Cargar datos...
        new UsuarioLoadTask().execute();
    }

    private class UsuarioLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return usuarioDb.getAllUsuario();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mUsuarioAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }


    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditUsuarioActivity.class);
        startActivityForResult(intent, AddEditUsuarioActivity.REQUEST_ADD_USUARIO);
    }

    private void showDetailScreen(String usuarioId) {
        Intent intent = new Intent(getActivity(), UsuarioDetalleActivity.class);
        intent.putExtra(UsuarioActivity.EXTRA_USUARIO_ID, usuarioId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_USUARIO);
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Abogado guardado correctamente", Toast.LENGTH_SHORT).show();
    }
}
