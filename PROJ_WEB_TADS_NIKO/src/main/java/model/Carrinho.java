package model;

import java.util.ArrayList;

public class Carrinho {
    private ArrayList<Produtos> produtos = new ArrayList<>();


    public ArrayList<Produtos> getProdutos() {
        return this.produtos;
    }

    public void addProduto(ArrayList<Produtos> produtos) {
        this.produtos = produtos;

    }
}


