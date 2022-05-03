package controller;

import model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class CarrinhoController {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/carrinho")
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        var writer = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        var nome = request.getParameter("nome");
        int quantidade = Integer.parseInt(request.getParameter("qtd"));
        var descricao = request.getParameter("desc");
        var preco = request.getParameter("preco");

        Produtos produtos = new Produtos(id,nome,quantidade,preco,descricao);
        ArrayList <Produtos> lista_carrinhos = new ArrayList<>();

        lista_carrinhos.add(produtos);

        var index = lista_carrinhos.size();
        String[] id_arr = new String[index];
        String[] quantidade_arr = new String[index];
        String[] nome_arr = new String[index];
        String[] preco_arr = new String[index];
        String[] descricao_arr = new String[index];
        String id_li = "",nome_li = "",qtd_li = "",preco_li = "",descricao_li = "";



        for(int i = 0; i<index; i++){

            id_arr[i] = String.valueOf(lista_carrinhos.get(i).getId());
            quantidade_arr[i] = String.valueOf(lista_carrinhos.get(i).getQuantidade());
            nome_arr[i]= lista_carrinhos.get(i).getNome();
            preco_arr[i] = lista_carrinhos.get(i).getPreco();
            descricao_arr[i] = lista_carrinhos.get(i).getDescricao();

            id_arr[i] = "<a href = \"/carrinho?id="+id_arr[i]+"&nome="+nome_arr[i]
                    +"&desc="+descricao_arr[i]+"&qtd="
                    +quantidade_arr[i]+"&preco="+preco_arr[i]+"\">adicionar </a>";

            quantidade_arr[i] = "<li>"+quantidade_arr[i]+"</li>";
            nome_arr[i] = "<li>"+nome_arr[i]+"</li>";
            preco_arr[i] = "<li>"+preco_arr[i]+"</li>";
            descricao_arr[i] = "<li>"+descricao_arr[i]+"</li>";
        }


        for(int i = 0; i<index; i++){
            id_li += id_arr[i]+ "\n";
            qtd_li += quantidade_arr[i] + "\n";
            nome_li += nome_arr[i] + "\n";
            preco_li += preco_arr[i] + "\n";
            descricao_li += descricao_arr[i] + "\n";

        }


        var html = "<body>\n" +
                "    <title>Carrinho de compras</title>\n" +
                "    <h1>Carrinho de compras</h1>\n" +
                "    <div style=\" grid-template-columns: 150px 150px 150px 150px 100px;\">\n" +
                "        <div style=\" grid-column-start: 1;\">Nome\n" +
                nome_li+"\n"+

                "        </div>\n" +
                "        <div style=\" grid-column-start: 2;\">Descrição\n" +
                descricao_li+"\n"+
                "        </div>\n" +
                "        <div style=\" grid-column-start: 3;\">Quantidade\n" +
                qtd_li+"\n"+
                "        </div>\n" +
                "        <div style=\" grid-column-start: 4;\">Preço\n" +
                preco_li+"\n"+
                "        </div>\n" +
                "        <div style=\" grid-column-start: 5;\">Adicionar\n" +
                id_li+"\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <style>\n" +
                "        div{\n" +
                "            display: grid;\n" +
                "        }\n" +
                "        li{\n" +
                "            list-style: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "</body>";

        writer.println(html);
        writer.close();


   }
}
