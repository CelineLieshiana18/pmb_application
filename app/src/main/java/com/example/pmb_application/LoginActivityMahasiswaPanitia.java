package com.example.pmb_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pmb_application.databinding.ActivityLoginMahasiswaPanitiaBinding;

public class LoginActivityMahasiswaPanitia extends AppCompatActivity {
    private ActivityLoginMahasiswaPanitiaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginMahasiswaPanitiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMasuk.setOnClickListener(view -> {
            if (binding.txtNrp.getText().toString().isEmpty() || binding.txtPassword.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivityMahasiswaPanitia.this,R.string.field_kosong_mhs_messsage, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(LoginActivityMahasiswaPanitia.this, MainActivityMahasiswa.class);
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

        binding.linkToLoginDosen.setOnClickListener(view -> {
            System.out.println("Clicked");
            Intent intent = new Intent(LoginActivityMahasiswaPanitia.this, LoginActivityDosen.class);
            startActivity(intent);

        });
    }
}