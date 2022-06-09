package com.example.libraryapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class BarrowBookAdapter extends RecyclerView.Adapter<BarrowBookAdapter.ViewHolder> {


    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<BarrowBookModel> mBarrowBookModels;
    private long adapterPosition = -1;
    private BarrowBookModel mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private final Activity mActivity;
    private OnDeleteListener listener;
    private boolean mCanDelete = true;
    private final OnBarrowBookModelClickListener mOnBarrowBookModelClickListener;

    public BarrowBookAdapter(Context context, Activity activity, OnBarrowBookModelClickListener onBarrowBookModelClickListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mActivity = activity;
        mOnBarrowBookModelClickListener = onBarrowBookModelClickListener;
    }

    public interface OnDeleteListener {
        void deleteBarrowBookModel(BarrowBookModel BarrowBookModel);
    }

    public void setOnBarrowBookModelDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_barrowbooklistview, parent, false);
        return new ViewHolder(itemView, mOnBarrowBookModelClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Veritabani veritabani=new Veritabani(getContext());
        UserDataBase userDataBase=new UserDataBase(getContext());

        BarrowBookModel model = mBarrowBookModels.get(position);
        holder.bookNameText.setText(veritabani.getBook(Long.parseLong(model.getBookID())).getName());
        holder.userNameText.setText(userDataBase.getUser(Long.parseLong(model.getUserID())).getName()+" "+userDataBase.getUser(Long.parseLong(model.getUserID())).getSurname());
        holder.barrowBookIDText.setText(String.valueOf(model.getID()));



    }

    @Override
    public int getItemCount() {
        if (mBarrowBookModels == null) return 0;
        return mBarrowBookModels.size();
    }

    public Context getContext() {
        return mContext;
    }


    public void setAdapterPosition(long adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public void setBarrowBookModels(List<BarrowBookModel> barrowBookModelList, long removedPosition) {
        this.mBarrowBookModels = barrowBookModelList;
        if (removedPosition > -1) {
            notifyItemRemoved((int) removedPosition);
        } else {
            notifyDataSetChanged();
        }
    }

    public long getAdapterPosition() {
        return adapterPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView userNameText;
        private final TextView bookNameText;
        private final TextView barrowBookIDText;
        private final CardView barrowBookCard;

        OnBarrowBookModelClickListener onBarrowBookModelClickListener;

        public ViewHolder(@NonNull View itemView, OnBarrowBookModelClickListener onBarrowBookModelClickListener) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.userNameCustomList);
            bookNameText = itemView.findViewById(R.id.bookNameCustomList);
            barrowBookIDText=itemView.findViewById(R.id.bbIDText);
            barrowBookCard=itemView.findViewById(R.id.barrowBookCard);
            this.onBarrowBookModelClickListener = onBarrowBookModelClickListener;
            barrowBookCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBarrowBookModelClickListener.onBarrowBookModelClick(Long.parseLong(String.valueOf(barrowBookIDText.getText())));
        }

    }

    public interface OnBarrowBookModelClickListener {
        void onBarrowBookModelClick(long BarrowBookModelId);
    }

}
