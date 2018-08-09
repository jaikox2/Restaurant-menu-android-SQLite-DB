package net.simplifiedcoding.bottomnavigationexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    EditText EdUser,passED;
    Button login;
    String user,pass;
    //private String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        EdUser =(EditText)findViewById(R.id.edtUser);
        passED =(EditText)findViewById(R.id.edtPasswd);
        login = (Button)findViewById(R.id.btnLogin);






        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateUser();
            }
        });
    }

    public void ValidateUser(){

         user = EdUser.getText().toString();
         pass = passED.getText().toString();

        System.out.println("User"+user);
        System.out.println("Pass"+pass);

        if(user.equals("admin") && pass.equals("12345")){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else if(user.equals("user") && pass.equals("12345")){
            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
        } else {
            Toast.makeText(getApplicationContext(), "PASSWORD WRONG", Toast.LENGTH_SHORT).show();
        }

    }
}
