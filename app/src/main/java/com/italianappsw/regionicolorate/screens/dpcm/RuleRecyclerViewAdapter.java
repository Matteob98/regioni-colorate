package com.italianappsw.regionicolorate.screens.dpcm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.italianappsw.regionicolorate.R;
import com.italianappsw.regionicolorate.models.Rule;

import java.util.List;

class RuleRecyclerViewAdapter extends RecyclerView.Adapter<RuleRecyclerViewAdapter.ViewHolder> {

    List<Rule> ruleList;
    Context context;

    public RuleRecyclerViewAdapter(List<Rule> ruleList, Context context) {
        this.ruleList = ruleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.rule_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RuleRecyclerViewAdapter.ViewHolder holder, int position) {
        Rule rule = ruleList.get(position);
        holder.mName.setText(rule.name);
        switch (rule.value) {
            case OPEN:
                holder.mImage.setImageResource(R.drawable.baseline_check_circle_outline_black_24);
                holder.mImage.setColorFilter(ContextCompat.getColor(
                        context.getApplicationContext(),
                        R.color.greenLight));
                break;
            case LIMITED:
                holder.mImage.setImageResource(R.drawable.baseline_error_outline_black_24);
                holder.mImage.setColorFilter(ContextCompat.getColor(
                        context.getApplicationContext(),
                        R.color.yellow));
                break;
            case CLOSED:
                holder.mImage.setImageResource(R.drawable.baseline_highlight_off_black_24);
                holder.mImage.setColorFilter(ContextCompat.getColor(
                        context.getApplicationContext(),
                        R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return ruleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private ImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.ruleTextView);
            mImage = itemView.findViewById(R.id.ruleImageView);
        }
    }
}
