package com.example.libraryapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<BookModel> mBookModels;
    private long adapterPosition = -1;
    private BookModel mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private final Activity mActivity;
    private OnDeleteListener listener;
    private boolean mCanDelete = true;
    private final OnBookModelClickListener mOnBookModelClickListener;

    public CustomAdapter(Context context, Activity activity, OnBookModelClickListener onBookModelClickListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mActivity = activity;
        mOnBookModelClickListener = onBookModelClickListener;
    }

    public interface OnDeleteListener {
        void deleteBookModel(BookModel BookModel);
    }

    public void setOnBookModelDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.custom_listview, parent, false);
        return new ViewHolder(itemView, mOnBookModelClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        BookModel model = mBookModels.get(position);


        holder.nameText.setText(model.getName());
        holder.authorText.setText(model.getAuthor());
        holder.publisherText.setText(model.getPublisher());
        holder.typeText.setText(model.getType());
        holder.bookImage.setImageResource(R.drawable.books);
        holder.bookIDText.setText(String.valueOf(model.getID()));



    }

    @Override
    public int getItemCount() {
        if (mBookModels == null) return 0;
        return mBookModels.size();
    }

    public Context getContext() {
        return mContext;
    }





    /*private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.string_BookModel_deleted,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (mCanDelete) {
                    listener.deleteBookModel(mRecentlyDeletedItem);
                }
            }
        });
        snackbar.show();
    }*/



    public void setAdapterPosition(long adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public void setBookModels(List<BookModel> bookModelList, long removedPosition) {
        this.mBookModels = bookModelList;
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
        private final TextView nameText;
        private final TextView authorText;
        private final TextView publisherText;
        private final TextView typeText;
        private final ImageView bookImage;
        private final TextView bookIDText;
        private final CardView bookCard;

        OnBookModelClickListener onBookModelClickListener;

        public ViewHolder(@NonNull View itemView, OnBookModelClickListener onBookModelClickListener) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameCustomList);
            authorText = itemView.findViewById(R.id.authorCustomList);
            publisherText = itemView.findViewById(R.id.publisherCustomList);
            typeText=itemView.findViewById(R.id.typeCustomList);
            bookImage=itemView.findViewById(R.id.bookImage);
            bookIDText=itemView.findViewById(R.id.bbBookIDText);
            bookCard=itemView.findViewById(R.id.bookCard);
            this.onBookModelClickListener = onBookModelClickListener;
            bookCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBookModelClickListener.onBookModelClick(Long.parseLong(String.valueOf(bookIDText.getText())));
        }


        /*@Override
        public void onClick(View view) {
            onBookModelClickListener.onBookModelClick(Long.parseLong(String.valueOf(BookModelId.getText())));

        }*/
    }

    public interface OnBookModelClickListener {
        void onBookModelClick(long BookModelId);
    }

    /*public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private final BookModelsAdapter mAdapter;
        private final Drawable icon;
        private final ColorDrawable background;


        public SwipeToDeleteCallback(BookModelsAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;

            icon = ContextCompat.getDrawable(mAdapter.getContext(),
                    R.drawable.ic_delete_white);
            background = new ColorDrawable(ContextCompat.getColor(mAdapter.getContext(), R.color.error));
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getLayoutPosition();
            mAdapter.deleteItem(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;
            if (dX > 0) { // Swiping to the right
                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());

            } else if (dX < 0) { // Swiping to the left
                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }
            background.draw(c);

            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + icon.getIntrinsicHeight();

            if (dX > 0) { // Swiping to the right
                int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                int iconRight = itemView.getLeft() + iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());
            } else if (dX < 0) { // Swiping to the left
                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }

            background.draw(c);
            icon.draw(c);
        }
    }*/
}
