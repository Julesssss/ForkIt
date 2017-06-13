package info.forkit.recipelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.forkit.R;

public class RecipeListViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public RecipeListViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text_name);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        itemView.setOnClickListener(clickListener);
    }
}
