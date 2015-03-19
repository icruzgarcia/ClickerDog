package icruzgarcia.com.clickerdog;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class Mejoras extends SQLiteOpenHelper {
    private double modificadorTotal;
    private double modificadorIdle;

    /*
    * Creamos las variables para acceder a la base de datos
    *
    * */
    private SQLiteDatabase miBase;

    Context miContexto;
    private final static int VERSION = 1;
    private final static String NOMBRE_BASEDATOS = "mejoras.db";//Nombre de la base
    private final static String nombreTabla = "mejoras";
    private static String DESTINO_DB =null;

    public Mejoras(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION);
        this.miContexto = context;
        DESTINO_DB="/data/data/"+miContexto.getPackageName()+"/"+NOMBRE_BASEDATOS;
    }


    public void crearBase() {
        File miFich = miContexto.getDatabasePath(NOMBRE_BASEDATOS);
        Boolean existe = miFich.exists();
        if (existe) {
            Log.d("¿ESTA?", "LA BASE DE DATOS EXISTE,NO PASA NADA");
        } else {
            this.getWritableDatabase();
            try {
                copiarBase();
                Log.d("¿ESTA?", "LA BASE DE DATOS ESTA COPIADA");
            } catch (IOException e) {
                throw new Error("ERROR EN LA COPIA");
            }
        }


    }

    public void copiarBase() throws IOException {

        InputStream is = miContexto.getAssets().open(NOMBRE_BASEDATOS);
        OutputStream os = new FileOutputStream(DESTINO_DB);
        byte[] buffer = new byte[1024];
        int longitud;
        while ((longitud = is.read(buffer)) > 0) {
            os.write(buffer, 0, longitud);

        }
        os.flush();
        os.close();
        is.close();

    }

    public void abrirBase() throws SQLException {

            crearBase();
                miBase=SQLiteDatabase.openDatabase(DESTINO_DB,null,SQLiteDatabase.OPEN_READWRITE);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + nombreTabla);
        onCreate(db);
    }

    public Mejora recogerModificador(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] mejora_recuperar = {"id", "nombre", "coste", "descripcion", "comprada", "pasivo"};
        Cursor c = null;//miBase.rawQuery("SELECT * from mejoras where id="+id,null);
        if (c != null) {
            c.moveToFirst();
        }
        Mejora mejora = new Mejora(c.getInt(0), c.getString(1), c.getDouble(2), c.getString(3), c.getInt(4), c.getInt(5));
        db.close();
        c.close();

        return mejora;

    }


    public void setModificadorTotal(double mod) {

        this.modificadorTotal = mod;
    }

    public double getModificadorTotal() {
        return this.modificadorTotal;

    }

    public void setModificadorIdle(double mod) {
        this.modificadorIdle = mod;

    }

    public double getModificadorIdle() {
        return this.modificadorIdle;

    }


}
