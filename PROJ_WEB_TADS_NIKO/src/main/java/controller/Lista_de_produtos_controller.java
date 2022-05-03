package controller;

import model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

@Controller
public class Lista_de_produtos_controller {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/lista_de_produtos")
    public void pagina_do_cliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String add = "null";
        String nomeP = "null";
        String qtd = "null";
        String desc = "null";
        String precoP = "null";
        request.setAttribute("id", add);
        request.setAttribute("nome",nomeP);
        request.setAttribute("qtd",qtd);
        request.setAttribute("desc",desc);
        request.setAttribute("preco",precoP);

        ArrayList<Produtos> lista_de_produtos = new ArrayList<>();


        var write = response.getWriter();

        final String sql = "SELECT * FROM Produtos";
        jdbcTemplate.query(sql, (ResultSet rs) -> {
            do{

                Produtos produtos = new Produtos(
                        rs.getInt("id"),rs.getString("nome"),rs.getInt("quantidade"),
                        rs.getString("preco"),rs.getString("descricao")
                );

                lista_de_produtos.add(produtos);
            }while(rs.next());

        });


        var index = lista_de_produtos.size();
        String[] id = new String[index];
        String[] quantidade = new String[index];
        String[] nome = new String[index];
        String[] preco = new String[index];
        String[] descricao = new String[index];
        String id_li = "",nome_li = "",qtd_li = "",preco_li = "",descricao_li = "";


        for(int i = 0; i<index; i++){

            id[i] = String.valueOf(lista_de_produtos.get(i).getId());
            quantidade[i] = String.valueOf(lista_de_produtos.get(i).getQuantidade());
            nome[i]= lista_de_produtos.get(i).getNome();
            preco[i] = lista_de_produtos.get(i).getPreco();
            descricao[i] = lista_de_produtos.get(i).getDescricao();

            id[i] = "<a href = \"/carrinho?id="+id[i]+"&nome="+nome[i]
                    +"&desc="+descricao[i]+"&qtd="
                    +quantidade[i]+"&preco="+preco[i]+"\">adicionar </a>";

            quantidade[i] = "<li>"+quantidade[i]+"</li>";
            nome[i] = "<li>"+nome[i]+"</li>";
            preco[i] = "<li>"+preco[i]+"</li>";
            descricao[i] = "<li>"+descricao[i]+"</li>";
        }



        for(int i = 0; i<index; i++){
            id_li += id[i]+ "\n";
            qtd_li += quantidade[i] + "\n";
            nome_li += nome[i] + "\n";
            preco_li += preco[i] + "\n";
            descricao_li += descricao[i] + "\n";

        }



        var html = "<body>\n" +
                "    <title>Lista de produtos</title>\n" +
                "    <h1>Produtos</h1>\n" +
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

        write.println(html);
        write.close();
    }
}
