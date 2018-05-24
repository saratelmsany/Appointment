package com.example.obada.appointment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView nameTxtView,phoneTxtView,mailTxtView,codeTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       // Toolbar toolbar =findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        nameTxtView=findViewById(R.id.name_text_view);
        phoneTxtView=findViewById(R.id.phone_text_view);
        mailTxtView=findViewById(R.id.mail_text_view);
        codeTxtView=findViewById(R.id.code_text_view);

        final String Id=getIntent().getStringExtra("Id");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("resrvations").child(Id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String mail = dataSnapshot.child("email").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String code = dataSnapshot.child("code").getValue().toString();

                nameTxtView.setText(name);
                mailTxtView.setText(mail);
                phoneTxtView.setText(phone);
                codeTxtView.setText(code);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
