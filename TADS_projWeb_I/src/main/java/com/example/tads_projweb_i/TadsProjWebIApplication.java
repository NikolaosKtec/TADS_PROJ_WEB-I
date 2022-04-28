package com.example.tads_projweb_i;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@RequestMapping
public class TadsProjWebIApplication {

    public static void main(String[] args) {
        SpringApplication.run(TadsProjWebIApplication.class, args);
    }

    @GetMapping(value = "/hello")
    public void getHello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        var writer =  response.getWriter();
        writer.println("<div>hello word!</div>");
        writer.close();
    }

}
