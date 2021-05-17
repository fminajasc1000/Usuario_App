package usuario;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.usuarioapp.R;
import data.UsuarioProyecto.UsuarioEntry;

/**
 * Addaptador de Usuarios
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 * */
public class UsuarioCursorAdapter extends CursorAdapter {

    public UsuarioCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    /**
     * Implementa una nueva vista
     * @param context contexto de la aplicacion
     * @param cursor informacion de la db
     * @param parent Vista
     * @return View la vista
     * */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_usuario, parent, false);
    }

    /**
     * Obtiene los valores de las columnas para luego serearlos en los view
     * @param view vista
     * @param cursor informacion de la db
     * @param context contexto
     * */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_nombre);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(UsuarioEntry.NOMBRE));

        // Setup.
        nameText.setText(name);
    }
}
