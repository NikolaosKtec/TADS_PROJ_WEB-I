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
    public void add_carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Produtos p = new Produtos ();
        Cookie[] requestCookies = request.getCookies();


        String sql = "SELECT id FROM Produtos WHERE id = "+request.getParameter("id");

        if(session != null){
            jdbcTemplate.query(sql ,(ResultSet rs) -> {
                //procura pelo id passado o produto

                p.setId(rs.getInt("id"));
                //lista_carrinho.add(p);
            });

            Cookie carrinho = new Cookie("carrinho","");
            carrinho.setMaxAge(60*60*24*7);


            if(requestCookies != null){
                for(var item: requestCookies){
                    if(item.getName().equals("carrinho")){
                        var value = item.getValue();
                        carrinho.setValue(value+p.getId()+"|");
                    }
                }
            }else{
                carrinho.setValue(p.getId()+"|");
            }

            response.addCookie(carrinho);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/carrinho");
            dispatcher.forward(request, response);

        }else
            response.sendRedirect("/index.html");
    }
}
