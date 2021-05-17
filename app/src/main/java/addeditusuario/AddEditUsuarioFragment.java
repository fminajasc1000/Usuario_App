package addeditusuario;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usuarioapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import model.Usuario;
import data.UsuarioDb;

public class AddEditUsuarioFragment extends Fragment {

    private static final String ARG_USUARIO_ID = "arg_usuario_id";

    private String mUsuarioId;

    private UsuarioDb mUsuarioDbHelper;

    private FloatingActionButton mSaveButton;

    private TextInputEditText mNombreField;
    private TextInputEditText mProyectManagerField;
    private TextInputEditText mDescripcionField;
    private TextInputEditText mDesarrollador1Field;
    private TextInputEditText mDesarrollador2Field;

    private TextInputLayout mNombreLabel;
    private TextInputLayout mProyectManagerLabel;
    private TextInputLayout mDescripcionLabel;
    private TextInputLayout mDesarrollador1Label;
    private TextInputLayout mDesarrollador2Label;

    public AddEditUsuarioFragment() {
        // Required empty public constructor
    }

    public static AddEditUsuarioFragment newInstance(String lawyerId) {
        AddEditUsuarioFragment fragment = new AddEditUsuarioFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_usuario, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mNombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mProyectManagerField = (TextInputEditText) root.findViewById(R.id.et_proyect_manager);
        mDescripcionField = (TextInputEditText) root.findViewById(R.id.et_descripcion);
        mDesarrollador1Field = (TextInputEditText) root.findViewById(R.id.et_desarrollador1);
        mDesarrollador2Field = (TextInputEditText) root.findViewById(R.id.et_desarrollador2);

        mNombreLabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mProyectManagerLabel = (TextInputLayout) root.findViewById(R.id.til_proyect_manager);
        mDescripcionLabel = (TextInputLayout) root.findViewById(R.id.til_descripcion);
        mDesarrollador1Label = (TextInputLayout) root.findViewById(R.id.til_desarrollador1);
        mDesarrollador2Label = (TextInputLayout) root.findViewById(R.id.til_desarrollador2);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditUsuario();
            }
        });

        mUsuarioDbHelper = new UsuarioDb(getActivity());

        // Carga de datos
        if (mUsuarioId != null) {
            loadUsuario();
        }

        return root;
    }

    private void loadUsuario() {
        // AsyncTask
        new GetUsuarioByIdTask().execute();
    }



    private class AddEditUsuarioTask extends AsyncTask<Usuario, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Usuario... usuarios) {
            if (mUsuarioId != null) {
                return mUsuarioDbHelper.updateUsuario(usuarios[0], mUsuarioId) > 0;

            } else {
                return mUsuarioDbHelper.saveUsuario(usuarios[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showUsuarioScreen(result);
        }

    }

    private void showUsuarioScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void addEditUsuario() {
        boolean error = false;

        String nombre = mNombreField.getText().toString();
        String descripcion = mDescripcionField.getText().toString();
        String proyect_manager = mProyectManagerField.getText().toString();
        String desarrollo1 = mDesarrollador1Field.getText().toString();
        String desarrollo2 = mDesarrollador2Field.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            mNombreLabel.setError("Hay un error");
            error = true;
        }

        if (TextUtils.isEmpty(descripcion)) {
            mDescripcionLabel.setError("Hay un error");
            error = true;
        }

        if (TextUtils.isEmpty(proyect_manager)) {
            mProyectManagerLabel.setError("Hay un error");
            error = true;
        }


        if (TextUtils.isEmpty(desarrollo1)) {
            mDesarrollador1Label.setError("Hay un error");
            error = true;
        }

        if (TextUtils.isEmpty(desarrollo2)) {
            mDesarrollador2Label.setError("Hay un error");
            error = true;
        }

        if (error) {
            return;
        }

        Usuario usuario = new Usuario(nombre, proyect_manager, descripcion, desarrollo1, desarrollo2);

        new AddEditUsuarioTask().execute(usuario);

    }

    private void showUsuario(Usuario usuario){
        mNombreField.setText(usuario.getNombre());
        mProyectManagerField.setText(usuario.getProject());
        mDescripcionField.setText(usuario.getDescripcion());
        mDesarrollador1Field.setText(usuario.getDesarrollador1());
        mDesarrollador2Field.setText(usuario.getDesarrollador2());

    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar usuario", Toast.LENGTH_SHORT).show();
    }

    private class GetUsuarioByIdTask extends AsyncTask<Void, Void, Cursor> {


        @Override
        protected Cursor doInBackground(Void... voids) {
            return mUsuarioDbHelper.getUsuarioById(mUsuarioId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showUsuario(new Usuario(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }
}