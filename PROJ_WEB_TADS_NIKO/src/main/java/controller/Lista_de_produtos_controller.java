package controller;

import model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession(false);

        ArrayList<Produtos> lista_de_produtos = new ArrayList<>();

        var write = response.getWriter();
        final String sql = "SELECT * FROM Produtos";

        if(session != null){

            jdbcTemplate.query(sql, (ResultSet rs) -> {
                do{

                    Produtos produtos = new Produtos(
                            rs.getInt("id"),rs.getString("nome"),rs.getInt("quantidade"),
                            rs.getString("preco"),rs.getString("descricao")
                    );

                    lista_de_produtos.add(produtos);
                }while(rs.next());

            });

            write.println("<html><body>");
            write.println("<table>  <tr>\n" +
                    "    <th>Produto</th>\n" +
                    "    <th>Desc</th>\n" +
                    "    <th>Preco</th>\n" +
                    "    <th>Quantidade</th>\n" +
                    "    <th>Adicionar</th>\n" +
                    "  </tr>");

            for(var item: lista_de_produtos){
                write.println("<tr>");
                write.println("<td>");
                write.println(item.getNome());
                write.println("</td>");
                write.println("<td>");
                write.println(item.getDescricao());
                write.println("</td>");
                write.println("<td>");
                write.println(item.getPreco());
                write.println("</td>");
                write.println("<td>");
                write.println(item.getQuantidade());
                write.println("</td>");
                write.println("<td>");
                write.println("<a href='/carrinho?id="+item.getId()+"'>Adicionar</a>");
                write.println("</td>");
                write.println("</tr>");
            }
            write.println("</table>");
            write.println("</body></html>");
            write.close();
        }else{
            response.sendRedirect("/index.html");
        }

    }
}
