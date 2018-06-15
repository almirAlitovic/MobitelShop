package com.example.entreprenur.mobileshop.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.api.models.MobileModel;
import com.example.entreprenur.mobileshop.utils.AnimationUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainShopAdapter extends RecyclerView.Adapter<MainShopAdapter.ViewHolder>{

    List<MobileModel> mobileModelList;
    List<Integer> amountList;
    OnItemClickListener listener;
    OnItemBasketClickListener basketClickListener;
    int previousPosition = 0;

    public MainShopAdapter(List<MobileModel> mobileModels, List<Integer> amountList, OnItemClickListener listener, OnItemBasketClickListener onItemBasketClickListener) {
        this.mobileModelList = mobileModels;
        this.listener = listener;
        this.basketClickListener = onItemBasketClickListener;
        this.amountList = amountList;
    }

    public void deleteAll() {
        mobileModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        return new ViewHolder(view, listener, basketClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MobileModel mobileModel = mobileModelList.get(position);

        if (mobileModel.SlikaUrl != null) {
            Picasso.get().load(mobileModel.SlikaUrl).into(holder.articleImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    holder.progressBar.setVisibility(View.GONE);
                }
            });
        }

        if (mobileModel.Naziv != null) {
            holder.articleName.setText(mobileModel.Naziv);
        }

        holder.mobileModel = mobileModel;

        if (holder.detailsText != null) {
            holder.detailsText.setVisibility(View.VISIBLE);
            holder.detailsText.setText(String.valueOf(amountList.get(position)) + "*" + String.format("%s KM", String.valueOf(mobileModel.Cijena)));
        }

        //scrolling down
        if (holder.getAdapterPosition() >= previousPosition) {
            AnimationUtil.animateRecyclerViewScrolling(holder, true);
        } else { //scrolling up
            AnimationUtil.animateRecyclerViewScrolling(holder, false);
        }
        previousPosition = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return mobileModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MobileModel mobileModel;
        ImageView articleImage;
        TextView articleName, detailsText = null;
        ProgressBar progressBar;

        public ViewHolder(final View itemView, final OnItemClickListener listener, final OnItemBasketClickListener onItemBasketClickListener) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.image_id);
            articleName = itemView.findViewById(R.id.title_id);
            progressBar = itemView.findViewById(R.id.progress_bar);

            if (basketClickListener != null) {
                detailsText = itemView.findViewById(R.id.details_id);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClickListener(mobileModel);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (basketClickListener != null) {
                        basketClickListener.onItemLongClick(getAdapterPosition());
                    }
                    return false;
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClickListener(MobileModel mobileModel);
    }

    public interface OnItemBasketClickListener {
        void onItemLongClick(int position);
    }
}
