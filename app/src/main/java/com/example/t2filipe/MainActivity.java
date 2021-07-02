package com.example.t2filipe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    TextView pontos;
    int pontuacao;
    TextView txtPalavra; //txt word to be guessed;
    String palavraCompleta; //wordToBeGuessed
    String palavraAtual; // WordDisplayedString
    char[] palavraAtualArray; // wordDisplayedChar
    ArrayList<String> listaPalavras;
    EditText editText;
    TextView txtLetrasTentadas;
    String letrasTentadas;
    final String LETRAS_TENTADAS = "letras tentadas: ";
    TextView txtTentativas;
    String tentativas;
    final String GANHOU = "Parabens, você ganhou!";
    final String PERDEU = "perdeu ;/";

    public void reseta(View v){
        initJogo();
    }
    public void mostraPontuacao(){

    }
    void mostraLetra(char letra){
        int letraPos = palavraCompleta.indexOf(letra);
        while(letraPos >= 0){
            palavraAtualArray[letraPos] = palavraCompleta.charAt(letraPos);
            letraPos = palavraCompleta.indexOf(letra, letraPos+1);
        }
        palavraAtual = String.valueOf(palavraAtualArray);
    }
    void mostraPalavra(){
        String stringFormatada = "";
        for (char caractere : palavraAtualArray){
            stringFormatada += caractere + " ";
        }
        txtPalavra.setText(stringFormatada);
    }
    void initJogo(){
        //aleatorio e pega primeira palavra
        Collections.shuffle(listaPalavras);
        palavraCompleta = listaPalavras.get(0);
        listaPalavras.remove(0);

        //char
        palavraAtualArray = palavraCompleta.toCharArray();
        //adiciona _
        for (int i = 0; i < palavraAtualArray.length;i++){
            palavraAtualArray[i] = '_';
        }

        palavraAtual = String.valueOf(palavraAtualArray);
        mostraPalavra();
        //input
        editText.setText("");
        //letras tentadas
        letrasTentadas = " ";
        //mostra
        txtLetrasTentadas.setText(LETRAS_TENTADAS);
        //tentativas faltando
        tentativas = " X X X X X";
        txtTentativas.setText(tentativas);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.vega);
        mediaPlayer.start();
        pontos = findViewById(R.id.pontuacao);
        listaPalavras = new ArrayList<>();
        txtPalavra = findViewById(R.id.txtPalavra);
        editText = findViewById(R.id.editAddLetra);
        txtLetrasTentadas = findViewById(R.id.txtLetrasTentadas);
        txtTentativas = findViewById(R.id.txtTentativas);
        InputStream iStream = null;
        Scanner entrada = null;
        String plv = "";
        try {
            iStream = getAssets().open("palavras");
            entrada = new Scanner(iStream);
            while(entrada.hasNext()){
                plv = entrada.next();
                listaPalavras.add(plv);
            }
        }catch (IOException e){
            Toast.makeText(MainActivity.this, e.getClass().getSimpleName() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            if (entrada != null){
                entrada.close();
            }
            try {
                if (iStream != null){
                    iStream.close();
                }
            }catch (IOException e){
                Toast.makeText(MainActivity.this, e.getClass().getSimpleName() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        initJogo();
        //mudar o listener
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    temLetra(s.charAt(0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
    void temLetra(char letra){
        if(palavraCompleta.indexOf(letra) >= 0 ) {
            if (palavraAtual.indexOf(letra) < 0) {
                mostraLetra(letra);
                mostraPalavra();
                if (!palavraAtual.contains("_")) {
                    txtTentativas.setText(GANHOU);
                    pontuacao += palavraCompleta.length();
                    pontos.setText(pontuacao + "");
                }
            }
        }else{
            if (!tentativas.isEmpty()){
                tentativas = tentativas.substring(0, tentativas.length()-2);
                txtTentativas.setText(tentativas);
            }
            if (tentativas.isEmpty()){
                txtTentativas.setText(PERDEU);
                txtPalavra.setText(palavraCompleta);
            }
        }

        if (letrasTentadas.indexOf(letra) < 0){
            letrasTentadas += letra + ",";
            String mostraLetras = LETRAS_TENTADAS + letrasTentadas;
            txtLetrasTentadas.setText(mostraLetras);
        }

    }

}