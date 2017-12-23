package com.example.administrator.flowlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitFlowActivity extends AppCompatActivity {
    private Button open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_flow);
        open = (Button) findViewById(R.id.go_to_flow);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(InitFlowActivity.this,MainActivity.class);
                startActivity(it);
            }
        });
    }
}
