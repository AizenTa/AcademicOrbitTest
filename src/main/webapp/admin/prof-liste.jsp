<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="DAO.AdminDAO" %>
<%@ page import="business.Professors" %>
<%
    // Database connection setup
    String url = "jdbc:mysql://localhost:3306/gradebook";
    String user = "root";
    String password = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    List<Professors> professors = new ArrayList<>();

    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        AdminDAO dao = new AdminDAO(conn);
        professors = dao.getAllProfessors();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Professeurs</title>
</head>
<body>
    <h1>Liste des Professeurs</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Name</th>
            <th>Last Name</th>
            <th>Address</th>
            <th>Sex</th>
            <th>Age</th>
            <th>CNE Prof</th>
            <th>Actions</th>
        </tr>
        <%
            for (Professors prof : professors) {
        %>
        <tr>
            <td><%= prof.getId() %></td>
            <td><%= prof.getUsername() %></td>
            <td><%= prof.getName() %></td>
            <td><%= prof.getLast_name() %></td>
            <td><%= prof.getAddress() %></td>
            <td><%= prof.getSex() %></td>
            <td><%= prof.getAge() %></td>
            <td><%= prof.getCne_prof() %></td>
            <td>
                <a href="edit-prof.jsp?id=<%= prof.getId() %>">Edit</a> 
                <a href="delete-prof?id=<%= prof.getId() %>">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="add-prof.jsp">Add New Professor</a>
</body>
</html>
