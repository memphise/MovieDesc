package com.example.memphise.rxjava_retrofit_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by memphise on 2016/8/2.
 */
public class MovieAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<MovieBean.SubjectsBean> list;

    public MovieAdapter(Context context, List<MovieBean.SubjectsBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder vh = null;
        if(null == convertView){
            convertView = inflater.inflate(R.layout.itemview, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        MovieBean.SubjectsBean subjectsBean = list.get(position);
        vh.tv.setText(subjectsBean.toString());
        //加载图片
        Glide.with(context).load(subjectsBean.getImages().getSmall()).placeholder(R.mipmap.ic_launcher).into(vh.iv);
        return convertView;
    }

    class ViewHolder{
        private ImageView iv;
        private TextView tv;

        public ViewHolder(View itemView){
            this.iv = (ImageView) itemView.findViewById(R.id.iv);
            this.tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
