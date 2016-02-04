package devas.com.whatchaap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText passwd, username;
    AppCompatTextView reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserDetails ud = new UserDetails(this);
        if(ud.isUserSet() == 1) {
           // ud.setDetails(u, p, this);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        username = (AppCompatEditText) findViewById(R.id.username);
        passwd  = (AppCompatEditText) findViewById(R.id.pass);
        reg = (AppCompatTextView) findViewById(R.id.register);
        AppCompatButton log = (AppCompatButton) findViewById(R.id.login);

        log.setOnClickListener(this);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String u = username.getText().toString().trim();
        String p = passwd.getText().toString().trim();
        Log.d(u, p);

        if((!u.equals("") && !p.equals(""))) {
            UserDetails ud = new UserDetails(this);
            ud.setDetails(u, p , this);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();

        }
    }
}
