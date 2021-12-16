package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import static android.content.Context.MODE_PRIVATE;

public class SignupFragment extends Fragment {

    EditText et_username;
    EditText et_email;
    EditText et_password;
    Button btn_signup;
    TextView tv_login;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = view.getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

        et_username = view.findViewById(R.id.et_username);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        btn_signup = view.findViewById(R.id.button_signup);
        tv_login = view.findViewById(R.id.tv_login);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_username.getText().length() == 0){
                    Toast.makeText(view.getContext(), "Username is required", Toast.LENGTH_SHORT).show();
                    return;
                }else if(et_email.getText().length() == 0){
                    Toast.makeText(view.getContext(), "Email address is required", Toast.LENGTH_SHORT).show();
                    return;
                }else if(et_password.getText().length() == 0){
                    Toast.makeText(view.getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }else if(et_password.getText().length() < 6){
                    Toast.makeText(view.getContext(), "Password must be 6 character long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                signUp(et_username.getText().toString(),et_email.getText().toString(),et_password.getText().toString());
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignupFragment.this)
                        .navigate(R.id.action_SignUpFragment_to_LoginFragment);
            }
        });

    }

    void signUp(String username,String email, String password){
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("username",username);
        myEdit.putString("email",email);
        myEdit.putString("password",password);
        myEdit.putBoolean("is_login",true);
        myEdit.apply();

        Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), ContactActivity.class);
        startActivity(intent);
    }
}