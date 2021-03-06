package br.ufpi.easii.es.vendedistraido.view.corretor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufpi.easii.es.vendedistraido.R;
import br.ufpi.easii.es.vendedistraido.control.ImovelControle;
import br.ufpi.easii.es.vendedistraido.model.Corretor;
import br.ufpi.easii.es.vendedistraido.model.Imovel;
import br.ufpi.easii.es.vendedistraido.util.Constantes;
import br.ufpi.easii.es.vendedistraido.view.MainInterface;
import br.ufpi.easii.es.vendedistraido.view.cliente.AdapterListView;
import br.ufpi.easii.es.vendedistraido.view.cliente.ImovelClienteActivity;

/**
 * Created by Jpegx.
 * Activity que implementa a interface MainInterface e e responsavel pela tela inicial do Corretor.
 */
public class CorretorActivity extends AppCompatActivity implements MainInterface{
    private Button btn_cadastrar;
    private ListView lista_imoveis;
    public static String ID_CORRETOR = "id_corretor";
    private Corretor corretor;
    /**
     * Metodo padrao
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corretor);
        setTitle("Imoveis");
        this.corretor = usuarioLogado();
        btn_cadastrar = (Button)findViewById(R.id.corretor_btn_cadastar_imovel);
        btn_cadastrar.setOnClickListener(onClickCadastrar());
        lista_imoveis = (ListView)findViewById(R.id.corretor_list_lista_imoveis);
        ImovelControle.pesquisar(corretor, getContext(),CorretorActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImovelControle.pesquisar(corretor, getContext(),CorretorActivity.this);
    }

    /**
     * Metodo que transforma os dados do arquivo de preferencias no objeto Corretor logado na sessao.
     * @return retorna o corretor logado ou null casso nao haja alguem logado
     */
    private Corretor usuarioLogado(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.USER, Context.MODE_PRIVATE);
        if(sharedPreferences == null) return null;
        Corretor corretor = new Corretor(sharedPreferences.getLong(Constantes.USER_LOGIN_ID,-1),
                sharedPreferences.getString(Constantes.USER_LOGIN_NOME,"-1"),
                sharedPreferences.getString(Constantes.USER_LOGIN_EMAIL,"-1"),
                sharedPreferences.getString(Constantes.USER_LOGIN_SENHA,"-1"),
                sharedPreferences.getString(Constantes.USER_LOGIN_TELEFONE,"-1"),
                //Pegar LISTA de IMOVEIS
                new ArrayList<Imovel>());
        return corretor;
    }
    private View.OnClickListener onClickCadastrar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CadastraImovelActivity.class);
                intent.putExtra(ID_CORRETOR, corretor.getId());
                startActivity(intent);
            }
        };
    }
    private Context getContext(){
        return this;
    }
    /**
     * Metodo de view.MainInterface
     * @param dados
     */
    @Override
    public void dadosLidos(Object dados) {
        final ArrayList<Imovel> imoveis = new ArrayList<Imovel>();
        if((dados instanceof ArrayList) && (((ArrayList) dados).size()>0)){
            if(((ArrayList) dados).get(0) instanceof Imovel){
                for(Imovel i:(ArrayList<Imovel>)dados){
                    imoveis.add(i);
                }
                AdapterListView adapter = new AdapterListView(this, R.layout.item_lista, imoveis);
                lista_imoveis.setAdapter(adapter);

                lista_imoveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), ImovelCorretorActivity.class);
                        intent.putExtra(Constantes.IMOVEL_ENDERECO, imoveis.get(position).getEndereco());
                        intent.putExtra(Constantes.IMOVEL_VALOR, imoveis.get(position).getValor());
                        intent.putExtra(Constantes.IMOVEL_ID, imoveis.get(position).getId());
                        //intent.putExtra(Constantes.IMOVEL_FOTO, imoveis.get(position).getFoto());
                        startActivity(intent);
                    }
                });
            }
        }
    }
    /**
     * Metodo de view.MainInterface
     * @param e
     */
    @Override
    public void dadosNaoLidos(Exception e) {
    }
}