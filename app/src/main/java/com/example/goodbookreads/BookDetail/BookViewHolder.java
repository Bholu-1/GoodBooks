package com.example.goodbookreads.BookDetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodbookreads.Interface.ItemClickListner;
import com.example.goodbookreads.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView bookt,booka,bookp;
    public ImageView imageView;
    public ItemClickListner listner;


    public BookViewHolder(@NonNull View itemView)
    {
        super(itemView);


        imageView = itemView.findViewById(R.id.b_imaged);
        bookt = itemView.findViewById(R.id.booktitle);
        booka = itemView.findViewById(R.id.bookauthor);
        bookp = itemView.findViewById(R.id.bookprice);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;

    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
