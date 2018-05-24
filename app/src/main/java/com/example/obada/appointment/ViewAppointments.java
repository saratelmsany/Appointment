package com.example.obada.appointment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewAppointments extends Fragment {

    private RecyclerView mRrcyclerview;

    private DatabaseReference musersDatabase;

    public ViewAppointments() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewAppointments newInstance(String param1, String param2) {
        ViewAppointments fragment = new ViewAppointments();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_view_appointments, container, false);

        musersDatabase= FirebaseDatabase.getInstance().getReference().child("resrvations");

        mRrcyclerview=rootView.findViewById(R.id.recycler_view);
        mRrcyclerview.setHasFixedSize(true);
        mRrcyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<UserModel,UserViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<UserModel, UserViewholder>(

                UserModel.class,
                R.layout.raw,
                UserViewholder.class,
                musersDatabase

        ) {
            @Override
            protected void populateViewHolder(UserViewholder viewHolder, UserModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                //viewHolder.setImage(model.getImage(),getApplicationContext());

               final String Id=getRef(position).getKey();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent userProfileIntent=new Intent(getActivity(),DetailActivity.class);
                        userProfileIntent.putExtra("Id",Id);
                        startActivity(userProfileIntent);
                    }
                });
            }
        };
                mRrcyclerview.setAdapter(firebaseRecyclerAdapter);
            }

      public static class UserViewholder extends RecyclerView.ViewHolder{
         View mview;
         public UserViewholder(View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setName(String name){
            TextView userNameView=mview.findViewById(R.id.name_text_view);
            userNameView.setText(name);
        }

        public void setEmail(String mail){
            TextView userMailView=mview.findViewById(R.id.mail_text_view);
            userMailView.setText(mail);
        }

    }
}