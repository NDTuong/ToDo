package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private TextView signUp;
    private TextInputLayout email, password;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        email = (TextInputLayout) findViewById(R.id.textInputEmail);
        password = (TextInputLayout) findViewById(R.id.textInputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        signUp = (TextView) findViewById(R.id.back2login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(v -> {
            String Email = email.getEditText().getText().toString().trim();
            String Password = password.getEditText().getText().toString().trim();

            if (TextUtils.isEmpty(Email)) {
                email.setError(getString(R.string.empty_email));
                return;
            } else {email.setError(null);}

            if (TextUtils.isEmpty(Password)) {
                password.setError(getString(R.string.empty_password));
                return;
            } else {password.setError(null);}
            progressBar.setVisibility(View.VISIBLE);
            login(Email, Password);
        });

    }

    private void login(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.login_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
        // [END sign_in_with_email]
    }

    public void clickSignUp(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void clickForgotPassword(View view){
        Dialog mDialog;
        mDialog=new Dialog(LoginActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_forgot_password);
        Button send,cancel;
        TextInputLayout email;
        ProgressBar progressBar2;
        send =(Button) mDialog.findViewById(R.id.btnSend);
        cancel =(Button) mDialog.findViewById(R.id.btnCancel);
        email = (TextInputLayout)  mDialog.findViewById(R.id.textInputEmail);
        progressBar2 = (ProgressBar) mDialog.findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);
        send.setOnClickListener(v -> {
            String forgot_email = email.getEditText().getText().toString().trim();
            if (TextUtils.isEmpty(forgot_email)) {
                email.setError(getString(R.string.empty_email));
                return;
            }
            progressBar2.setVisibility(View.VISIBLE);
            mAuth = FirebaseAuth.getInstance();
            mAuth.sendPasswordResetEmail(forgot_email)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Thành công! Vui lòng kiểm tra email.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Không thành công! Vui lòng kiểm tra lại email đã nhập.", Toast.LENGTH_SHORT).show();
                            }

                            progressBar2.setVisibility(View.GONE);
                        }
                    });

            mDialog.cancel();

        });
        cancel.setOnClickListener(v -> {
            mDialog.cancel();
        });
        mDialog.show();
    }
}