package br.com.datamob.webservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.datamob.webservice.webservices.WebServiceControle;
import br.com.datamob.webservice.webservices.content.UniversidadesSquidexInfo;
import br.com.datamob.webservice.webservices.content.Item;

public class ListagemActivity extends AppCompatActivity
{
    public static final String EXTRA_REGISTRO = "br.com.datamob.webservice.ListagemActivity.EXTRA_REGISTRO";
    private ListView lvUniversidades;
    private SwipeRefreshLayout srUniversidades;
    private FloatingActionButton fabConfirmar;
    private UniversidadesSquidexInfo listUniversidades;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        //
        inicializaComponenetes();
        criaAdapterLista();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        carregaListaUniversidades();
    }

    private void inicializaComponenetes()
    {
        lvUniversidades = findViewById(R.id.lvUniversidades);
        srUniversidades = findViewById(R.id.srUniversidades);
        fabConfirmar = findViewById(R.id.fabConfirmar);
        srUniversidades.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                carregaListaUniversidades();
            }
        });
        //
        fabConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ListagemActivity.this, CadastroActivity.class));
            }
        });
        //
        lvUniversidades.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Item item = listUniversidades.getItems()[position];
                Intent intent = new Intent(ListagemActivity.this, CadastroActivity.class);
                intent.putExtra(EXTRA_REGISTRO, item);
                startActivity(intent);
            }
        });
    }

    private void criaAdapterLista()
    {
        lvUniversidades.setAdapter(new ArrayAdapter<Object>(this, 0)
                                   {
                                       class ViewHolder
                                       {
                                           TextView tvNome;
                                           TextView tvCidade;
                                           TextView tvEstado;
                                       }

                                       @Override
                                       public int getCount()
                                       {
                                           if (listUniversidades != null)
                                               return listUniversidades.getTotal().intValue();
                                           else
                                               return 0;
                                       }

                                       @NonNull
                                       @Override
                                       public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
                                       {
                                           ViewHolder viewHolder;
                                           Item item = listUniversidades.getItems()[position];
                                           //
                                           if (convertView == null)
                                           {
                                               convertView = getLayoutInflater().inflate(R.layout.item_listagem, null);
                                               viewHolder = new ViewHolder();
                                               convertView.setTag(viewHolder);
                                               //
                                               viewHolder.tvNome = convertView.findViewById(R.id.tvNome);
                                               viewHolder.tvCidade = convertView.findViewById(R.id.tvCidade);
                                               viewHolder.tvEstado = convertView.findViewById(R.id.tvEstado);
                                           }
                                           else
                                               viewHolder = (ViewHolder) convertView.getTag();
                                           //
                                           viewHolder.tvNome.setText(item.getData().getNome().getIv());
                                           viewHolder.tvCidade.setText(item.getData().getCidade().getIv());
                                           viewHolder.tvEstado.setText(item.getData().getEstado().getIv());
                                           //
                                           return convertView;
                                       }
                                   }
        );
    }

    private void carregaListaUniversidades()
    {
        srUniversidades.setRefreshing(true);
        new WebServiceControle().carregaListaUniversidades(this, new WebServiceControle.CarregaListaUniversidadesListener()
        {
            @Override
            public void onResultOk(UniversidadesSquidexInfo universidades)
            {
                listUniversidades = universidades;
                ((ArrayAdapter) lvUniversidades.getAdapter()).notifyDataSetChanged();
                srUniversidades.setRefreshing(false);
            }

            @Override
            public void onErro()
            {
                listUniversidades = null;
                ((ArrayAdapter) lvUniversidades.getAdapter()).notifyDataSetChanged();
                srUniversidades.setRefreshing(false);
            }
        });

    }
}
