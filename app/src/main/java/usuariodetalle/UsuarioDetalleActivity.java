package usuariodetalle;

import android.os.Bundle;

import com.example.usuarioapp.R;
import usuario.UsuarioActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
/**
 * Vista para el detalle del usuario
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 */
public class UsuarioDetalleActivity extends AppCompatActivity {
    /**
     * Se realiza la apertura de la actividad contenedora
     * y la transaccion de agregacion, esta recibe el id del usuario*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(UsuarioActivity.EXTRA_USUARIO_ID);

        UsuarioDetalleFragment fragment = (UsuarioDetalleFragment)
                getSupportFragmentManager().findFragmentById(R.id.usuario_detalle_container);
        if (fragment == null) {
            fragment = UsuarioDetalleFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.usuario_detalle_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}