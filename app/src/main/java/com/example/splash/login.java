package com.example.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class login extends Activity {
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seva);
        b1=(Button)findViewById(R.id.login);
        b2=(Button)findViewById(R.id.register);

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(getApplicationContext(),loginin.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(getApplicationContext(),register.class);
                startActivity(i);
            }
        });
    }
}
