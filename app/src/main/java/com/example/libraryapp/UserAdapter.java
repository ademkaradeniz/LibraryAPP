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


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<UserModel> mUserModels;
    private long adapterPosition = -1;
    private UserModel mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private final Activity mActivity;
    private OnDeleteListener listener;
    private boolean mCanDelete = true;
    private final OnUserModelClickListener mOnUserModelClickListener;

    public UserAdapter(Context context, Activity activity, OnUserModelClickListener onUserModelClickListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mActivity = activity;
        mOnUserModelClickListener = onUserModelClickListener;
    }

    public interface OnDeleteListener {
        void deleteUserModel(UserModel UserModel);
    }

    public void setOnUserModelDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_userlistview, parent, false);
        return new ViewHolder(itemView, mOnUserModelClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        UserModel model = mUserModels.get(position);


        holder.nameSurnameText.setText(model.getName() +" " +model.getSurname());
        holder.telephoneText.setText(model.getTelephone());
        holder.emailText.setText(model.getEmail());
        holder.userIDText.setText(String.valueOf(model.getID()));



    }

    @Override
    public int getItemCount() {
        if (mUserModels == null) return 0;
        return mUserModels.size();
    }

    public Context getContext() {
        return mContext;
    }


    public void setAdapterPosition(long adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public void setUserModels(List<UserModel> userModelList, long removedPosition) {
        this.mUserModels = userModelList;
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
        private final TextView nameSurnameText;
        private final TextView telephoneText;
        private final TextView emailText;
        private final TextView userIDText;
        private final CardView userCard;

        OnUserModelClickListener onUserModelClickListener;

        public ViewHolder(@NonNull View itemView, OnUserModelClickListener onUserModelClickListener) {
            super(itemView);
            nameSurnameText = itemView.findViewById(R.id.nameSurnameCustomList);
            telephoneText = itemView.findViewById(R.id.telephoneCustomList);
            emailText = itemView.findViewById(R.id.emailCustomList);
            userIDText=itemView.findViewById(R.id.bbUserIDText);
            userCard=itemView.findViewById(R.id.barrowBookCard);
            this.onUserModelClickListener = onUserModelClickListener;
            userCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onUserModelClickListener.onUserModelClick(Long.parseLong(String.valueOf(userIDText.getText())));
        }

    }

    public interface OnUserModelClickListener {
        void onUserModelClick(long UserModelId);
    }

}
