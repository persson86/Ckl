package ckl.lfspersson.ckl;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LFSPersson on 11/11/16.
 */

public class ArticleViewAdapter extends BaseAdapter {
    private final Context mContext;
    List<ArticleModel> articles;

    public ArticleViewAdapter(Context context, List<ArticleModel> articles) {
        this.mContext = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.custom_item, null);
        }

        int color = Color.parseColor("#ffffff");
        convertView.setBackgroundColor(color);

        final ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        final TextView tvAuhtor = (TextView) convertView.findViewById(R.id.tvAuthor);
        final TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        final TextView tvRead = (TextView) convertView.findViewById(R.id.tvReadStatus);

        tvTitle.setText(articles.get(position).getTitle());
        tvAuhtor.setText(articles.get(position).getAuthors());
        tvDate.setText(articles.get(position).getDate());
        if (articles.get(position).getReadStatus() == true)
            tvRead.setText(R.string.txt_read_true);
        else
            tvRead.setText(null);

        switch (position) {
            case 0:
                ivImage.setImageResource(R.drawable.a1);
                break;
            case 1:
                ivImage.setImageResource(R.drawable.a2);
                break;
            case 2:
                ivImage.setImageResource(R.drawable.a3);
                break;
            case 3:
                ivImage.setImageResource(R.drawable.a4);
                break;
            case 4:
                ivImage.setImageResource(R.drawable.a5);
                break;
            case 5:
                ivImage.setImageResource(R.drawable.a6);
                break;
        }

        return convertView;
    }

}
