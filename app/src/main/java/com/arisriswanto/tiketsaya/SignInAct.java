package com.arisriswanto.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class SignInAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);





        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SignInAct.this,RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Laoding ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty() && password.isEmpty()) {
                    // ubah state menjadi loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                    Toast.makeText(getApplicationContext(), "Username & Password kosong !", Toast.LENGTH_SHORT).show();

                } else if (username.isEmpty()){
                    // ubah state menjadi loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                    Toast.makeText(getApplicationContext(), "Username kosong !", Toast.LENGTH_SHORT).show();

                } else if (password.isEmpty()){
                    // ubah state menjadi loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                    Toast.makeText(getApplicationContext(), "Password kosong !", Toast.LENGTH_SHORT).show();

                } else {
                    reference = FirebaseDatabase.getInstance().getReference().child("User").child(username);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                // ambil data password dari firebase
                                String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                // validasi password dengan password pada firebase
                                if (password.equals(passwordFromFirebase)) {
                                    // simpan username kepada local
                                    // menyimpan data ke local storage (handphone)
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key, xusername.getText().toString());
                                    editor.apply();

                                    // pindah activity
                                    Intent gotohome = new Intent(SignInAct.this,HomeAct.class);
                                    startActivity(gotohome);
                                } else {
                                    // ubah state menjadi loading
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                    Toast.makeText(getApplicationContext(), "Password salah !", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                // ubah state menjadi loading
                                btn_sign_in.setEnabled(true);
                                btn_sign_in.setText("SIGN IN");

                                Toast.makeText(getApplicationContext(), "Username tidak ada !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Database Error !", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });
    }
}
