package qi.saoma.com.barcodereader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.bean.Numberbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Shuruadapter extends RecyclerView.Adapter<Shuruadapter.MyViewHolder> {
      private ArrayList<Numberbean> list;
    private Context context;
    private MyViewHolder myViewHolder;
    private int MAX_SIZE = 15;

    public Shuruadapter(ArrayList<Numberbean> list, Context context) {
        this.list = list;
        this.context = context;
        for (int i = 0; i <list.size(); i++) {
            Log.d("tags",list.get(i).getMunber());

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycle,parent,false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        myViewHolder.setIsRecyclable(false);

            myViewHolder.item_ed.setText(list.get(position).getMunber());

               myViewHolder.item_ed.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                   }

                   @Override
                   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                   }

                   @Override
                   public void afterTextChanged(Editable editable) {

                        list.get(position).setMunber(editable.toString().trim());

                     // notifyDataSetChanged();


                   }
               });


    }

    @Override
    public int getItemCount() {
        if(list.size()<MAX_SIZE)
      return list.size();
        else {
            return 15;
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final EditText item_ed;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_ed = itemView.findViewById(R.id.item_ed);
        }
    }
}
