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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;
import mobildev.iosm.com.ecom.PreValent.PreValent;
import mobildev.iosm.com.ecom.Model.Users;

public class LoginActivity extends AppCompatActivity {
    private EditText inputPhoneNbr,inputPassword;
    Button loginBtn;
    ProgressDialog LoadingBar;
    String parentDbName="Users";
    private CheckBox checkBoxRemembreMe;
    private TextView AdminLink,NotAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn =findViewById(R.id.login_btn);
        inputPassword=findViewById(R.id.login_password_input);
        inputPhoneNbr=findViewById(R.id.login_nbrPhone_input);
        NotAdminLink=findViewById(R.id.not_admin_panel_link);
        AdminLink=findViewById(R.id.admin_panel_link);


        checkBoxRemembreMe=findViewById(R.id.remember_me_chk);
        Paper.init(this);


        LoadingBar = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUsers();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";
                Toast.makeText(LoginActivity.this, "welcome Admin You Are", Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText(" Login ");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });

    }

    private void loginUsers()
    {
        String password=inputPassword.getText().toString();
        String phoneNbr=inputPhoneNbr.getText().toString();

        if(TextUtils.isEmpty(phoneNbr)){
            Toast.makeText(this,"Please write your Phone Numbre",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please Wait , While We Are Checking  The Credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            AlowAccessToAccount(phoneNbr,password);

        }
    }

    private void AlowAccessToAccount(final String phoneNbr, final String password)
    {

        if(checkBoxRemembreMe.isChecked())
        {
            Paper.book().write(PreValent.UserPhoneKey,phoneNbr);
            Paper.book().write(PreValent.UserPasswordKey,password);
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {                                       //unique is the phone
                if(dataSnapshot.child(parentDbName).child(phoneNbr).exists())
                {
                    Users userData=dataSnapshot.child(parentDbName).child(phoneNbr).getValue(Users.class);//recupere les donnéer saisir
                    if(userData.getPhone().equals(phoneNbr)){
                        if(userData.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins")){

                                Toast.makeText(LoginActivity.this, "welcome Admin You Are, Logged in Successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                                Intent myIntent = new Intent(LoginActivity.this,AdminCategoryActivity.class);
                                startActivity(myIntent);
                            }
                            else if(parentDbName.equals("Users"))
                            {

                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                                Intent myIntent = new Intent(LoginActivity.this,HomeActivity.class);
                                PreValent.CurrentOnLineUser = userData;//enregistrer les information du user
                                startActivity(myIntent);
                            }
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password Is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account With This Phone Number ( "+phoneNbr+" ) Not Exists", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
