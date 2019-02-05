package com.example.week5day1firebasemessaging;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements UserLoginContract{
    FirebaseAuth firebaseAuth;
    EditText etEmail;
    EditText etPassword;
    TextView tvLoinResult;
    CallbackManager callbackManager;
    UserLoginPresenter userLoginPresenter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText etMessage;
    TextView tvReceiveMessage;
    private static final String MESSAGES = "messages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvLoinResult = findViewById(R.id.tvLoginResult);
        etMessage = findViewById(R.id.etMessage);
        tvReceiveMessage = findViewById(R.id.tvReceiveMessage);
        userLoginPresenter = new UserLoginPresenter(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        System.out.println("Key is: " + myRef.getKey());

        saveMessageToFirebaseDB(new Message("--", "--", "--", "6969" ));

    }


    private  void  saveMessageToFirebaseDB(Message message){
        String key = message.getKey();
        //message.setKey(key);
        myRef.child(key).setValue(message);
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "onDataChange: " + dataSnapshot.getKey() + " = " + dataSnapshot.getValue());
                String response = dataSnapshot.getValue().toString();
                System.out.println(response);
                Message onChangeMessage = new Gson().fromJson(response, Message.class);
                String time = onChangeMessage.getTime();
                tvReceiveMessage.setText("Received at: " +  time +
                        "\nFrom: " + onChangeMessage.getEmail() +
                        "\n\n" + onChangeMessage.getMessage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClick(final View view) {
        String email = etEmail.getText() != null ? etEmail.getText().toString() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString() : "";
        switch (view.getId()){
            case R.id.btnSignIn:
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        Toast.makeText(view.getContext(), "Signed in Successfully!", Toast.LENGTH_SHORT).show();
                                        //userLoginPresenter.upDateFirebaseUser(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(view.getContext(), "Sign in Failed!", Toast.LENGTH_SHORT).show();
                                        //userLoginPresenter.upDateFirebaseUser(null);
                                    }

                                    // ...
                                }
                            });
                }
                else {
                    tvLoinResult.setText("User Does not exist, Please ReEnter");
                    etEmail.setText("");
                    etPassword.setText("");
                }

                break;
            case R.id.btnSignUp:
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        Toast.makeText(view.getContext(), "Sign up Successful!", Toast.LENGTH_SHORT).show();
                                        //userLoginPresenter.upDateFirebaseUser(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(view.getContext(), "Sign up Failed!", Toast.LENGTH_SHORT).show();
                                        //userLoginPresenter.upDateFirebaseUser(null);
                                    }

                                    // ...
                                }
                            });
                }else{
                    tvLoinResult.setText("User Does not exist");
                    etEmail.setText("");
                    etPassword.setText("");
                }
                break;
            case R.id.btnSendMessage:
                userLoginPresenter.checkMessage(etMessage.getText().toString(), firebaseAuth.getCurrentUser());
                break;

        }
    }

    @Override
    public void MessageReturnCheck() {
            String message = "'" + etMessage.getText().toString() + "'";
            myRef.child("6969").child("message").setValue(message);
            myRef.child("6969").child("email").setValue(firebaseAuth.getCurrentUser().getEmail());
            Date date = new Date();
            String strDateFormat = "hh:mma";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= "'" + dateFormat.format(date) + "'";
            myRef.child("6969").child("time").setValue(formattedDate);


    }

    @Override
    public void MessageReturnBadCheck() {

            Toast.makeText(this, "Input a valid Value for Message!", Toast.LENGTH_SHORT).show();

    }
}

