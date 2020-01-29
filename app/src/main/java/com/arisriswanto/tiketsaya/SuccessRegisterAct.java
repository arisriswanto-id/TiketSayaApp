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

public class SuccessRegisterAct extends AppCompatActivity {

    Animation app_splash, ttb, btt;
    Button btn_explore;
    ImageView icon_success_register;
    TextView app_title, app_subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        // load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        // load element
        icon_success_register = findViewById(R.id.icon_success_register);
        app_subtitle = findViewById(R.id.app_subtitle);
        app_title = findViewById(R.id.app_title);
        btn_explore = findViewById(R.id.btn_explore);

        // run animation
        icon_success_register.startAnimation(app_splash);
        btn_explore.startAnimation(btt);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);



        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegisterAct.this,HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}
