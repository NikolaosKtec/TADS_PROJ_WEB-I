package controller;

import model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

}
