package agendacontato.projeto.android.agendacontato.database;

/**
 * Created by Jose on 18/01/2018.
 */
import android.content.Context;
import android.database.sqlite.*;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context){

        super(context,"AGENDA",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(ScriptSql.getCreateContato() );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
