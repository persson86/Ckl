package ckl.lfspersson.ckl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public List<ArticleModel> articleModel;
    private ProgressDialog progressDialog;

    @ViewById
    ListView listView;

    @Bean
    ArticleDAO articleDAO;

    @AfterViews
    void init() {
        startDialog();
        callRestService();
    }

    @Background
    public void callRestService() {
        RestService service = RestService.retrofit.create(RestService.class);
        final Call<List<ArticleModel>> call = service.getArticles();

        call.enqueue(new Callback<List<ArticleModel>>() {
            @Override
            public void onResponse(Response<List<ArticleModel>> response, Retrofit retrofit) {
                articleModel = new ArrayList<>();
                articleModel = response.body();

                int count = 0;
                List<ArticleModel> modelArticleList = new ArrayList<ArticleModel>();
                for (int i = 0; i < articleModel.size(); i++) {
                    ArticleModel model = new ArticleModel();
                    count++;
                    model.setId(count);
                    model.setTitle(articleModel.get(i).getTitle());
                    model.setAuthors(articleModel.get(i).getAuthors());
                    model.setContent(articleModel.get(i).getContent());
                    model.setDate(articleModel.get(i).getDate());
                    model.setWebsite(articleModel.get(i).getWebsite());
                    modelArticleList.add(model);
                }

                if (modelArticleList.size() != articleDAO.getArticles().size())
                    articleDAO.saveArticles(modelArticleList);

                listManager();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(getString(R.string.log_error), t.toString());
            }
        });
    }

    private void startDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.msg_getting_articles));
        progressDialog.setTitle(getResources().getString(R.string.msg_wait));
        progressDialog.show();
    }

    private void listManager() {
        int[] listviewImage = new int[]{
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4
        };

        final List<HashMap<String, String>> contentList = new ArrayList<>();

        int index = 0;
        for (ArticleModel model : articleModel) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("title", model.getTitle());
            hm.put("date", model.getDate());
            hm.put("author", model.getAuthors());
            hm.put("image", Integer.toString(listviewImage[index]));
            contentList.add(hm);
            index++;
        }

        String[] from = {"image", "title", "date", "author"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_date, R.id.listview_item_author};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), contentList, R.layout.listview_activity, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idArticle = position + 1;

                Intent it;
                it = new Intent(getApplicationContext(), ArticleDetail_.class);
                Bundle b = new Bundle();
                b.putInt("idArticle", idArticle);
                it.putExtras(b);
                startActivity(it);
            }
        });
    }

}
