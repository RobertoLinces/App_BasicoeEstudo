package agendacontato.projeto.android.agendacontato;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import agendacontato.projeto.android.agendacontato.database.DataBase;
import agendacontato.projeto.android.agendacontato.dominio.entidades.Contato;
import agendacontato.projeto.android.agendacontato.dominio.repositorioContato;

public class ActCadContatos extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextEmail;
    private EditText editTextDatasEspeciais;
    private EditText editTextEndereco;
    private EditText editTextGrupos;

    private Spinner spinnerTelefone;
    private Spinner spinnerEmail;
    private Spinner spinnerEndereco;
    private Spinner spinnerDatasEspeciais;

    private ArrayAdapter<String> adapterTelefone;
    private ArrayAdapter<String> adapterEmail;
    private ArrayAdapter<String> adapterEndereco;
    private ArrayAdapter<String> adapterDatasEspecias;


    private DataBase dataBase;
    private SQLiteDatabase conexao;
    private agendacontato.projeto.android.agendacontato.dominio.repositorioContato repositorioContato;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_contatos);

        editTextNome = findViewById(R.id.editTextNomeId);
        editTextTelefone = findViewById(R.id.editTextTelefoneId);
        editTextEmail = findViewById(R.id.editTextEmailId);
        editTextDatasEspeciais = findViewById(R.id.editTextDatasEspeciaisId);
        editTextEndereco = findViewById(R.id.editTextEnderecoId);
        editTextGrupos = findViewById(R.id.editTextGrupoId);

        spinnerTelefone = findViewById(R.id.spinnerTelefoneId);
        spinnerEmail = findViewById(R.id.spinnerEmailId);
        spinnerEndereco = findViewById(R.id.spinnerEnderecoId);
        spinnerDatasEspeciais = findViewById(R.id.spinnerDatasEspeciaisId);

        adapterTelefone = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adapterTelefone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterEmail = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adapterEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterEndereco = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adapterEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterDatasEspecias = new ArrayAdapter<String>(ActCadContatos.this, android.R.layout.simple_spinner_item);
        adapterDatasEspecias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTelefone.setAdapter(adapterTelefone);
        spinnerEmail.setAdapter(adapterEmail);
        spinnerEndereco.setAdapter(adapterEndereco);
        spinnerDatasEspeciais.setAdapter(adapterDatasEspecias);

        adapterEmail.add("Casa");
        adapterEmail.add("Trabalho");
        adapterEmail.add("Outros");

        adapterTelefone.add("Celular");
        adapterTelefone.add("Trabalho");
        adapterTelefone.add("Casa");
        adapterTelefone.add("Principal");
        adapterTelefone.add("Fax Trabalho");
        adapterTelefone.add("Fax Casa");
        adapterTelefone.add("Pager");
        adapterTelefone.add("Outros");

        adapterEndereco.add("Casa");
        adapterEndereco.add("Trabalho");
        adapterEndereco.add("Outros");

        adapterDatasEspecias.add("Aniversário");
        adapterDatasEspecias.add("Data Comemorativa");
        adapterDatasEspecias.add("Outros");


        ExibeDataListener listener = new ExibeDataListener();
        editTextDatasEspeciais.setOnClickListener(listener);
        editTextDatasEspeciais.setOnFocusChangeListener(listener);


        Bundle bundle = getIntent().getExtras();

        if((bundle!= null) && (bundle.containsKey("CONTATO")) ){

            contato = (Contato) bundle.getSerializable("CONTATO");

            preencheDados();

        }else {


            contato = new Contato();

        }

        try {


            dataBase = new DataBase(ActCadContatos.this);

            conexao = dataBase.getWritableDatabase();

            repositorioContato = new repositorioContato(conexao);

        }catch (SQLException ex){

            AlertDialog.Builder alertaDataBase = new AlertDialog.Builder(ActCadContatos.this);
            alertaDataBase.setMessage("ERRO ao criar o BANCO !!! " + ex.getMessage());
            alertaDataBase.setNeutralButton("OK", null);
            alertaDataBase.show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.act_cad_contatos,menu);

        if(contato.getId() != 0) {
            menu.getItem(1).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_acao_salvar:

                salvar();
                finish();

                break;

            case R.id.menu_acao_excluir:

                excluir();
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);

    }


    private void preencheDados(){

       editTextNome.setText(contato.getNome() );

       editTextTelefone.setText(contato.getTelefone());
       spinnerTelefone.setSelection(Integer.parseInt(contato.getTipoTelefone()));

       editTextEmail.setText(contato.getEmail());
       spinnerEmail.setSelection(Integer.parseInt(contato.getTipoEmail()));

       editTextEndereco.setText(contato.getEndereco());
       spinnerEndereco.setSelection(Integer.parseInt(contato.getTipoEndereco()));


        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format( contato.getDatasEspeciais() );

       editTextDatasEspeciais.setText(dt);
       spinnerDatasEspeciais.setSelection(Integer.parseInt(contato.getTipoDatasEspeciais()));

       editTextGrupos.setText(contato.getGrupos());

    }

    private void excluir(){


        try {

            repositorioContato.excluir(contato.getId());

        }catch (Exception ex){

        AlertDialog.Builder alertaDataBase = new AlertDialog.Builder(ActCadContatos.this);
        alertaDataBase.setMessage("ERRO ao Deletar os Dados !!! " + ex.getMessage());
        alertaDataBase.setNeutralButton("OK", null);
        alertaDataBase.show();

        }

    }

    private void salvar(){

        try {

        contato.setNome(editTextNome.getText().toString());
        contato.setTelefone(editTextTelefone.getText().toString());
        contato.setEmail(editTextEmail.getText().toString());
        contato.setEndereco(editTextEndereco.getText().toString());
        contato.setGrupos(editTextGrupos.getText().toString());

        contato.setTipoTelefone(String.valueOf(spinnerTelefone.getSelectedItemPosition()));
        contato.setTipoEndereco(String.valueOf(spinnerEndereco.getSelectedItemPosition()));
        contato.setTipoEmail(String.valueOf(spinnerEmail.getSelectedItemPosition()));
        contato.setTipoDatasEspeciais(String.valueOf(spinnerDatasEspeciais.getSelectedItemPosition()));

        if(contato.getId()==0) {

            repositorioContato.inserir(contato);
        }else{

            repositorioContato.alterar(contato);

        }

        }catch (Exception ex){

            AlertDialog.Builder alertaDataBase = new AlertDialog.Builder(ActCadContatos.this);
            alertaDataBase.setMessage("ERRO ao Inserir os Dados !!! " + ex.getMessage());
            alertaDataBase.setNeutralButton("OK", null);
            alertaDataBase.show();

        }

    }


    private void exibeData(){

        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogData = new DatePickerDialog(ActCadContatos.this, new SelecionaDataListener() , ano , mes , dia );
        dialogData.show();

    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener   {


        @Override
        public void onClick(View view) {

            exibeData();

        }

        @Override
        public void onFocusChange(View view, boolean b) {

            //VERIFICA SE A CAIXA ESTA NO FOCUS----------------------
            if(b){
            exibeData();

            }

        }
    }


    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{


        @Override
        public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {


            Calendar calendar = Calendar.getInstance();
            calendar.set(ano,mes,dia);

            Date date = calendar.getTime();

            DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String dt = format.format(date);

            editTextDatasEspeciais.setText(dt);

            contato.setDatasEspeciais(date);


        }


    }

}
