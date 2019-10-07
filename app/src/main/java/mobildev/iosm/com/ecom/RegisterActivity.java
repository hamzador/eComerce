package mobildev.iosm.com.ecom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountBtn;
    private EditText inputUsername,inputPassword,inputPhoneNbr;
    ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountBtn=findViewById(R.id.register_btn);
        inputPassword=findViewById(R.id.register_password_input);
        inputUsername=findViewById(R.id.register_username_input);
        inputPhoneNbr=findViewById(R.id.register_nbrPhone_input);

        LoadingBar =new ProgressDialog(this);

        CreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();

            }
        });
    }

    private void CreateAccount() {
        String username=inputUsername.getText().toString();
        String password=inputPassword.getText().toString();
        String phoneNbr=inputPhoneNbr.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please write your Username",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your Password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneNbr)){
            Toast.makeText(this,"Please write your Phone Numbre",Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.setTitle("Create Account");
            LoadingBar.setMessage("Please Wait , While We Are Checking  The Credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            ValidatePhoneNbr(username,password,phoneNbr);
        }

    }

    private void ValidatePhoneNbr(final String username, final String password, final String phoneNbr)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //le numero de tele n'existe pas
                if(!(dataSnapshot.child("Users").child(phoneNbr)).exists())
                {
                    HashMap<String , Object> userdataMap=new HashMap<>();
                    userdataMap.put("phone",phoneNbr);
                    userdataMap.put("username",username);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(phoneNbr).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, " Congratulations your Account has been Created ", Toast.LENGTH_SHORT).show();
                                        LoadingBar.dismiss();

                                        Intent myIntent =new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(myIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, " Network Error: Please Try Again After some time ...", Toast.LENGTH_SHORT).show();
                                        LoadingBar.dismiss();
                                    }
                                }
                            });
                }
                else
                    {
                        Toast.makeText(RegisterActivity.this,"This Phone Number  ("+phoneNbr+")  Already Exist",Toast.LENGTH_LONG).show();
                        LoadingBar.dismiss();
                        //give a suggetion
                        Toast.makeText(RegisterActivity.this, "Please Try Again Using An Other Phone Number", Toast.LENGTH_SHORT).show();
                        Intent myIntent =new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(myIntent);
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
