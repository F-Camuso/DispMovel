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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText usuario, senha;
    Button btn;
    TextView semCadastro;
    Cursor cursor;
    CheckBox mostrar;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        semCadastro = findViewById(R.id.txtSemCadastro);
        usuario = findViewById(R.id.edtUsuario);
        senha = findViewById(R.id.edtSenha);
        mostrar = findViewById(R.id.mostraSenha);
        btn = findViewById(R.id.btnLogin);
        semCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor = db.rawQuery("SELECT * FROM " + DBHelper.USER_TABLE + " WHERE "
                                + DBHelper.COLUMN_USERNAME + " =? AND " + DBHelper.COLUMN_PASSWORD + " =?",
                        new String[]{usuario.getText().toString(), senha.getText().toString()});

                if(usuario.getText().toString().equals("")||
                        senha.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "campos vazios", Toast.LENGTH_LONG).show();
                    return;
                }

                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        Toast.makeText(Login.this, "Logou!",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        //muda aqui pra ir pro jogo
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Algo invalido!",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        mostrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }
}
