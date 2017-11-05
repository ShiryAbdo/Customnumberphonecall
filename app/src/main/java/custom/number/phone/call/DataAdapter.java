package custom.number.phone.call;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by falcon on 04/11/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<PhoneData> androidList;
    private Context context;
    private int lastPosition=-1;

    public DataAdapter(ArrayList<PhoneData> android, Context c) {
        this.androidList = android;
        this.context=c;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout. contact_items_listview, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {


        viewHolder.name.setText(androidList.get(i).getName());
        viewHolder.phone.setText(androidList.get(i).getPhone());
        viewHolder.makAcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final Dialog okdialog = new Dialog(context, R.style.custom_dialog_theme);
                okdialog.setContentView(R.layout.dialog);
                Button privateid = (Button)okdialog.findViewById(R.id.privateid);
                Button Custom = (Button)okdialog.findViewById(R.id.Custom);
                privateid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PrivateNumber.class);
                        context.startActivity(intent);
                    }
                });
                Custom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,customNumber.class);
                        context.startActivity(intent);
                    }
                });

                okdialog.show();
//

            }
        });


    }






    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount()
    {
        return androidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name  ,phone;
 ImageView makAcall ;
         public ViewHolder(View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.name);
             phone=(TextView)view.findViewById(R.id.phone);
             makAcall =(ImageView)view.findViewById(R.id.makAcall);




        }
    }

}