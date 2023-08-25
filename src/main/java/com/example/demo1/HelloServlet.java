package com.example.demo1;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>All Employees</title></head>");
        out.println("<body>");
        out.println("<center><h1>All Employees</h1>");
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection("jdbc:odbc:Employees");
            stmt = conn.createStatement();
            String orderBy = request.getParameter("sort");
            if ((orderBy == null) || orderBy.equals("")) {
                orderBy = "SSN";
            }
            String orderByDir = request.getParameter("sortdir");
            if ((orderByDir == null) || orderByDir.equals("")) {
                orderByDir = "asc";
            }
            String query = "select * from Usuarios ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Cedula:" + rs.getInt("cedula"));
                System.out.println("Nombre:" + rs.getString("nombre"));
                System.out.println("Apellido:" + rs.getString("apellido"));
                System.out.println("Edad:" + rs.getString("edad"));
                System.out.println("Contraseña:" + rs.getString("password"));
            }
        } catch (SQLException e) {
            out.println("An error occured while retrieving " + "all employees: "
                    + e.toString());
        } catch (ClassNotFoundException e) {
            throw (new ServletException(e.toString()));
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }
        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}