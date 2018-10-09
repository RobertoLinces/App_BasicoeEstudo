package agendacontato.projeto.android.agendacontato.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.widget.*;

import java.util.Date;

import agendacontato.projeto.android.agendacontato.R;
import agendacontato.projeto.android.agendacontato.dominio.entidades.Contato;

/**
 * Created by Jose on 18/01/2018.
 */

public class repositorioContato {

    private SQLiteDatabase conexao;

    public repositorioContato(SQLiteDatabase conexao){

        repositorioContato.this.conexao = conexao;
    }


    private ContentValues preencheContentValues(Contato contato){

        ContentValues values = new ContentValues();
        values.put("NOME", contato.getNome() );
        values.put("TELEFONE", contato.getTelefone() );
        values.put("TIPOTELEFONE", contato.getTipoTelefone() );
        values.put("EMAIL", contato.getEmail() );
        values.put("TIPOEMAIL", contato.getTipoEmail() );
        values.put("ENDERECO", contato.getEndereco() );
        values.put("TIPOENDERECO", contato.getTipoEndereco() );
        values.put("DATASESPECIAIS", contato.getDatasEspeciais().getTime());
        values.put("TIPODATASESPECIAIS", contato.getTipoDatasEspeciais() );
        values.put("GRUPOS", contato.getGrupos() );

        return values;

    }

    public void excluir(long id){

        conexao.delete("CONTATO", " _id = ? ", new String[]{ String.valueOf(id)} );

    }

    public void alterar(Contato contato){


        ContentValues values = preencheContentValues(contato);
        conexao.update("CONTATO", values, " _id = ? ", new String[]{ String.valueOf(contato.getId())});


    }

    public void inserir(Contato contato){

        ContentValues values = preencheContentValues(contato);

        conexao.insertOrThrow("CONTATO", null, values);


    }



    public ArrayAdapter<Contato> buscaContatos(Context context){

        ArrayAdapter<Contato> adapterContatos = new ArrayAdapter<Contato>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conexao.query("CONTATO", null,null,null,null,null,null);

        if(cursor.getCount()>0){

            cursor.moveToFirst();

            do {
                Contato contato = new Contato();

                contato.setId(cursor.getLong(cursor.getColumnIndex(Contato.ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndex(Contato.NOME)) );
                contato.setTelefone(cursor.getString(cursor.getColumnIndex(Contato.TELEFONE)) );
                contato.setTipoTelefone(cursor.getString(cursor.getColumnIndex(Contato.TIPOTELEFONE)) );
                contato.setEmail(cursor.getString(cursor.getColumnIndex(Contato.EMAIL)) );
                contato.setTipoEmail(cursor.getString(cursor.getColumnIndex(Contato.TIPOEMAIL)) );
                contato.setEndereco(cursor.getString(cursor.getColumnIndex(Contato.ENDERECO)) );
                contato.setTipoEndereco(cursor.getString(cursor.getColumnIndex(Contato.TIPOENDERECO)) );
                contato.setDatasEspeciais(new Date(cursor.getLong(cursor.getColumnIndex(Contato.DATASESPECIAIS))));
                contato.setTipoDatasEspeciais(cursor.getString(cursor.getColumnIndex(Contato.TIPODATASESPECIAIS)) );
                contato.setGrupos(cursor.getString(cursor.getColumnIndex(Contato.GRUPOS)) );

                adapterContatos.add(contato);

            }while (cursor.moveToNext());
        }


        return adapterContatos;

    }

}
