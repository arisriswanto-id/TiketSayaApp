package com.arisriswanto.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBuyTicketAct extends AppCompatActivity {

    Animation app_splash, ttb, btt;

    ImageView icon_success_ticket;
    TextView app_title, app_subtitle;
    Button btn_view_ticket, btn_mydahsboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        // load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        // load element
        icon_success_ticket = findViewById(R.id.icon_success_ticket);
        app_subtitle = findViewById(R.id.app_subtitle);
        app_title = findViewById(R.id.app_title);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        btn_mydahsboard = findViewById(R.id.btn_mydahsboard);

        // run animation
        icon_success_ticket.startAnimation(app_splash);

        app_subtitle.startAnimation(ttb);
        app_title.startAnimation(ttb);

        btn_view_ticket.startAnimation(btt);
        btn_mydahsboard.startAnimation(btt);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(SuccessBuyTicketAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_mydahsboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessBuyTicketAct.this,HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}
