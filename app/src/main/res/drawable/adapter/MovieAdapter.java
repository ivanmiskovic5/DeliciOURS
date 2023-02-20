package ba.sum.fpmoz.deliciours.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.deliciours.R;
import ba.sum.fpmoz.deliciours.model.food;

public class foodAdapter extends FirebaseRecyclerAdapter<food, foodAdapter.foodViewHolder> {

    Context cxt;
    public foodAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull foodAdapter.foodViewHolder holder, int position, @NonNull food model) {
        holder.foodTitleTxt.setText(model.getName());
        holder.foodingredientsTxt.setText(model.getingredients());
        holder.foodScoreTxt.setText(model.getScore().toString());
        holder.foodReceptTxt.setText(model.getRecept());
        Picasso
                .get()
                .load(model.getImage())
                .into(holder.foodImage);
    }

    @NonNull
    @Override
    public foodAdapter.foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.cxt = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_view, parent, false);
        return new foodAdapter.foodViewHolder(view);
    }


    public class foodViewHolder extends RecyclerView.ViewHolder {
        TextView foodTitleTxt, foodingredientsTxt, foodScoreTxt, foodReceptTxt;
        ImageView foodImage;
        public foodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTitleTxt = itemView.findViewById(R.id.foodTitleTxt);
            foodingredientsTxt = itemView.findViewById(R.id.foodingredientsTxt);
            foodScoreTxt = itemView.findViewById(R.id.foodScoreTxt);
            foodReceptTxt = itemView.findViewById(R.id.foodReceptTxt);
            foodImage = itemView.findViewById(R.id.foodImage);
        }
    }
}
