package com.zxw.reviewapp.ctrip;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxw.reviewapp.R;

import java.util.List;

/**
 * Created by xiangwuzhu on 2018/11/25.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<MyTestHolder> {

    private Context context;
    private List<MyModel> data;

    public RecyclerviewAdapter(Context context, List<MyModel> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_item, parent, false);
        return new MyTestHolder(view, parent.getContext());
    }


    @Override
    public void onBindViewHolder(@NonNull MyTestHolder holder, final int position) {
        holder.setData(data.get(position), position);
    }


    @Override
    public void onViewRecycled(MyTestHolder holder) {
        holder.onViewRecycled();
        super.onViewRecycled(holder);

    }

    @Override
    public void onViewAttachedToWindow(MyTestHolder holder) {
        holder.onViewAttachedToWindow();
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(MyTestHolder holder) {
        holder.onViewDetachedFromWindow();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.e("zxw", "--      onAttachedToRecyclerView --");
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        Log.e("zxw", "--      onDetachedFromRecyclerView --");
        super.onDetachedFromRecyclerView(recyclerView);
    }

}

