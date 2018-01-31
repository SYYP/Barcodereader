package qi.saoma.com.barcodereader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qi.saoma.com.barcodereader.R;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Mainadapter extends RecyclerView.Adapter<Mainadapter.myviewholder> {
    private Context context;
    private myviewholder myViewHolder;
    private List<String> list;
    private OnItemClickListener mOnItemClickListener = null;
    public Mainadapter(Context context) {
        this.context = context;
        data();
    }

    private void data() {
        list = new ArrayList<>();
        int s=2018-10-11;
        for (int i = 0; i <10 ; i++) {
            list.add(s+i+"");

        }

    }

    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder = new myviewholder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final myviewholder holder, int position) {
        if (mOnItemClickListener != null) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int layoutPosition = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);
            }
        });
        }

         holder.text_date.setText(list.get(position).toString());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        private TextView num_renwu;
        private RelativeLayout re_one;
        private View Vew_ome;
        private TextView food_name;
        private TextView food_num;
        private LinearLayout line_one;
        private TextView chang_name;
        private TextView food_weight;
        private final TextView text_date;

        public myviewholder(View itemView) {
            super(itemView);
            num_renwu  = itemView.findViewById(R.id.num_renwu);
            food_name  = itemView.findViewById(R.id.food_name);
            food_num  = itemView.findViewById(R.id.food_num);
            chang_name = itemView.findViewById(R.id.chang_name);
            food_weight = itemView.findViewById(R.id.food_weight);
            text_date = itemView.findViewById(R.id.text_date);
                        

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
