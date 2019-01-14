package com.example.viraj.swimmingapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    private Context mContext;
    private List<Person> personList = new ArrayList<>();
//@layout res
    public PersonAdapter(@NonNull Context context,ArrayList<Person> list) {
        super(context, 0 , list);
        mContext = context;
        personList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_recycler_template
                    ,parent,false);

        Person currentPerson = personList.get(position);

      //  TextView image = (TextV)listItem.findViewById(R.id.imageView_poster);
       // image.setImageResource(currentMovie.getmImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.lbl_message);
        name.setText(currentPerson.getmName());

        TextView stroke = (TextView) listItem.findViewById(R.id.best_stroke_text);
        stroke.setText(currentPerson.getmBestStroke());

        TextView age = (TextView) listItem.findViewById(R.id.age);
        age.setText(currentPerson.getmAge());


        return listItem;
    }

    public void notify(ArrayList<Person> list){
        this.personList= list;
        notifyDataSetChanged();
    }
}