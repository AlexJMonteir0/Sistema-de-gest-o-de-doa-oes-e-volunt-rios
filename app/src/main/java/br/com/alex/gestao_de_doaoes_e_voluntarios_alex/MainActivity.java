package br.com.alex.gestao_de_doaoes_e_voluntarios_alex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view.AuthenticationActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread1 = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(700);
                    startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                    finish();
                }catch (InterruptedException e){
                    throw new RuntimeException(e);

                }
            }

        };thread1.start();

    }
}