package umn.ac.id.uts_27658;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username, password;
    TextView us, pw;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        us = findViewById(R.id.tv_username);
        pw = findViewById(R.id.tv_password);
        login = findViewById(R.id.btn_masuk);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().trim().equals("uasmobile") &&
                        password.getText().toString().trim().equals("uasmobilegenap")){
                    username.setText("");
                    password.setText("");
                    alert();
                }else if(!"uasmobile".equals(username.getText().toString().trim())){
                    username.setText("");
                    us.setTextColor(Color.parseColor("#EF0A0A"));
                }else if(!"uasmobilegenap".equals(password.getText().toString().trim())){
                    password.setText("");
                    pw.setTextColor(Color.parseColor("#EF0A0A"));
                }
            }
        });
    }

    //Alert pop up
    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Selamat Datang");
        builder.setMessage("Louis Jonathan 00000027658");

        //// Is dismiss when touching outside?
        builder.setCancelable(false);

        //Negative Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                Intent intent = new Intent(getApplicationContext(), List.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.show();

        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        //TextView titleText = (TextView)dialog.findViewById(android.R.id.title);
        //Button okbtn = (Button)dialog.findViewById(android.R.id.closeButton);
        messageText.setGravity(Gravity.CENTER);
        //okbtn.setGravity(Gravity.CENTER);
        //titleText.setGravity(Gravity.CENTER);

        dialog.show();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "Klik back button di atas",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }

}