package binaries.app.codeutsava.restapi.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerProduceDetail;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;

public class AdapterProduce extends RecyclerView.Adapter<AdapterProduce.ViewHolder> {
    private List<FarmerProduceResponse> produces;
    private Activity activity;
    private FragmentManager fragManager;

    public void setFragManager(FragmentManager fragManager) {
        this.fragManager = fragManager;
    }

    public AdapterProduce(List<FarmerProduceResponse> produces, Activity activity) {
        this.produces = produces;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterProduce.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.farmer_produce_row, null);

        return new AdapterProduce.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduce.ViewHolder holder, int position) {
        if (produces != null) {
            FarmerProduceResponse produce = produces.get(position);

            holder.fgName.setText(produce.type.type);
            holder.fgGrade.setText("Grade: " + produce.grade);
            holder.fgPrice.setText("Price: " + produce.price);
            holder.fgDate.setText("Date: " + produce.date);

            holder.itemView.setOnClickListener(v -> {
                FarmerProduceResponse currProduceData = produces.get(position);

                Bundle args = new Bundle();
                args.putSerializable("produce", currProduceData);

                FragmentFarmerProduceDetail produceDetail = new FragmentFarmerProduceDetail();
                produceDetail.setArguments(args);
                produceDetail.show(fragManager, "....");
            });
        }
    }

    @Override
    public int getItemCount() {
        return produces == null ? 0 : produces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fgName, fgPrice, fgGrade, fgDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            fgName = itemView.findViewById(R.id.food_prod_row_name);
            fgPrice = itemView.findViewById(R.id.food_prod_row_price);
            fgGrade = itemView.findViewById(R.id.food_prod_row_grade);
            fgDate = itemView.findViewById(R.id.food_prod_row_date);
        }
    }
}
