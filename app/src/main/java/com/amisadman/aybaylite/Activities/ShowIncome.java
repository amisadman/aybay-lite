package com.amisadman.aybaylite.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.amisadman.aybaylite.Controllers.ShowIncomeHelper;
import com.amisadman.aybaylite.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowIncome extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tvTitle;
    TextView tvNoDataMessage, tvBalance, tvTotal_show;
    Button btnAddOther;
    ImageButton btnBack;
    LottieAnimationView noDataAnimation;
    ShowIncomeHelper showIncomeHelper;
    ArrayList<HashMap<String, String>> arrayList;
    HashMap<String, String> hashMap;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        recyclerView = findViewById(R.id.recyclerView);
        tvTitle = findViewById(R.id.tvTitle);
        noDataAnimation = findViewById(R.id.noDataAnimation);
        tvNoDataMessage = findViewById(R.id.tvNoDataMessage);
        btnBack = findViewById(R.id.btnBack);
        btnAddOther = findViewById(R.id.btnAddOther);
        tvBalance = findViewById(R.id.tvBalance);
        tvTotal_show = findViewById(R.id.tvTotal_show);

        showIncomeHelper = new ShowIncomeHelper(this);
        tvTitle.setText("Income Statement");
        btnBack.setOnClickListener(v -> onBackPressed());

        arrayList = showIncomeHelper.loadIncome();

        if(!arrayList.isEmpty()){

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyAdapter();
            recyclerView.setAdapter(adapter);

            recyclerView.setVisibility(View.VISIBLE);
            noDataAnimation.setVisibility(View.GONE);
            tvNoDataMessage.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noDataAnimation.setVisibility(View.VISIBLE);
            tvNoDataMessage.setVisibility(View.VISIBLE);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_income, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            hashMap = arrayList.get(position);

            String id = hashMap.get("id");
            String reason = hashMap.get("reason");
            String formattedTime = hashMap.get("time");

            double amountValue = 0;
            try {
                amountValue = Double.parseDouble(hashMap.get("amount"));
            } catch (NumberFormatException e) {
                amountValue = 0;
            }

            String formattedAmount = String.format("%.2f", amountValue);

            holder.tvReason.setText(reason);
            holder.tvAmount.setText("৳ " + formattedAmount);
            holder.tvTime.setText(formattedTime);

            holder.btnDeleteItem.setOnClickListener(v -> {
                showIncomeHelper.deleteData(id);
                arrayList = showIncomeHelper.loadIncome();
                notifyDataSetChanged();
            });

            holder.btnEditItem.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), AddIncome.class);
                intent.putExtra("EDIT_ID", id);
                intent.putExtra("EDIT_AMOUNT", String.valueOf(formattedAmount));
                intent.putExtra("EDIT_REASON", reason);
                v.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvReason, tvAmount, tvTime;
            Button btnDeleteItem, btnEditItem;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvReason = itemView.findViewById(R.id.tvReason);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                tvTime = itemView.findViewById(R.id.tvTime);
                btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
                btnEditItem = itemView.findViewById(R.id.btnEditItem);
            }
        }
    }
}