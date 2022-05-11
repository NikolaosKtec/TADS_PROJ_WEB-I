package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Cadastro {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @PostMapping("/cadastro")
    public void cadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {

    var nome = request.getParameter("nome");
    var email = request.getParameter("email");
    var senha =  request.getParameter("senha");

        String sql = "INSERT INTO Pessoa(nome,email,senha) VALUES ";
        sql += "('"+nome+"','"+email+"','"+senha+"')";

        jdbcTemplate.update(sql);

        response.sendRedirect("/index.html");



    }
}
