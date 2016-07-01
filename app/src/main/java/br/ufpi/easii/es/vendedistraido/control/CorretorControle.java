package br.ufpi.easii.es.vendedistraido.control;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import br.ufpi.easii.es.vendedistraido.exception.ExcecaoDeErroDeConexao;
import br.ufpi.easii.es.vendedistraido.exception.ExcecaoDeUsuarioJaExistente;
import br.ufpi.easii.es.vendedistraido.model.Corretor;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Irvayne Matheus on 30/06/2016.
 */
public class CorretorControle {
    private static final String SEND_URL = "";

    public void inserir(Corretor corretor) throws ExcecaoDeErroDeConexao, ExcecaoDeUsuarioJaExistente {
        final Gson gson = new Gson();
        String jsonCorretor = gson.toJson(corretor);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("objetoCorretor", jsonCorretor);

        final Corretor[] corretorResposta = new Corretor[1];

        client.post(SEND_URL, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
                        corretorResposta[0] = gson.fromJson(res, Corretor.class);

                        Log.i("LOG", "OK" + res);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                        Log.i("LOG","D:"+res);
                    }
                }
        );


        //insere no servidor

        //redireciona para tela apropriada
    }
}