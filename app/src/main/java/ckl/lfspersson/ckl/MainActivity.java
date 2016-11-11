package ckl.lfspersson.ckl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity{

    public List<ArticleModel> articleModel;
    private ProgressDialog progressDialog;
    private ArticleViewAdapter articleViewAdapter;

    @Bean
    ArticleDAO articleDAO;
    @Bean
    DatabaseHelper dbHelper;
    @ViewById
    GridView gvArticles;

    @AfterViews
    void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startDialog();
        onClickManager();

        if (isConnectedToInternet() == true)
            callRestService();
        else if (articleDAO.getArticles().size() > 0)
            listManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ArticleDetail.returnFromDetail == true) {
            listManager();
            ArticleDetail.returnFromDetail = false;
        }
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
                    model.setTag(articleModel.get(i).getTag());
                    model.setReadStatus(false);
                    modelArticleList.add(model);
                }

                if (modelArticleList.size() != articleDAO.getArticles().size()) {
                    articleDAO.saveArticles(modelArticleList);
                }
                listManager();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(getString(R.string.log_error), t.toString());
                if (articleDAO.getArticles().size() > 0)
                    listManager();
                else {
                    Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void onClickManager(){
        gvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idArticle = position + 1;
                articleDAO.setReadStatus(idArticle, true);

                Intent it;
                it = new Intent(getApplicationContext(), ArticleDetail_.class);
                Bundle b = new Bundle();
                b.putInt("idArticle", idArticle);
                it.putExtras(b);
                startActivity(it);
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
        List<ArticleModel> articleModelList = articleDAO.getArticles();
        articleViewAdapter = new ArticleViewAdapter(this, articleModelList);
        articleViewAdapter.notifyDataSetChanged();
        gvArticles.setAdapter(articleViewAdapter);
        progressDialog.dismiss();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Toast.makeText(MainActivity.this, R.string.msg_no_internet, Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }
}
