package devas.com.whatchaap;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jxmpp.stringprep.XmppStringprepException;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText passwd, username, email;
    private MyService mService;
    private boolean mBounded;

    private final ServiceConnection mConnection = new ServiceConnection() {

        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(final ComponentName name,
                                       final IBinder service) {
            mService = ((LocalBinder<MyService>) service).getService();
            mBounded = true;
            Log.d("REGISTER", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mService = null;
            mBounded = false;
            Log.d("REGISTER", "onServiceDisconnected");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        doBindService();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = (AppCompatEditText) findViewById(R.id.usernameR);
        passwd  = (AppCompatEditText) findViewById(R.id.passR);
        email = (AppCompatEditText) findViewById(R.id.emailR);
        AppCompatButton log = (AppCompatButton) findViewById(R.id.reg);
        log.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        String u = username.getText().toString().trim();
        String p = passwd.getText().toString().trim();
        String e = email.getText().toString().trim();
        Log.d(u, p);

        if((!u.equals("") && !p.equals(""))) {
           // UserDetails ud = new UserDetails(this);
            //ud.setDetails(u, p , this);
            try {
               if( mService.xmpp.addAccount(u, p, e)) {
                   Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(this, Login.class);
                   startActivity(i);
                   finish();
               }
            } catch (XmppStringprepException et) {
                et.printStackTrace();
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        doUnbindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doBindService();
    }

    void doBindService() {
        bindService(new Intent(this, MyService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    void doUnbindService() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
    }
    public MyService getmService() {
        return mService;
    }
}
