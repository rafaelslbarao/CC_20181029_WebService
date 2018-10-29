package br.com.datamob.webservice.webservices.content;

import java.io.Serializable;

public class Data implements Serializable
{
    private StringValue nome;
    private StringValue cidade;
    private StringValue estado;

    public StringValue getNome()
    {
        return nome;
    }

    public void setNome(StringValue nome)
    {
        this.nome = nome;
    }

    public StringValue getCidade()
    {
        return cidade;
    }

    public void setCidade(StringValue cidade)
    {
        this.cidade = cidade;
    }

    public StringValue getEstado()
    {
        return estado;
    }

    public void setEstado(StringValue estado)
    {
        this.estado = estado;
    }
}
