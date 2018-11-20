package com.example.bonnie.petaid.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.model.Voluntario;
import com.example.bonnie.petaid.presenter.VoluntariosPresenter;
import java.util.ArrayList;

public class ListaVoluntariosAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Voluntario> list = new ArrayList<Voluntario>();
    private Context context;
    private VoluntariosPresenter presenter;

    public ListaVoluntariosAdapter(ArrayList<Voluntario> list, Context context, VoluntariosPresenter presenter) {
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
        return list.get(pos).getId_voluntario();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.voluntario_item_list, null);
        }

        TextView nomeVoluntarioTextView = (TextView) view.findViewById(R.id.nomeVoluntarioTexView);
        nomeVoluntarioTextView.setText(list.get(position).getNome_voluntario());
        TextView telefoneVoluntarioTextView = (TextView) view.findViewById(R.id.telefoneVoluntarioEditText);
        telefoneVoluntarioTextView.setText(list.get(position).getTelefone_voluntario());
        TextView emailVoluntarioTextView = (TextView) view.findViewById(R.id.emailVoluntarioTextView);
        emailVoluntarioTextView.setText(list.get(position).getEmail());
        return view;
    }
}
