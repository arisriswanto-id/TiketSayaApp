package com.arisriswanto.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_pay_ticket_now, btnplus, btnmines;
    TextView textjumlahtiket, textmybalance, texttotalharga, lokasi, nama_wisata, ketentuan;
    ImageView notice_uang;
    Integer valuelumlahtiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisabalance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    // generate int secara random, membuat transaksi secara unix
    Integer no_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        // ambil string dari intent HomeAct
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_pay_ticket_now = findViewById(R.id.btn_pay_ticket_now);
        btnplus = findViewById(R.id.btnplus);
        btnmines = findViewById(R.id.btnmines);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);

        // setting value baru untuk beberapa komponen
        textjumlahtiket.setText(valuelumlahtiket.toString());


        // secara default, hide btn mines
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        // mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("User").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ " + mybalance +"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // menimpa data dengan data baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("ketentuan").getValue().toString();
                time_wisata = dataSnapshot.child("ketentuan").getValue().toString();

                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());


                valuetotalharga = valuehargatiket * valuelumlahtiket;
                texttotalharga.setText("US$ " + valuetotalharga +"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuelumlahtiket+=1;
                textjumlahtiket.setText(valuelumlahtiket.toString());

                if (valuelumlahtiket > 1) {
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }

                valuetotalharga = valuehargatiket * valuelumlahtiket;
                texttotalharga.setText("US$ " + valuetotalharga +"");

                if (valuetotalharga > mybalance) {
                    btn_pay_ticket_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay_ticket_now.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }

            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuelumlahtiket-=1;
                textjumlahtiket.setText(valuelumlahtiket.toString());

                if (valuelumlahtiket < 2) {
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }

                valuetotalharga = valuehargatiket * valuelumlahtiket;
                texttotalharga.setText("US$ " + valuetotalharga +"");

                if (valuetotalharga < mybalance) {
                    btn_pay_ticket_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_ticket_now.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });


        btn_pay_ticket_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // menyimpan data user ke dalam firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new)
                        .child(nama_wisata.getText().toString() + no_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + no_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_ticket").setValue(valuelumlahtiket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        // setelah data berhasil masuk, pindah act
                        Intent gotosuccessbuyticket = new Intent(TicketCheckoutAct.this,SuccessBuyTicketAct.class);
                        startActivity(gotosuccessbuyticket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // update data balance pada user (yang saat ini login)
                // mengambil data user dari firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("User").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisabalance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisabalance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void  getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
