package com.itapp.pmb_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.itapp.pmb_application.databinding.ActivityLoginMhsPanitiaBinding;

public class LoginActivityMhsPanitia extends AppCompatActivity {
    private ActivityLoginMhsPanitiaBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mhs_panitia);
        binding = ActivityLoginMhsPanitiaBinding.inflate(getLayoutInflater());

        binding.btnMasukMhs.setOnClickListener(view -> {
            if (binding.txtNrp.getText().toString().isEmpty() || binding.txtPassword.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivityMhsPanitia.this,R.string.error_messsage, Toast.LENGTH_SHORT).show();
            }
            else {
//                String email = binding.etEmail.getText().toString();
//                String password = binding.etPassword.getText().toString();
//                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,
//                        task -> {
//                            if (task.isSuccessful()){
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
//                            }
//                            else {
//                                Toast.makeText(MainActivity.this, R.string.error_login,Toast.LENGTH_SHORT).show();
//                            }
//                        });

            }
        });

        binding.linkToLoginDosen.setOnClickListener(view -> {
            System.out.println("Clicked");
            Intent intent = new Intent(LoginActivityMhsPanitia.this, LoginActivityDosen.class);
            startActivity(intent);

        });

    }
}
