package ckl.lfspersson.ckl;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by LFSPersson on 10/11/16.
 */

@EBean
public class DatabaseHelper {

    @RootContext
    Context context;

    private static final String DATABASE_NAME = "cklRealm.db";
    private static RealmConfiguration configuration;

    @AfterInject
    public void afterInject() {
        configuration = getConfiguration(context);
    }

    public Realm getRealm() {
        return Realm.getInstance(getConfiguration(context));
    }

    public void clearDataBase() {
        Realm.deleteRealm(getConfiguration(context));
    }
    public void clearDataBase(Context context) {
        Realm.deleteRealm(getConfiguration(context));
    }

    private RealmConfiguration getConfiguration(Context context) {
        if (configuration == null) {
            configuration = new RealmConfiguration.Builder(context)
                    .name(DATABASE_NAME)
                    .schemaVersion(BuildConfig.VERSION_CODE)
                    .deleteRealmIfMigrationNeeded()
                    .build();
        }

        return configuration;
    }

}
