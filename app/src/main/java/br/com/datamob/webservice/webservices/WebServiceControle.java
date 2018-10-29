package br.com.datamob.webservice.webservices;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.datamob.webservice.webservices.content.Data;
import br.com.datamob.webservice.webservices.content.UniversidadesSquidexInfo;
import br.com.datamob.webservice.webservices.content.Token;

public class WebServiceControle
{
    /**
     * Responsável por gerenciar as threads que realizam as chamadas web
     */
    private static RequestQueue requestQueue;
    private static Token token;

    public RequestQueue getRequestQueueInstance(Context context)
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public void carregaListaUniversidades(final Context context
            , final CarregaListaUniversidadesListener carregaListaUniversidadesListener)
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {
                    carregaListaUniversidades(context, carregaListaUniversidadesListener);
                }

                @Override
                public void onErro()
                {
                    if (carregaListaUniversidadesListener != null)
                        carregaListaUniversidadesListener.onErro();
                }
            });
        }
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://cloud.squidex.io/api/content/barao/universidades",
                    null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            if (carregaListaUniversidadesListener != null)
                                carregaListaUniversidadesListener.onResultOk(new Gson().fromJson(response.toString(), UniversidadesSquidexInfo.class));
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (carregaListaUniversidadesListener != null)
                                carregaListaUniversidadesListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(jsonObjectRequest);
        }
    }

    public void criaUniversidade(final Context context, final Data data, final UpdateUniversidadeListener criaUniversidadeListener) throws JSONException
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {
                    try
                    {
                        criaUniversidade(context, data, criaUniversidadeListener);
                    }
                    catch (JSONException e)
                    {
                        if (criaUniversidadeListener != null)
                            criaUniversidadeListener.onErro();
                    }
                }

                @Override
                public void onErro()
                {
                    if (criaUniversidadeListener != null)
                        criaUniversidadeListener.onErro();
                }
            });
        }
        else
        {
            JsonObjectRequest jsonObjectRequest
                    = new JsonObjectRequest(Request.Method.POST,
                    "https://cloud.squidex.io/api/content/barao/universidades?publish=true",
                    new JSONObject(new Gson().toJson(data)),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            if (criaUniversidadeListener != null)
                                criaUniversidadeListener.onResultOk();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (criaUniversidadeListener != null)
                                criaUniversidadeListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(jsonObjectRequest);
        }
    }

    public void atualizaUniversidade(final Context context, final Data data, final String id, final UpdateUniversidadeListener atualizaUniversidadeListener) throws JSONException
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {
                    try
                    {
                        atualizaUniversidade(context, data, id, atualizaUniversidadeListener);
                    }
                    catch (JSONException e)
                    {
                        if (atualizaUniversidadeListener != null)
                            atualizaUniversidadeListener.onErro();
                    }
                }

                @Override
                public void onErro()
                {
                    if (atualizaUniversidadeListener != null)
                        atualizaUniversidadeListener.onErro();
                }
            });
        }
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    "https://cloud.squidex.io/api/content/barao/universidades/" + id,
                    new JSONObject(new Gson().toJson(data)),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            if (atualizaUniversidadeListener != null)
                                atualizaUniversidadeListener.onResultOk();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (atualizaUniversidadeListener != null)
                                atualizaUniversidadeListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(jsonObjectRequest);
        }
    }

    public void deletaUniversidade(final Context context, final String id, final UpdateUniversidadeListener deleteUniversidadeListener)
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {

                    deletaUniversidade(context, id, deleteUniversidadeListener);

                }

                @Override
                public void onErro()
                {
                    if (deleteUniversidadeListener != null)
                        deleteUniversidadeListener.onErro();
                }
            });
        }
        else
        {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                    "https://cloud.squidex.io/api/content/barao/universidades/" + id,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if (deleteUniversidadeListener != null)
                                deleteUniversidadeListener.onResultOk();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (deleteUniversidadeListener != null)
                                deleteUniversidadeListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(stringRequest);
        }
    }

    private void geraToken(Context context, final GeraTokenListener geraTokenListener)
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://cloud.squidex.io/identity-server/connect/token",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        token = new Gson().fromJson(response, Token.class);
                        if (geraTokenListener != null)
                            geraTokenListener.onTokenOk();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (geraTokenListener != null)
                            geraTokenListener.onErro();
                    }
                }
        )
        {
            @Override
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("grant_type", "client_credentials");
                params.put("client_id", "barao:barao");
                params.put("client_secret", "oHdj7Gbcwrwy8dAaxo7nxcDlyTU7D409IICB2tGqo7A=");
                params.put("scope", "squidex-api");
                return params;
            }
        };
        //
        getRequestQueueInstance(context).add(stringRequest);
    }


    public interface GeraTokenListener
    {
        public abstract void onTokenOk();

        public abstract void onErro();
    }

    public interface CarregaListaUniversidadesListener
    {
        public abstract void onResultOk(UniversidadesSquidexInfo universidades);

        public abstract void onErro();
    }

    public interface UpdateUniversidadeListener
    {
        public abstract void onResultOk();

        public abstract void onErro();
    }
}
