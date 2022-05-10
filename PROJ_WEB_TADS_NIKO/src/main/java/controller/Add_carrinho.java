package controller;

import model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;

@Controller
public class Add_carrinho {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/add_carrinho")
    public void add_carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException,
                                                                                        ServletException {

        HttpSession session = request.getSession();

        Produtos p = new Produtos();
        Cookie[] requestCookies = request.getCookies();
        boolean e_logado = false;

        String sql = "SELECT id FROM Produtos WHERE id = "+request.getParameter("id");

        try{
            e_logado = session.getAttribute("e_logado").equals(true);
        }catch(NullPointerException e){

        }

        if(e_logado){
            jdbcTemplate.query(sql ,(ResultSet rs) -> {
                //procura pelo id passado o produto

                p.setId(rs.getInt("id"));
                //lista_carrinho.add(p);
            });

            Cookie carrinho = new Cookie("carrinho","");
            carrinho.setMaxAge(60*60*24*7);
            boolean achouCarrinho = false;

            if(requestCookies != null){
                for(var item: requestCookies){
                    if(item.getName().equals("carrinho")){
                        achouCarrinho = true;
                        carrinho = item;
                        break;
                    }
                }
            }



            String iff = String.valueOf(p.getId());
                 
            if (iff != null) {

                if (achouCarrinho) {
                    String value = carrinho.getValue();
                    carrinho.setValue(value + p.getId() + "|");
                } else {
                    carrinho.setValue(p.getId() + "|");
                }
            }else {
                response.addCookie(carrinho);
                response.getWriter().println("Id inexistente");
            }

            response.addCookie(carrinho);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/lista_de_produtos");
            dispatcher.forward(request, response);

        }else
            response.sendRedirect("/index.html");
    }
}
