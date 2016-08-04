package br.com.ekbt.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtProduto, edtPreco;
    private ListView listProdutos;
    private Button btnNovo;

    private SQLiteDatabase conn;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = (EditText) findViewById(R.id.edtId);
        edtProduto = (EditText) findViewById(R.id.edtProduto);
        edtPreco = (EditText) findViewById(R.id.edtPreco);
        listProdutos = (ListView) findViewById(R.id.listProdutos);
        btnNovo = (Button) findViewById(R.id.btnNovo);

        DBProduto db = new DBProduto(this);
        conn = db.getWritableDatabase();
        carregaLista();

        listProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Produto produto = (Produto) listProdutos.getItemAtPosition(position);
                preencherEdts(produto);
                btnNovo.setEnabled(true);
            }
        });
    }

    public void gravar() {
        if (validaCampos()) {
            String strId = edtId.getText().toString().trim();
            if (TextUtils.isEmpty(strId)) {
                inserir();
            } else {
                atualizar(strId);
            }
        } else {
            //msg
        }
    }

    private void inserir() {
        ContentValues cv = pegaDados();
        conn.insert("TBL_PRODUTO", null, cv);
    }

    private void atualizar(String strId) {
        ContentValues cv = pegaDados();
        String[] id = {strId};
        conn.update("TBL_PRODUTO", cv, "ID=?", id);
    }

    private void excluir(View v) {
        if (validaCampos()) {
            String strId = edtId.getText().toString().trim();
            String[] id = {strId};
            conn.delete("TBL_PRODUTO", "ID=?", id);
        } else {
            //msg
        }
    }

    //ContentValues encapsula os valores
    private ContentValues pegaDados() {
        ContentValues cv = new ContentValues();
        cv.put("NM_PRODUTO", edtProduto.getText().toString());
        cv.put("VL_PRECO", edtPreco.getText().toString());
        return cv;
    }

    private boolean validaCampos() {
        String produto = edtProduto.getText().toString().trim();
        String preco = edtPreco.getText().toString().trim();
        return (!TextUtils.isEmpty(produto)  && !TextUtils.isEmpty(preco));
    }

    private void limpaTela() {
        edtId.setText("");
        edtProduto.setText("");
        edtPreco.setText("");
        edtProduto.requestFocus();
    }

    public void preencherEdts(Produto produto) {
        if (produto != null) {
            edtId.setText(produto.getId().toString());
            edtProduto.setText(produto.getProduto().toString());
            edtPreco.setText(produto.getPreco().toString());
        }
    }

    public void msgCurta(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        if(conn != null) conn.close();
        if(cursor != null) cursor.close();
        super.onDestroy();
    };

    private void carregaLista() {
        List<Produto> produtos = new ArrayList<>();

        cursor = conn.query("TBL_PRODUTO", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Produto produto = new Produto();
            produto.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            produto.setProduto(cursor.getString(cursor.getColumnIndex("NM_PRODUTO")));
            produto.setPreco(cursor.getDouble(cursor.getColumnIndex("VL_PRECO_REAL")));
            produtos.add(produto);
            cursor.moveToNext();
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtos);
        listProdutos.setAdapter(adapter);
    };


}
