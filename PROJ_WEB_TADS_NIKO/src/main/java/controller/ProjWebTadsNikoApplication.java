package controller;

import model.Pessoa;


import model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;



@RequestMapping
@SpringBootApplication
public class ProjWebTadsNikoApplication {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProjWebTadsNikoApplication.class, args);
    }

    @GetMapping(value = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("  <form action=\"/triagem\" method=\"get\"> <label for=\"email\">email</label> ");
        writer.println(" <input name=\"email\" type=\"email\" required><label for=\"senha\">senha</label> ");
        writer.println(" <input name=\"senha\" type=\"password\" required>");
        writer.println(" <button type=\"submit\">enviar</button></form>");

        writer.close();

    }
    @GetMapping(value="/triagem")
    public void response(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Pessoa ps = new Pessoa();

        var writer = response.getWriter();
        var senha = request.getParameter("senha");
        var email = request.getParameter("email");

        final String sql = "SELECT * FROM Pessoa WHERE email= "+"'"+email+"'";

        jdbcTemplate.query(sql, (ResultSet rs) -> {

                ps.setNome(rs.getString("nome"));
                ps.setEmail(rs.getString("email"));
                ps.setSenha(rs.getString("senha"));
                ps.setElojista(rs.getBoolean("e_lojista"));
            });


        var nome = ps.getNome();

        if(ps.getEmail() == null){
             writer.println("<div><p> usuário não tem cadastro!</p>");
         }else if(ps.getSenha().equals(senha)){
             writer.println("<div><p> Bem vindo!"+nome+"</p></div>");
             if(!ps.getElojista())
                response.sendRedirect("/lista_de_produtos");
         }else{
             writer.println("<div><p>Senha ou email incorretos!</p></div>");
         }
        writer.close();
    }

    @GetMapping(value = "/lista_de_produtos")
    public void pagina_do_cliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

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
        String id = "";
        String quantidade = "";
        String nome = "";
        String preco = "";
        String descricao = "";
        String aux1,aux2,aux3,aux4,aux5;

        for(int i = 0; i<index; i++){
            aux1 = String.valueOf(lista_de_produtos.get(i).getId());
            aux2 = String.valueOf(lista_de_produtos.get(i).getQuantidade());
            aux3= lista_de_produtos.get(i).getNome();
            aux4 = lista_de_produtos.get(i).getPreco();
            aux5 = lista_de_produtos.get(i).getDescricao();

            id += "..........." +aux1;
            quantidade += "..........."+aux2;
            nome += "..........."+aux3;
            preco += "..........."+aux4;
            descricao += "..........."+aux5;
        }



        var html = "<body>\n" +
                "    <title>Lista de produtos</title>\n" +
                "    <h1>Produtos</h1>\n" +
                "    <div style=\" grid-template-columns: repeat(5,15vw);\">\n" +
                "        <div  style=\" grid-column-start: 1;\">Nome\n" +
                "        <li> caneta azul"+nome+"</li>\n" +
                "        </div>\n" +
                "        <div style=\" grid-column-start: 2;\">Descrição\n" +
                "        <l1>uma caneta azul"+descricao+"</l1>\n" +
                "        </div>\n" +
                "        <div  style=\" grid-column-start: 3;\">Quantidade\n" +
                "        <li>100"+quantidade+"</li>\n" +
                "        </div>\n" +
                "        <div  style=\" grid-column-start: 4;\">Preço\n" +
                "        <li>1"+preco+"</li>\n" +
                "        </div>\n" +
                "        <div style=\" grid-column-start: 5;\">Adicionar\n" +
                "            <a href=\"#\">add</a>\n" +
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
                "</body>";

        write.println(html);
        write.close();
    }

}
