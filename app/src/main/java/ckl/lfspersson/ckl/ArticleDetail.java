package ckl.lfspersson.ckl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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
    ImageView ivImage;

    @Bean
    ArticleDAO articleDAO;

    @AfterViews
    void init() {
        idArticle = getIntent().getExtras().getInt("idArticle");
        ArticleModel articleModel = new ArticleModel();
        articleModel = articleDAO.getArticlebyId(idArticle);

        tvTitle.setText(articleModel.getTitle());
        tvAuthor.setText(articleModel.getAuthors());
        tvDate.setText(articleModel.getDate());
        tvContent.setText(articleModel.getContent());

        switch (idArticle)
        {
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
