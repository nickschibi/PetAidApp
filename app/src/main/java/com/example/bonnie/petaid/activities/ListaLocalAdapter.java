package com.example.bonnie.petaid.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Local;
import com.example.bonnie.petaid.presenter.CadastroOngEnderecosPresenter;


import java.util.ArrayList;

public class ListaLocalAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Local> list = new ArrayList<Local>();
    private Context context;
    private CadastroOngEnderecosPresenter presenter;



    public ListaLocalAdapter(ArrayList<Local> list, Context context, CadastroOngEnderecosPresenter presenter) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.get(pos).getIdLocal();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getEndereco().getEnd());

        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        ImageButton editBtn = (ImageButton) view.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.apagaLocal(list.get(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int idLocal;
                Intent intent = new Intent(context, CadastroLocalActivity.class);
                intent.putExtra("idLocal",idLocal= list.get(position).getIdLocal());
                context.startActivity(intent);

            }
        });



        return view;
    }
}
