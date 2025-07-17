package com.amisadman.aybaylite.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.amisadman.aybaylite.Controllers.AddIncomeHelper;
import com.amisadman.aybaylite.R;
import com.amisadman.aybaylite.Repo.DatabaseHelper;

public class AddIncome extends AppCompatActivity {
    TextView tvTitle;
    EditText edAmount, edReason;
    Button button;
    ImageButton btnBack;
    AddIncomeHelper helper;
    LottieAnimationView animationAdd, animationUpdate;
    String editId = null;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        edAmount = findViewById(R.id.edAmount);
        edReason = findViewById(R.id.edReason);
        button = findViewById(R.id.button);
        animationAdd = findViewById(R.id.animationAdd);
        animationUpdate = findViewById(R.id.animationUpdate);
        helper = new AddIncomeHelper(this);
        Intent intent = getIntent();
        /*Edit Data
        Edit button theke ekhane ashe.
        */
        if (intent.hasExtra("EDIT_ID"))
        {
            isEdit = true;
            editId = intent.getStringExtra("EDIT_ID");
            edAmount.setText(intent.getStringExtra("EDIT_AMOUNT"));
            edReason.setText(intent.getStringExtra("EDIT_REASON"));
            tvTitle.setText("Edit Income");

            animationUpdate.setVisibility(View.VISIBLE);
            animationAdd.setVisibility(View.GONE);
            button.setText("Update");
        }
        else
        {
            // Add Data
            tvTitle.setText("Add Income");

            animationUpdate.setVisibility(View.GONE);
            animationAdd.setVisibility(View.VISIBLE);
        }
        btnBack.setOnClickListener(v -> onBackPressed());
        button.setOnClickListener(v ->
        {
            String sAmount = edAmount.getText().toString();
            String reason = edReason.getText().toString();

            // Input validation
            if (sAmount.isEmpty() || reason.isEmpty())
            {
                Toast.makeText(AddIncome.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            double amount = Double.parseDouble(sAmount);

            if (isEdit)
            {
                //Update korbe
                helper.updateData(editId, amount, reason);
                finish();
                Toast.makeText(AddIncome.this, "Entry Updated!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Notun add
                helper.addData(amount, reason);
                Toast.makeText(AddIncome.this, "Income Added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
    // back press
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}