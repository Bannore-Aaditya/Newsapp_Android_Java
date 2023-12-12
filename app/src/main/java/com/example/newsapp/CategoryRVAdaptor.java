package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdaptor extends RecyclerView.Adapter<CategoryRVAdaptor.ViewHolder> {

    private ArrayList<CategoryRVmodal> categoryRVmodals ;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdaptor(ArrayList<CategoryRVmodal> categoryRVmodals, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVmodals = categoryRVmodals;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }



    @NonNull
    @Override
    public CategoryRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rv_items,parent,false);
        return new CategoryRVAdaptor.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdaptor.ViewHolder holder,int position) {
        CategoryRVmodal categoryRVmodal = categoryRVmodals.get(position);
        holder.categoryTV.setText(categoryRVmodal.getCategory());
        Picasso.get().load(categoryRVmodal.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });

    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    @Override
    public int getItemCount() {
        return categoryRVmodals.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTV ;
        private ImageView categoryIV ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTV = itemView.findViewById(R.id.idTVCategory);
            categoryIV = itemView.findViewById(R.id.idIVCategory);

        }
    }
}
