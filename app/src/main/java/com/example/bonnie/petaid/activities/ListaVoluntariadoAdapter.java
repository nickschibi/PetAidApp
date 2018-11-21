package com.example.bonnie.petaid.activities;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.bonnie.petaid.R;
import com.example.bonnie.petaid.Utils;
import com.example.bonnie.petaid.model.Voluntariado;
import com.example.bonnie.petaid.presenter.VoluntariadoPresenter;
import java.util.ArrayList;

public class ListaVoluntariadoAdapter extends BaseAdapter implements ListAdapter {


    private ArrayList<Voluntariado> list = new ArrayList<>();
    private Context context;
    private VoluntariadoPresenter presenter;

    public ListaVoluntariadoAdapter(ArrayList<Voluntariado>list, Context context, VoluntariadoPresenter presenter) {
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
        return list.get(pos).getIdVoluntariado();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.voluntariado_item_list, null);
        }

        TextView nomeOngTextView = (TextView) view.findViewById(R.id.nomeOngTextView);
        TextView cnpjTextView = (TextView) view.findViewById(R.id.cnpjTextView);
        TextView nomeResponsavelTextView = (TextView) view.findViewById(R.id.nomeResponsavelTextView);
        TextView dataVoluntariadoTextView = (TextView) view.findViewById(R.id.dataVoluntariadoTextView);
        TextView telefoneTextView = (TextView) view.findViewById(R.id.telefoneTextView);
        TextView proprietarioTextView = (TextView) view.findViewById(R.id.proprietarioTextView);
        TextView numDocTextView = (TextView) view.findViewById(R.id.numDocTextView);
        TextView bancoTextView = (TextView) view.findViewById(R.id.bancoTextView);
        TextView agenciaTextView = (TextView) view.findViewById(R.id.agenciaTextView);
        TextView categoriaTextView = (TextView) view.findViewById(R.id.categoriaTextView);
        TextView contaBancariaTextView = (TextView) view.findViewById(R.id.contaBancariaTextView);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.linearConta);

        nomeOngTextView.setText(list.get(position).getLocal().getOrganizacao().getNomeFantasia());
        cnpjTextView.setText(list.get(position).getLocal().getOrganizacao().getNmCnpj());
        nomeResponsavelTextView.setText(list.get(position).getLocal().getNomeResponsavel());
        dataVoluntariadoTextView.setText(Utils.covertData(list.get(position).getDtVoluntariado())); //tirei o to string do final
        telefoneTextView.setText(list.get(position).getLocal().getTelefoneLocal());

        if(list.get(position).getLocal().getContaBancaria() != null){
            ll.setVisibility(View.VISIBLE); //Torna Visivel o Bloco de Campos Relacionados a Conta Bancaria apenas se tiver Conta
            proprietarioTextView.setText(list.get(position).getLocal().getContaBancaria().getNomeProprietario());
            numDocTextView.setText(list.get(position).getLocal().getContaBancaria().getNumDoc());
            bancoTextView.setText(list.get(position).getLocal().getContaBancaria().getBanco().getNomeBanco());
            agenciaTextView.setText(Integer.toString(list.get(position).getLocal().getContaBancaria().getCodAgencia()));
            contaBancariaTextView .setText(Integer.toString(list.get(position).getLocal().getContaBancaria().getCodConta()));
            categoriaTextView.setText(list.get(position).getLocal().getContaBancaria().getCategoriaConta().getTipoConta());
        } else {
            ll.setVisibility(View.GONE);
        }
        return view;
    }
}