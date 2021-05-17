package usuario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.usuarioapp.R;

public class UsuarioActivity extends AppCompatActivity {

    public static final String EXTRA_USUARIO_ID = "extra_usuario_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UsuarioFragment fragment = (UsuarioFragment)
                getSupportFragmentManager().findFragmentById(R.id.usuario_container);

        if (fragment == null) {
            fragment = UsuarioFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.usuario_container, fragment)
                    .commit();
        }

    }
}
