package kyi.boost2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kyu on 2017-07-10.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Item> list;

    public RecyclerAdapter(List<Item> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        final Item item = list.get(pos);

        holder.image.setImageResource(item.getImage());
        holder.name.setText(item.getName());
        holder.content.setText(item.getContent());
        holder.check.setBackgroundResource(item.isCheck() ? R.drawable.checked : R.drawable.unchecked);
        holder.check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(item.isCheck()) {
                    item.setCheck(false);
                }
                else {
                    item.setCheck(true);
                }
                holder.check.setBackgroundResource(item.isCheck() ? R.drawable.checked : R.drawable.unchecked);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        Button check;
        TextView name, content;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.card_image);
            check = (Button) v.findViewById(R.id.card_check);
            name = (TextView) v.findViewById(R.id.card_name);
            content = (TextView) v.findViewById(R.id.card_content);
        }
    }
}
