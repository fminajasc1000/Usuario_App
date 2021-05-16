package addeditusuario;

import android.os.Bundle;

import com.example.usuarioapp.R;
import usuario.UsuarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

public class AddEditUsuarioActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_USUARIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String lawyerId = getIntent().getStringExtra(UsuarioActivity.EXTRA_USUARIO_ID);

        setTitle(lawyerId == null ? "Añadir usuario" : "Editar usuario");

        AddEditUsuarioFragment addEditUsuarioFragment = (AddEditUsuarioFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_usuario_container);
        if (addEditUsuarioFragment == null) {
            addEditUsuarioFragment = AddEditUsuarioFragment.newInstance(lawyerId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_usuario_container, addEditUsuarioFragment)
                    .commit();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}