package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdaptor.CategoryClickInterface {

    //55fbdd39eec640a298bfecbecd322287
    private RecyclerView newsRV,categoryRV ;
    private ProgressBar loadingPB ;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVmodal> categoryRVmodalArrayList;
    private CategoryRVAdaptor categoryRVAdaptor;
    private NewsRVAdaptor newsRVAdaptor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategory);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>() ;
        categoryRVmodalArrayList = new ArrayList<>() ;
        newsRVAdaptor = new NewsRVAdaptor(articlesArrayList,this);
        categoryRVAdaptor = new CategoryRVAdaptor(categoryRVmodalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdaptor);
        categoryRV.setAdapter(categoryRVAdaptor);
        getCategories();
        getNews("Space");
        newsRVAdaptor.notifyDataSetChanged();
    }

    private void getCategories(){
        categoryRVmodalArrayList.add(new CategoryRVmodal("All","https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fHNwYWNlfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Technology","https://images.unsplash.com/photo-1614728611996-806169502cf1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8bmFzYSUyMG9mZmljZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("science","https://images.unsplash.com/photo-1541185933-ef5d8ed016c2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c3BhY2V4fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Business","https://media.istockphoto.com/id/918887006/photo/deep-space-star-field-with-black-hole.jpg?b=1&s=170667a&w=0&k=20&c=iR0NNIb_H_-1iSCKwC_yNyuAWjUcpKmyekPEcqoEiyU="));
        categoryRVmodalArrayList.add(new CategoryRVmodal("India","https://images.unsplash.com/photo-1532375810709-75b1da00537c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8aW5kaWF8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVAdaptor.notifyDataSetChanged();
   }

   private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?q="+category+"&apiKey=55fbdd39eec640a298bfecbecd322287";
        String BASE_URL = "https://newsapi.org/";
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
       Call<NewsModal> call ;

       call = retrofitAPI.getNewsByCategory(categoryURL);

       call.enqueue(new Callback<NewsModal>() {
           @Override
           public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {

               NewsModal newsModal = response.body();
               loadingPB.setVisibility(View.GONE);
               ArrayList<Articles> articles = newsModal.getArticles();
               for(int i=0;i<articles.size();i++){
                   articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),
                           articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
               }
                newsRVAdaptor.notifyDataSetChanged();
           }

           @Override
           public void onFailure(Call<NewsModal> call, Throwable t) {
               Toast.makeText(MainActivity.this,"Failed to get News",Toast.LENGTH_SHORT).show();
           }
       });


   }

    @Override
    public void onCategoryClick(int position){
        String category = categoryRVmodalArrayList.get(position).getCategory();
        getNews(category);
    }

}
