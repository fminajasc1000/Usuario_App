package usuariodetalle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuarioapp.R;
import usuario.UsuarioActivity;
import usuario.UsuarioFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import data.Usuario;
import data.UsuarioDb;
import addeditusuario.AddEditUsuarioActivity;

public class UsuarioDetalleFragment extends Fragment {
    /**
     * Atributos del objeto usuario
     * */
    private static final String ARG_USUARIO_ID = "UsuarioId";

    private String mUsuarioId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView nombre;
    private TextView proyecto;
    private TextView descripcion;
    private TextView desarrollador1;
    private TextView desarrollador2;

    private UsuarioDb mUsuarioDb;


    public UsuarioDetalleFragment() {
        // Required empty public constructor
    }

    public static UsuarioDetalleFragment newInstance(String lawyerId) {
        UsuarioDetalleFragment fragment = new UsuarioDetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USUARIO_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUsuarioId = getArguments().getString(ARG_USUARIO_ID);
        }

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuario_detalle, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        
        nombre = (TextView) root.findViewById(R.id.tv_nombre);
        proyecto = (TextView) root.findViewById(R.id.tv_project_manager);
        descripcion = (TextView) root.findViewById(R.id.tv_descripcion);
        desarrollador1 = (TextView) root.findViewById(R.id.tv_desarrollador1);
        desarrollador2 = (TextView) root.findViewById(R.id.tv_desarrollador2);

        mUsuarioDb = new UsuarioDb(getActivity());

        loadUsuario();

        return root;
    }

    private void loadUsuario() {
        new GetUsuarioByIdTask().execute();
    }

    private void showUsuario(Usuario usuario) {
        mCollapsingView.setTitle(usuario.getNombre());

        nombre.setText(usuario.getNombre());
        proyecto.setText(usuario.getProyectManager());
        descripcion.setText(usuario.getDescripcion());
        desarrollador1.setText(usuario.getDesarrollador1());
        desarrollador2.setText(usuario.getDesarrollador2());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetUsuarioByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mUsuarioDb.getUsuarioById(mUsuarioId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showUsuario(new Usuario(cursor));
            } else {
                showLoadError();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteUsuarioTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DeleteUsuarioTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mUsuarioDb.deleteUsuario(mUsuarioId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showUsuarioScreen(integer > 0);
        }

    }

    private void showUsuarioScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar usuario", Toast.LENGTH_SHORT).show();
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditUsuarioActivity.class);
        intent.putExtra(UsuarioActivity.EXTRA_USUARIO_ID, mUsuarioId);
        startActivityForResult(intent, UsuarioFragment.REQUEST_UPDATE_DELETE_USUARIO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UsuarioFragment.REQUEST_UPDATE_DELETE_USUARIO) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }
}