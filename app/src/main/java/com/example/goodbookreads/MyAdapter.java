package com.example.goodbookreads;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private Context mcontext;
    private List<BookData> myBookList;

    public MyAdapter(Context mcontext, List<BookData> myBookList) {
        this.mcontext = mcontext;
        this.myBookList = myBookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item,parent,false);

        return new BookViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookViewHolder holder, int position) {

        Glide.with(mcontext)
                .load(myBookList.get(position).getImage())
                .into(holder.imageView);

        //holder.imageView.setImageResource(myBookList.get(position).getImageImage());
        holder.mTitle.setText(myBookList.get(position).getTitle());
        holder.mAuthor.setText(myBookList.get(position).getAuthor());
        holder.mPrice.setText(myBookList.get(position).getPrice());

        holder.mcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,ProductDetails.class);
                intent.putExtra("Image",myBookList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Title",myBookList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("Author",myBookList.get(holder.getAdapterPosition()).getAuthor());
                intent.putExtra("Price",myBookList.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("Likes",myBookList.get(holder.getAdapterPosition()).getLikes());
                intent.putExtra("Hates",myBookList.get(holder.getAdapterPosition()).getHates());
                intent.putExtra("Rating",myBookList.get(holder.getAdapterPosition()).getRating());
                intent.putExtra("Isbn",myBookList.get(holder.getAdapterPosition()).getIsbn());
                intent.putExtra("Pages",myBookList.get(holder.getAdapterPosition()).getNo_of_pages());
                intent.putExtra("Publisher",myBookList.get(holder.getAdapterPosition()).getPublisher());

                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBookList.size();
    }
}
    class  BookViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView mTitle,mPrice,mAuthor,mTotal;
        CardView mcardView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageviewimg);
            mTitle = itemView.findViewById(R.id.title2);
            mAuthor = itemView.findViewById(R.id.author1);
            mPrice = itemView.findViewById(R.id.price1);
            mcardView = itemView.findViewById(R.id.mycardview);


        }
    }

