package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUp";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private TextView back2login;
    private TextInputLayout email, name, password, password2;
    private Button btnSignUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        email = (TextInputLayout) findViewById(R.id.textInputEmail);
        password = (TextInputLayout) findViewById(R.id.textInputPassword);
        password2 = (TextInputLayout) findViewById(R.id.textInputPassword2);
        name = (TextInputLayout) findViewById(R.id.textInputName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        back2login = (TextView) findViewById(R.id.back2login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getEditText().getText().toString().trim();
                String Password = password.getEditText().getText().toString().trim();
                String Password2 = password2.getEditText().getText().toString().trim();
                String Name = name.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    email.setError(getString(R.string.empty_email));
                    return;
                } else {email.setError(null);}

                if (TextUtils.isEmpty(Name)) {
                    name.setError(getString(R.string.empty_name));
                    return;
                } else {name.setError(null);}

                if (TextUtils.isEmpty(Password)) {
                    password.setError(getString(R.string.empty_password));
                    return;
                } else {password.setError(null);}

                if (TextUtils.isEmpty(Password2)) {
                    password2.setError(getString(R.string.empty_password));
                    return;
                } else {password2.setError(null);}

                if (Password.length() < 6) {
                    password.setError(getString(R.string.short_password));
                    return;
                } else {password.setError(null);}

                if (!Password.equals(Password2)) {
                    password2.setError(getString(R.string.not_match_password));
                    return;
                } else {password2.setError(null);}

                progressBar.setVisibility(View.VISIBLE);
                createAccount(Email, Password, Name);

            }
        });

    }

    private void createAccount(String email, String password, String name) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this, R.string.sign_up_success,
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            writeNewUser(user.getUid(),name, email);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });

                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, R.string.sign_up_fail,
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    // Go to Login activity when click
    public void back2LoginClick(View view){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // Save user info to database
    public void writeNewUser(String userId, String name, String email) {
        User user = new User(email, name);
        mDatabase.child("users").child(userId).setValue(user);
    }
}
