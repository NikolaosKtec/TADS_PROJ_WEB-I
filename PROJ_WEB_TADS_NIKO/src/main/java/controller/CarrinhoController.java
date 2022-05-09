package controller;

import model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Controller
public class CarrinhoController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/carrinho")
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Produtos p = new Produtos();
        ArrayList <Produtos> lista_carrinho = new ArrayList<>();
        HttpSession session = request.getSession(false);

        if(session != null){
            var write = response.getWriter();
            boolean achou = false;
            String sql = "SELECT * FROM Produtos WHERE id = ";

            write.println("<html><body>");
            write.println("<table>  <tr>\n" +
                    "    <th>Produto</th>\n" +
                    "    <th>Desc</th>\n" +
                    "    <th>Preco</th>\n" +
                    "    <th>Quantidade</th>\n" +
                    "    <th>Adicionar</th>\n" +
                    "  </tr>");

            Cookie carrinho = new Cookie("carrinho","");
            carrinho.setMaxAge(60*60*24*7);

            Cookie[] requestCookies = request.getCookies();
            for (var item: requestCookies){
                if(item.getName().equals("carrinho")){
                    achou = true;
                    carrinho = item;
                }
            }
            if(achou) {
                StringTokenizer tokenizer = new StringTokenizer(carrinho.getValue(), "|");
                while (tokenizer.hasMoreTokens()) {
                    sql = sql + (tokenizer.nextToken());

                    jdbcTemplate.query(sql, (ResultSet rs) -> {
                        //procura pelo id passado o produto
                        p.setNome(rs.getString("nome"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setPreco(rs.getString("preco"));
                        p.setQuantidade(rs.getInt("quantidade"));
                        p.setId(rs.getInt("id"));
                    });
                    lista_carrinho.add(p);
                }
                for(var item : lista_carrinho){

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
                    write.println("<a href='/removerCarrinho?id="+item.getId()+"'>Remover</a>");
                    write.println("</td>");
                    write.println("</tr>");

                }
                write.println("</table>");
                write.println("</body></html>");
            }else{
                write.println("carrinho Vazio!");
            }
        }else{
            response.sendRedirect("/index.html");
        }
    }
}
