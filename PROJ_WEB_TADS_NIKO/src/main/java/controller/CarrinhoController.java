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

import java.util.List;
import java.util.StringTokenizer;

@Controller
public class CarrinhoController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/carrinho")
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("text/html");
        Produtos p = new Produtos();

        HttpSession session = request.getSession();
        boolean e_logado = false;

        try{
            e_logado = session.getAttribute("e_logado").equals(true);
        }catch(NullPointerException e){

        }

        if(e_logado){
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
            if (requestCookies != null) {
                for (var item: requestCookies){
                    if(item.getName().equals("carrinho")){
                        achou = true;
                        carrinho = item;
                        break;
                    }
                }
            }


            if(achou) {
                StringTokenizer tokenizer = new StringTokenizer(carrinho.getValue(), "|");
                ArrayList<Produtos> lista_carrinho = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
//                    List<Produtos> produtos = produtoDao.listarProdutosPorId(Integer.parseInt(tokenizer.nextToken()));

                    jdbcTemplate.query(sql + (tokenizer.nextToken()), (ResultSet rs) -> {
                        //procura pelo id passado o produto
                        p.setNome(rs.getString("nome"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setPreco(rs.getString("preco"));
                        p.setQuantidade(rs.getInt("quantidade"));
                        p.setId(rs.getInt("id"));

                        lista_carrinho.add(p);

                    });

                    write.println("<tr>");
                    write.println("<td>");
                    write.println(lista_carrinho.get(0).getNome());
                    write.println("</td>");
                    write.println("<td>");
                    write.println(lista_carrinho.get(0).getDescricao());
                    write.println("</td>");
                    write.println("<td>");
                    write.println(lista_carrinho.get(0).getPreco());
                    write.println("</td>");
                    write.println("<td>");
                    write.println(lista_carrinho.get(0).getQuantidade());
                    write.println("</td>");
                    write.println("<td>");
                    write.println("<a href='/removerCarrinho?id="+lista_carrinho.get(0).getId()+"'>Remover</a>");
                    write.println("</td>");
                    write.println("</tr>");
                }

                write.println("</table> <a href=\"/lista_de_produtos\">ver produtos</a>");
                write.println("</body></html>");
            }else{
                write.println("carrinho Vazio!");
            }
        }else{
            response.sendRedirect("/index.html");
        }
    }
}
