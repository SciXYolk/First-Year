<?php
   // Connect to database, and print error message if it fails
   try {
      $dbhandle = new PDO('','', ''); // edited out to not reveal private information
   } 
   catch (PDOException $e) {
      // The PDO constructor throws an exception if it fails
      die('Error Connecting to Database: ' . $e->getMessage());
   }

   // Run the SQL query, and print error message if it fails.
   $sql = "SELECT * FROM User";

   $query = $dbhandle->prepare($sql);

   if ( $query->execute() === FALSE ) {
      die('Error Running Query: ' . implode($query->errorInfo(),' ')); 
   }

   // Put the results into a nice big associative array
   $results = $query->fetchAll();

   // Printing out the course details in the array results
?>
<?php
$user_options = "";
   foreach ($results as $row) {
      $id = $row["uid"];
      $name = $row["forename"]. " " . $row["surname"];
      $isStudent = ($row["student"] == "Y") ? "Student (Y)" : "Student (N)";
      $user_options .= "<option value = '$id'>$name ($id) - $isStudent</option>";
   }
?>

<!DOCTYPE html>
<html>
<head>
	<title>Task 6 - User Details</title>
</head>
<body>
	<h2>User Details</h2>
	<form method="get" action="task7.php">
		<label for="user_id">Select a user:</label>
		<select name="user_id" id="user_id">
			<?php echo $user_options; ?>
		</select>
		<br><br>
		<input type="submit" value="Submit">
	</form>
</body>
</html>
