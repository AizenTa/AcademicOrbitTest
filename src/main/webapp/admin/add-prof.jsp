<!DOCTYPE html>
<html>
<head> 
    <title>Ajouter un Professeur</title>
</head>
<body>
    <h1>Ajouter un Professeur</h1> 
    <form action="add-prof.jsp" method="post">
        <label>Username:</label>
        <input type="text" name="username" required><br>
        <label>Password:</label>
        <input type="password" name="password" required><br>
        <label>Name:</label>
        <input type="text" name="name" required><br>
        <label>Last Name:</label>
        <input type="text" name="last_name" required><br>
        <label>Address:</label>
        <input type="text" name="address" required><br>
        <label>Sex:</label>
        <input type="text" name="sex" required><br>
        <label>Age:</label>
        <input type="number" name="age" required><br>
        <label>CNE Prof:</label>
        <input type="text" name="cne_prof" required><br>
        <button type="submit">Add</button>
    </form>
</body>
</html>
