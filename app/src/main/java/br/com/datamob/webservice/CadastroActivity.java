package br.com.datamob.webservice;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Spinner;

import org.json.JSONException;

import br.com.datamob.webservice.dialogs.PopupInformacao;
import br.com.datamob.webservice.webservices.WebServiceControle;
import br.com.datamob.webservice.webservices.content.Data;
import br.com.datamob.webservice.webservices.content.Item;
import br.com.datamob.webservice.webservices.content.StringValue;

public class CadastroActivity extends AppCompatActivity
{
    private TextInputLayout tilNome;
    private TextInputEditText etNome;
    private TextInputLayout tilCidade;
    private TextInputEditText etCidade;
    private Spinner spEstado;
    //
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        //
        item = (Item) getIntent().getSerializableExtra(ListagemActivity.EXTRA_REGISTRO);
        //
        carregaValores();
    }

    private void inicializaComponentes()
    {
        tilNome = findViewById(R.id.tilNome);
        etNome = findViewById(R.id.etNome);
        tilCidade = findViewById(R.id.tilCidade);
        etCidade = findViewById(R.id.etCidade);
        spEstado = findViewById(R.id.spEstado);
        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        FloatingActionButton fabDeletar = findViewById(R.id.fabDeletar);
        //
        etNome.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                tilNome.setError(null);
            }
        });
        etCidade.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                tilCidade.setError(null);
            }
        });
        //
        fabConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmaTela();
            }
        });
        //
        fabDeletar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deletaRegistro();
            }
        });
    }

    private boolean validaTela()
    {
        boolean retorno = true;
        //
        if (etNome.getText().toString().trim().length() == 0)
        {
            tilNome.setError("Informe o nome da universidade");
            retorno = false;
        }
        //
        if (etCidade.getText().toString().trim().length() == 0)
        {
            tilCidade.setError("Informe a cidade");
            retorno = false;
        }
        //
        if (spEstado.getSelectedItemPosition() <= 0)
        {
            PopupInformacao.mostraMensagem(this, "Selecione o estado");
            retorno = false;
        }
        //
        return retorno;
    }

    private void confirmaTela()
    {
        if (!validaTela())
            return;

        salvaRegistro();
    }

    private void salvaRegistro()
    {
        Data data = new Data();
        data.setNome(new StringValue(etNome.getText().toString()));
        data.setCidade(new StringValue(etCidade.getText().toString()));
        data.setEstado(new StringValue(spEstado.getSelectedItem().toString()));
        //
        try
        {
            if(item == null)
            {
                new WebServiceControle().criaUniversidade(this, data, new WebServiceControle.UpdateUniversidadeListener()
                {
                    @Override
                    public void onResultOk()
                    {
                        CadastroActivity.this.finish();
                    }

                    @Override
                    public void onErro()
                    {
                        PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao salvar registro");
                    }
                });
            }
            else
            {
                new WebServiceControle().atualizaUniversidade(this, data, item.getId(), new WebServiceControle.UpdateUniversidadeListener()
                {
                    @Override
                    public void onResultOk()
                    {
                        CadastroActivity.this.finish();
                    }

                    @Override
                    public void onErro()
                    {
                        PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao salvar registro");
                    }
                });
            }
        }
        catch (JSONException e)
        {
            PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao salvar registro");
        }
        //

    }

    private void carregaValores()
    {
        if(item != null)
        {
            etCidade.setText(item.getData().getCidade().getIv());
            etNome.setText(item.getData().getNome().getIv());
            for (int i = 1; i < spEstado.getCount(); i++)
            {
                if (((String) spEstado.getItemAtPosition(i)).equals(item.getData().getEstado().getIv()))
                {
                    spEstado.setSelection(i, true);
                    break;
                }
            }
        }
    }

    private void deletaRegistro()
    {
        if(item != null)
        {
            new WebServiceControle().deletaUniversidade(this, item.getId(), new WebServiceControle.UpdateUniversidadeListener()
            {
                @Override
                public void onResultOk()
                {
                    CadastroActivity.this.finish();
                }

                @Override
                public void onErro()
                {
                    PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao salvar registro");
                }
            });
        }
    }

}
