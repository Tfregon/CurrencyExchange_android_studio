package com.example.currencyexchange.view;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyexchange.R;
import com.example.currencyexchange.controller.HistoryController;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryController historyController;

    private ArrayList<String> historyList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyController = new HistoryController(this);

        Cursor cursor = historyController.db.getAllConversions();
        ArrayList<String> historyList = new ArrayList<>();
        while(cursor.moveToNext()){
            String entry = cursor.getString(1) + " to " + cursor.getString(2)
                    + ": " + cursor.getDouble(3) + " → " + cursor.getDouble(4)
                    + "\n(" + cursor.getString(5) + ")";
            historyList.add(entry);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,historyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter(){
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
                TextView tv = new TextView(parent.getContext());
                tv.setPadding(10,10,10,10);
                return new RecyclerView.ViewHolder(tv){};
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
                ((TextView)holder.itemView).setText(historyList.get(position));
            }
            @Override
            public int getItemCount(){
                return historyList.size();
            }
        });
    }
}
