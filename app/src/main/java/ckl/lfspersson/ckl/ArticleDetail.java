package ckl.lfspersson.ckl;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmList;

@EActivity(R.layout.activity_article_detail)
public class ArticleDetail extends AppCompatActivity {

    public int idArticle;

    @ViewById
    TextView tvTitle;
    @ViewById
    TextView tvAuthor;
    @ViewById
    TextView tvDate;
    @ViewById
    TextView tvContent;
    @ViewById
    TextView tvLabel;
    @ViewById
    TextView tvWebsite;
    @ViewById
    ImageView ivImage;

    @Bean
    ArticleDAO articleDAO;

    public static Boolean returnFromDetail = false;

    @AfterViews
    void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        returnFromDetail = true;

        idArticle = getIntent().getExtras().getInt("idArticle");
        ArticleModel articleModel = articleDAO.getArticlebyId(idArticle);
        RealmList<ArticleTagModel> articleTagModelRealmList = articleModel.getTag();

        tvTitle.setText(articleModel.getTitle());
        tvAuthor.setText(articleModel.getAuthors());
        tvDate.setText(articleModel.getDate());
        tvContent.setText(articleModel.getContent());
        tvWebsite.setText(articleModel.getWebsite());
        tvLabel.setText(articleTagModelRealmList.get(0).getLabel());

        switch (idArticle) {
            case 1:
                ivImage.setImageResource(R.drawable.a1);
                break;
            case 2:
                ivImage.setImageResource(R.drawable.a2);
                break;
            case 3:
                ivImage.setImageResource(R.drawable.a3);
                break;
            case 4:
                ivImage.setImageResource(R.drawable.a4);
                break;
            case 5:
                ivImage.setImageResource(R.drawable.a1);
                break;
            case 6:
                ivImage.setImageResource(R.drawable.a2);
                break;
        }
    }
}
