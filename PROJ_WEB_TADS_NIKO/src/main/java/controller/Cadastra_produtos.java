package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class Cadastra_produtos {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/cadastrar_produtos")
    public void cadastrar_prod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);

        var produto = request.getParameter("produto");
        var id = request.getParameter("id");
        var quantidade= request.getParameter("quantidade");
        var preco = request.getParameter("preco");
        var descricao = request.getParameter("descricao");
        var write = response.getWriter();

        boolean e_logado = false;
        boolean e_logista = false;


        try{
            e_logado = session.getAttribute("e_logado").equals(true);
            e_logista  = session.getAttribute("e_logista").equals(true);
        }catch(NullPointerException e){

        }
        if(e_logado && e_logista) {

            String sql = " INSERT INTO Produtos(id,nome,quantidade,preco,descricao) values";
            sql += "("+id+",'"+produto+"',"+quantidade+","+preco+",'"+descricao+"')";

//            try{
                jdbcTemplate.update(sql);
//                write.println("Inserção foi um sucesso!");
//            }catch (BadSqlGrammarException e){
//
//                write.println(e.getMessage());
//            }//BadSqlGrammarException
            response.sendRedirect("/produtos_logista");
        }else{
            response.sendRedirect("/index.html");
        }

    }
}
