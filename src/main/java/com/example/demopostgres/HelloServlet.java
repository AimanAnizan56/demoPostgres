package com.example.demopostgres;

import com.example.demopostgres.connection.Postgres;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<String> name = new ArrayList <String>();

        try {
            Connection conn = Postgres.getConnection();

            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM person");
            
            while(rs.next()) {
                id.add(rs.getInt(1));
                name.add(rs.getString(2));
            }

            rs.close();
        } catch (SQLException sqlErr) {
            sqlErr.printStackTrace();
        } finally {
            Postgres.closeConnection();
        }
        
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        final int SIZE = id.size();

        out.println("<h1>" + message + "</h1>");

        for(int i = 0; i < SIZE; i++) {
            out.println("<div>");
            out.println("<span>" + id.get(i) + "</span>");
            out.println("<span>" + name.get(i) + "</span>");
            out.println("</div>");
        }


        
        out.println("</body></html>");
    }

    public void destroy() {
    }
}