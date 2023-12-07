package com.example.finalproj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.searchEditText);
        ListView articlesListView = findViewById(R.id.articlesListView);

        adapter = new ArticleAdapter(this, new ArrayList<>());
        articlesListView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String date = searchEditText.getText().toString();
            new FetchArticlesTask().execute(date);
        });
    }
    private class FetchArticlesTask extends AsyncTask<String, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(String... strings) {
            String date = strings[0];
            String urlString = "https://api.nasa.gov/planetary/apod?api_key=ewaZ6BEvZQnHmwQm1GsHeJ6BmzrSN0daBnk90dKf&date=" + date;
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return parseJson(stringBuilder.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private List<Article> parseJson(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                List<Article> articles = new ArrayList<>();
                Article article = new Article(
                        jsonObject.getString("title"),
                        jsonObject.getString("url"),
                        jsonObject.getString("hdurl"),
                        jsonObject.getString("explanation"),
                        jsonObject.getString("date"),
                        jsonObject.getString("sectionName")
                );
                articles.add(article);
                return articles;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // Inside onPostExecute
        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);
            if (articles != null && !articles.isEmpty()) {
                adapter.clear();
                adapter.addAll(articles);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("FetchArticlesTask", "Failed to fetch or parse data");
            }
        }

    }
}
