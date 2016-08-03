package br.com.ekbt.persistencia;

/**
 * Created by rm74043 on 03/08/2016.
 */
public class Produto {
    private Integer id;
    private String produto;
    private float preco;

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "produto='" + produto + '\'' +
                ", preco=" + preco +
                '}';
    }
}
