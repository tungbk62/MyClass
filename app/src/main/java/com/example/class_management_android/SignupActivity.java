package com.example.class_management_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtRePassword;
    private Button btnSignup;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        edtEmail = (EditText) findViewById(R.id.edtEmailAccount);
        edtPassword = (EditText) findViewById(R.id.edtPasswordAccount);
        edtRePassword = (EditText) findViewById(R.id.edtRePasswordAccount);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSignUp();
            }
        });
    }

    private void checkSignUp(){
        String email = edtEmail.getText().toString();
        if(email.length() == 0){
            edtEmail.setError("?");
            edtEmail.requestFocus();
            return;
        }
        String password = edtPassword.getText().toString();
        if(password.length() == 0){
            edtPassword.setError("?");
            edtPassword.requestFocus();
            return;
        }
        String rePassword = edtRePassword.getText().toString();
        if(rePassword.length() == 0){
            edtRePassword.setError("?");
            edtRePassword.requestFocus();
            return;
        }

        if(password.equals(rePassword)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignupActivity.this, "Create Account Successfully. Login",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Create Account failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Confirm Your Password", Toast.LENGTH_LONG).show();
        }
    }
}