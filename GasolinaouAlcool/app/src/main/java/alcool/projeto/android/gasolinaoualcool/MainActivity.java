package alcool.projeto.android.gasolinaoualcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button botaoVerificar;
    private TextView textoResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Definição dos elementos
        precoAlcool = findViewById(R.id.alcoolId);
        precoGasolina = findViewById(R.id.gasolinaId);
        botaoVerificar = findViewById(R.id.botaoVerificarId);
        textoResultado = findViewById(R.id.resultadoId);

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperar os valores Digitados

                String TextoPrecoAlcool = precoAlcool.getText().toString();
                String TextoPrecoGasolina = precoGasolina.getText().toString();

                double valorResultado = 0;

                if(TextoPrecoAlcool.isEmpty() || TextoPrecoGasolina.isEmpty()){
                    textoResultado.setText("Digite o valor do Álcool e da Gasolina para Verificar o melhor custo benefício !!!" + "\n Não é possível verificar se não digitar os valores para alcool e gasolina diferentes de 0. ");
                }else{
                    //double valorAlcool = Double.parseDouble(TextoPrecoAlcool);
                    double valorAlcool = Double.parseDouble(TextoPrecoAlcool);
                    double valorGasolina = Double.parseDouble(TextoPrecoGasolina);
                    valorResultado = valorAlcool / valorGasolina;

                    if(valorResultado >= 0.7){
                        //Gasolina melhor
                        textoResultado.setText("É melhor Utilizar a Gasolina !!!");

                    }else{

                        textoResultado.setText("É melhor Utilizar o Álcool !!!");

                    }

                }

            }
        });
    }
}
