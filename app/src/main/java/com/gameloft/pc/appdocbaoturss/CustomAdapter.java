package com.gameloft.pc.appdocbaoturss;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by PC on 11/2/2017.
 */

public class CustomAdapter extends ArrayAdapter <DocBao> {
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DocBao> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dong_layout_listview, null);
        }
        DocBao p = getItem(position);
        if (p != null){
            TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtTitle.setText(p.title);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            Picasso.with(getContext()).load(p.image).into(imageView);
        }

        return view;
    }
}
