package com.example.imc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère toutes les vues dont on a besoin

        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText) findViewById(R.id.taille);
        poids = (EditText) findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);


        // On attribue un listener adapté aux vues qui en ont besoin

        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);










    };

    private View.OnClickListener envoyerListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            // on récupère la taille
            String t = taille.getText().toString();
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);
            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, R.string.height_error_notPositive,
                        Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if(pValue <= 0)
                    Toast.makeText(MainActivity.this, R.string.weight_error_notPositive, Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat=getString(R.string.your_bmi) + imc +". ";
                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);
                    result.setText(resultat);
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String interpreteIMC(float imc)
    {
        String cmt;

        if(imc > 40)
        {
            cmt = getString(R.string.obesite_morbide);
        }
        else if(imc > 35)
        {
            cmt = getString(R.string.obesite_severe);
        }
        else if(imc > 30)
        {
            cmt = getString(R.string.obesite_moderee);
        }
        else if(imc > 25)
        {
            cmt = getString(R.string.surpoids);
        }
        else if(imc > 18.5)
        {
            cmt = getString(R.string.normal);
        }
        else if(imc > 16.5)
        {
            cmt = getString(R.string.maigreur);
        }
        else cmt = getString(R.string.famine);

        return cmt;

    }


    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };

    private View.OnClickListener checkedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };








}