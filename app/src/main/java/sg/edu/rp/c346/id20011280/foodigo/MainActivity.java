package sg.edu.rp.c346.id20011280.foodigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button diary, link1 , link2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diary = findViewById(R.id.diary);
        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);

        String website1 = "https://twomenbagels.com";
        String website2 = "https://lickers.com.sg";


        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DiaryActivity.class);
                startActivity(i);
            }
        });
        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website1));
                startActivity(i);
            }
        });
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(website2));
                startActivity(i);
            }
        });





    }
}