package agendacontato.projeto.android.agendacontato;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.database.sqlite.*;
import android.database.*;

import agendacontato.projeto.android.agendacontato.database.DataBase;
import agendacontato.projeto.android.agendacontato.dominio.entidades.Contato;
import agendacontato.projeto.android.agendacontato.dominio.repositorioContato;

public class ActContato extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button adicionar;
    private EditText editTextPesquisa;
    private ListView listViewContados;
    private ArrayAdapter<Contato> arrayAdapterContatos;

    private DataBase dataBase;
    private SQLiteDatabase conexao;
    private repositorioContato repositorioContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contato);

        //INICIALIZANDO COMPONENTES-----------------------------------------------------------------
        adicionar = findViewById(R.id.botaoAdicionarId);
        editTextPesquisa = findViewById(R.id.editTextPesquisaId);
        listViewContados = findViewById(R.id.listViewContatosId);

        listViewContados.setOnItemClickListener(ActContato.this);


        try {


            dataBase = new DataBase(ActContato.this);

            conexao = dataBase.getWritableDatabase();

            repositorioContato = new repositorioContato(conexao);



            arrayAdapterContatos = repositorioContato.buscaContatos(ActContato.this);

            listViewContados.setAdapter(arrayAdapterContatos);


        }catch (SQLException ex){

            AlertDialog.Builder alertaDataBase = new AlertDialog.Builder(ActContato.this);
            alertaDataBase.setMessage("ERRO ao criar o BANCO !!! " + ex.getMessage());
            alertaDataBase.setNeutralButton("OK", null);
            alertaDataBase.show();

        }


        //EVENTO CLICK DO BOTAO ADICIONAR ----------------------------------------------------------
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActContato.this,ActCadContatos.class);

                startActivityForResult(intent, 0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        arrayAdapterContatos = repositorioContato.buscaContatos(ActContato.this);

        listViewContados.setAdapter(arrayAdapterContatos);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Contato contato = arrayAdapterContatos.getItem(position);

        Intent intent = new Intent(ActContato.this,ActCadContatos.class);
        intent.putExtra("CONTATO", contato);

        startActivityForResult(intent, 0);

    }
}
