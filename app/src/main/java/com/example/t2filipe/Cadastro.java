package com.example.t2filipe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {
    EditText nome, usuario, senha, confirmaSenha;
    Button cadastrar;
    Cursor cursor;
    CheckBox mostrar;
    SQLiteDatabase db;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        dbHelper = new DBHelper(this);

        nome = findViewById(R.id.edtNome);
        usuario = findViewById(R.id.edtUusarioCadastro);
        senha = findViewById(R.id.edtSenhaCadastro);
        confirmaSenha = findViewById(R.id.edtConfirma);
        mostrar = findViewById(R.id.mostraSenhaCadastro);
        cadastrar = findViewById(R.id.btnCadastro);

        mostrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmaSenha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmaSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
    public void cadastrarBtn(View view) {

        if(nome.getText().toString().equals("")||
                usuario.getText().toString().equals("")||
                senha.getText().toString().equals("")||confirmaSenha.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Android studio não funciona no meu pc", Toast.LENGTH_LONG).show();
            return;
        }

        if(!senha.getText().toString().equals(confirmaSenha.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Senhas estão diferentes", Toast.LENGTH_LONG).show();
        }else {
            dbHelper.addUser(nome.getText().toString(),
                    usuario.getText().toString(), senha.getText().toString(),
                    confirmaSenha.getText().toString());

            Toast.makeText(Cadastro.this, "Foi!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Cadastro.this, Login.class);
            startActivity(intent);
        }
    }
}
