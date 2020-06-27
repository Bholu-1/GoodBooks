package com.example.goodbookreads;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<cartviewHolder> {

    private int overtotal = 0;
    private String totalamount= "";
    private Context mcontext;
    private List<BookData> myBookList;
    public CartAdapter(Context mcontext, List<BookData> myBookList) {
        this.mcontext = mcontext;
        this.myBookList = myBookList;
    }

    @Override
    public cartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.insidecart,parent,false);

        return new cartviewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartviewHolder holder, int position) {

        holder.mTitle.setText(myBookList.get(position).getTitle());
        holder.mAuthor.setText(myBookList.get(position).getAuthor());
        holder.mPrice.setText(myBookList.get(position).getPrice());

        int onetypeproductprice = ((Integer.valueOf(myBookList.get(position).getPrice())));
        overtotal = overtotal + onetypeproductprice;

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


    class cartviewHolder extends RecyclerView.ViewHolder{
        TextView mTitle,mPrice,mAuthor,mTotal;
        CardView mcardView;

        public cartviewHolder(@NonNull View itemView) {
            super(itemView);

            mTotal = itemView.findViewById(R.id.total);
            mTitle = itemView.findViewById(R.id.title2);
            mAuthor = itemView.findViewById(R.id.author1);
            mPrice = itemView.findViewById(R.id.price1);
            mcardView = itemView.findViewById(R.id.mycardview);
        }
    }

