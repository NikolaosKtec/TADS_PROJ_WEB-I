package controller;

import model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @PostMapping(value="/login")
    public void response(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Pessoa ps = new Pessoa();


        var email = request.getParameter("email");
        var senha = request.getParameter("senha");

        final String sql = "SELECT * FROM Pessoa WHERE email= "+"'"+email+"'";

        jdbcTemplate.query(sql, (ResultSet rs) -> {

            ps.setNome(rs.getString("nome"));
            ps.setEmail(rs.getString("email"));
            ps.setSenha(rs.getString("senha"));
            ps.setElojista(rs.getBoolean("e_lojista"));
        });

        int val_usu = 0;

        if(ps.getEmail() != null){
//valida usuario e salva a seção
            if(ps.getSenha().equals(senha)){
                 HttpSession session = request.getSession();
                    session.setAttribute("e_logado",true);
                    session.setAttribute("e_logista",ps.getElojista());
                val_usu = 1;
            }
        }else val_usu = -1;

        switch (val_usu){
            case 1:
                if(ps.getElojista()){//verifica se é logista ou cliente

                    response.sendRedirect("/produtos_logista");
                }else{
                    response.sendRedirect("/lista_de_produtos");
                }
                break;
            default: //se não retorna ao login
                response.sendRedirect("/login_response");
        }

    }

    @GetMapping("/login_response")
    public void lg_rs(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        var write = response.getWriter();
        write.println("<h1>usuario ou senha incorretos!</h1>");
        write.println("<a href = \"/index.html\">Página login</a>");
    }

}
