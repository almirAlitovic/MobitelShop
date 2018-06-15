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
import com.example.entreprenur.mobileshop.api.models.HistoryModel;
import com.example.entreprenur.mobileshop.utils.AnimationUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<HistoryModel> list;
    OnHistoryItemClick listener;
    int previousPosition = 0;

    public HistoryAdapter(List<HistoryModel> list, OnHistoryItemClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        HistoryModel historyModel = list.get(position);

        if (historyModel.SlikaUrl != null) {
            Picasso.get().load(historyModel.SlikaUrl).into(holder.articleImage, new Callback() {
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

        if (historyModel.Naziv != null) {
            holder.articleName.setText(historyModel.Naziv);
        }

        holder.historyModel = historyModel;

        Integer price = historyModel.Cijena;
        Integer amount = historyModel.Kolicina;
        Long orderId = historyModel.BrojNarudzbe;

        if ((price != null) && (amount != null) && (orderId != null)) {

            int totalAmount = price * amount;

            String totalString = String.format("Broj narudzbe :%d ukupno: \n%dx%d = %d KM", orderId, price, amount, totalAmount);

            holder.orderDetails.setText(totalString);

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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        HistoryModel historyModel;
        ImageView articleImage;
        TextView articleName, orderDetails;
        ProgressBar progressBar;

        public ViewHolder(View itemView, final OnHistoryItemClick listener) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.image_id);
            articleName = itemView.findViewById(R.id.title_id);
            progressBar = itemView.findViewById(R.id.progress_bar);
            orderDetails = itemView.findViewById(R.id.details_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onHistoryItemClick(historyModel);
                    }
                }
            });
        }
    }

    public interface OnHistoryItemClick {
        void onHistoryItemClick(HistoryModel historyModel);
    }
}
