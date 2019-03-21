package com.example.zwierzaki;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "imie";
    private static final String KEY_DESCRIPTION = "nazwisko";
    private static final String KEY_LOGIN = "login";

    private EditText editTextImie;
    private EditText editTextNazwisko;
    private EditText editTextLogin;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextImie = findViewById(R.id.edit_text_imie);
        editTextNazwisko = findViewById(R.id.edit_text_nazwisko);
        editTextLogin = findViewById(R.id.edit_text_login);


    }

    public void saveNote(View v) {
        String imie = editTextImie.getText().toString();
        String nazwisko = editTextNazwisko.getText().toString();
        final String login = editTextLogin.getText().toString();

        final Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, imie);
        note.put(KEY_DESCRIPTION, nazwisko);
        note.put(KEY_LOGIN,login);




        DocumentReference docIdRef = db.collection("Zwierzaki").document(login);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        Toast.makeText(MainActivity.this, "uzytkownik istnieje", Toast.LENGTH_SHORT).show();
                    } else {
                       // Log.d(TAG, "Document does not exist!");
                       // Toast.makeText(MainActivity.this, "uzytkownik nie istnieje", Toast.LENGTH_SHORT).show();
                        db.collection("Zwierzaki").document(login).set(note)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Toast.makeText(MainActivity.this, "Nieprawidłowy login!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, e.toString());
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }


}