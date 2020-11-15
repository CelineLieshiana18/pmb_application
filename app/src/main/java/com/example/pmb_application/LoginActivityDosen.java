package com.example.pmb_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pmb_application.databinding.ActivityLoginDosenBinding;
import com.example.pmb_application.databinding.ActivityLoginMahasiswaPanitiaBinding;

public class LoginActivityDosen extends AppCompatActivity {
    private ActivityLoginDosenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginDosenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMasuk.setOnClickListener(view -> {
            if (binding.txtNik.getText().toString().isEmpty() || binding.txtPassword.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivityDosen.this,R.string.field_kosong_mhs_messsage, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(LoginActivityDosen.this, MainActivityDosenPanitia.class);
                startActivity(intent);
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


        binding.linkToLoginMhsPanitia.setOnClickListener(view -> {
            System.out.println("Clicked");
            Intent intent = new Intent(LoginActivityDosen.this, LoginActivityMahasiswaPanitia.class);
            startActivity(intent);

        });
    }
}