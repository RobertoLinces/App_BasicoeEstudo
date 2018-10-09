package idadedecachorro.projeto.android.idadedecachorro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText caixaTexto;
    private Button botaoIdade;
    private TextView resultadoIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaTexto = findViewById(R.id.caixaTextoId);
        botaoIdade = findViewById(R.id.botaoFinalizarId);
        resultadoIdade = findViewById(R.id.resultadoIdadeId);

        botaoIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // recuperar o que foi digitado
                String textoDigitado = caixaTexto.getText().toString();

                if(textoDigitado.isEmpty()){
                    resultadoIdade.setText("Nenhum numero foi digitado !!! ");
                }else{
                    int valorDigitado = Integer.parseInt(textoDigitado);
                    int resultadoFinal = valorDigitado*7;

                    resultadoIdade.setText("A idade do cachorro em anos humano é de = " + resultadoFinal + " anos");
                }

            }
        });
    }
}
