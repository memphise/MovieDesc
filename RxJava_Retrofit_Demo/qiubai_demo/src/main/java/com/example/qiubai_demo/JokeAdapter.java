package com.example.qiubai_demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by memphise on 2016/8/2.
 */
public class JokeAdapter extends RecyclerView.Adapter{
    private List<JokeBean.ItemsBean> list;
    private LayoutInflater inflater;
    private Context context;
    private JokeAdapterListener jokeAdapterListener;

    public void setJokeAdapterListener(JokeAdapterListener jokeAdapterListener){
        this.jokeAdapterListener = jokeAdapterListener;
    }

    public JokeAdapter(Context context, List<JokeBean.ItemsBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.my_item_view, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        JokeBean.ItemsBean itemsBean = list.get(position);
        myViewHolder.user_name.setText(itemsBean.getUser().getLogin() + "");
        myViewHolder.content.setText("" + itemsBean.getContent());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView user_name;
        private ImageView user_face;
        private TextView content;

        public MyViewHolder(final View itemView){
            super(itemView);
            user_face = (ImageView) itemView.findViewById(R.id.user_face);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content);
            //添加头像点击监听
            user_face.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(null != jokeAdapterListener){
                        jokeAdapterListener.onJokeItemClicked(getLayoutPosition());
                    }
                }
            });
        }
    }

    //设置头像的点击事件
    interface JokeAdapterListener{
        void onJokeItemClicked(int position);
    }
}
