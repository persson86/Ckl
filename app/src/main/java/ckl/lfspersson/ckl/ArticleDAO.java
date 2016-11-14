package ckl.lfspersson.ckl;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by LFSPersson on 10/11/16.
 */

@EBean(scope = EBean.Scope.Singleton)
public class ArticleDAO {
    @RootContext
    Context context;

    @Bean
    DatabaseHelper dbHelper;
    public void saveArticles(List<ArticleModel> model) {
        Realm realm = dbHelper.getRealm();
        realm.beginTransaction();
        realm.where(ArticleModel.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.beginTransaction();
        realm.copyToRealm(model);
        realm.commitTransaction();
    }

    public void setReadStatus(int id, boolean articleReaded) {
        Realm realm = dbHelper.getRealm();
        realm.beginTransaction();
        realm.where(ArticleModel.class).equalTo("id", id).findFirst().setReadStatus(articleReaded);
        realm.commitTransaction();
        realm.close();
    }

    public List<ArticleModel> getArticles() {
        return dbHelper.getRealm().where(ArticleModel.class).findAll();
    }

    public ArticleModel getArticlebyId(long id) {
        return dbHelper.getRealm().where(ArticleModel.class).equalTo("id", id).findFirst();
    }

    public List<ArticleModel> getArticlesOrderByFilter(String filter){
        return dbHelper.getRealm().where(ArticleModel.class).findAllSorted(filter);
    }

}