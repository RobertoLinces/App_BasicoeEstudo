package frasedodia.projeto.android.frasedodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button botaoNovaFrase;
    private TextView textoNovaFrase;
    private String[] frases = {
            "É mais fácil obter o que se deseja com um sorriso do que à ponta da espada." ,
            "Viver é a coisa mais rara do mundo. A maioria das pessoas apenas existe.",
            "O que não provoca minha morte faz com que eu fique mais forte.",
            "As mais lindas palavras de amor são ditas no silêncio de um olhar.",
            "O verdadeiro amor nunca se desgasta. Quanto mais se dá mais se tem.",
            "Triste época! É mais fácil desintegrar um átomo do que um preconceito.",
            "As mais lindas palavras de amor são ditas no silêncio de um olhar.",
            "Quanto mais nos elevamos, menores parecemos aos olhos daqueles que não sabem voar.",
            "Sendo assim, cada ação de luta, poderes e magias, devem ser entendidos apenas como um entretenimento, referenciado nas tantas guerras e episódios naturais que já presenciamos na vida, ou na ficção. "
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoNovaFrase = findViewById(R.id.botaoNovaFraseId);
        textoNovaFrase = findViewById(R.id.textoNovaFraseId);

        //textoNovaFrase.setText(frases[1]);

        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random randomico = new Random();
                int numeroAleatorio = randomico.nextInt(frases.length);
                textoNovaFrase.setText(frases[numeroAleatorio]);
            }
        });
    }
}
