package com.memory.timeapp.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.memory.timeapp.BR;
import com.memory.timeapp.commonUtil.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.miao on 2018/5/9.
 */
public class BindingAdapter extends RecyclerView.Adapter<BindingAdapter.BindingHolder>{


    public List<BindingAdapterItem> getItems() {
        return items;
    }

    public void setItems(List<BindingAdapterItem> items) {
        this.items = items;
    }
    List<BindingAdapterItem> items = new ArrayList<>();

    /**
     * @return 返回的是adapter的view
     */
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new BindingHolder(binding);
    }
    /*
    * 数据绑定
    * */
    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
//        holder.bindData(items.get(position));
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;
        /**
         * @param binding   可以看作是这个hodler代表的布局的马甲，getRoot()方法会返回整个holder的最顶层的view
         * */
        public BindingHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

//        public void bindData(BindingAdapterItem item) {
//            binding.setVariable(BR.item,item);
//            binding.setVariable(BR.handler,new HandlerEvent(UIUtils.getContext()));
//        }
    }
}
