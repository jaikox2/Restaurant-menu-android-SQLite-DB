package net.simplifiedcoding.bottomnavigationexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<Food> mFoodList;
    private Context mContext;
    private RecyclerView mRecyclerV;



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodNameTxtV;
        public TextView foodPriceTxtV;
        public ImageView foodImageImgV;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            foodNameTxtV = (TextView) v.findViewById(R.id.textViewName);
            foodPriceTxtV = (TextView) v.findViewById(R.id.textViewPrice);
            foodImageImgV = (ImageView) v.findViewById(R.id.imageViewDis);

        }
    }

    public void add(int position, Food food) {
        mFoodList.add(position, food);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mFoodList.remove(position);
        notifyItemRemoved(position);
    }


    public FoodAdapter(List<Food> myDataset, Context context, RecyclerView recyclerView) {
        mFoodList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.single_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final Food food = mFoodList.get(position);
        holder.foodNameTxtV.setText("เมนู: " + food.getName());
        holder.foodPriceTxtV.setText("ราคา: " + food.getPrice()+" บาท");
        Bitmap imagebitmap = DbBitmapUtility.getImage(food.getImage());
        holder.foodImageImgV.setImageBitmap(imagebitmap);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(food.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteHelper dbHelper = new SQLiteHelper(mContext);
                        dbHelper.deleteFoodRecord(food.getId(), mContext);

                        mFoodList.remove(position);
                       // mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mFoodList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(mContext, UpdateActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

}
