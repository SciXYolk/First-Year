<?php
   // Connect to database, and print error message if it fails
   try {
      $dbhandle = new PDO('','', ''); //edited out to not reveal private information
   } 
   catch (PDOException $e) {
      // The PDO constructor throws an exception if it fails
      die('Error Connecting to Database: ' . $e->getMessage());
   }

   // Run the SQL query, and print error message if it fails.
   $sql = "SELECT DISTINCT course, instructor FROM Class c JOIN Timeslot t ON c.cid = t.cid ORDER BY instructor";

   $query = $dbhandle->prepare($sql);

   if ( $query->execute() === FALSE ) {
      die('Error Running Query: ' . implode($query->errorInfo(),' ')); 
   }

   // Put the results into a nice big associative array
   $results = $query->fetchAll();

   // Printing out the course details in the array results
?>
<head>
   <title>Task 5</title>
   <style>
		table {
			border-collapse: collapse;
			width: 100%;
			border: 1px solid black; 
		}
		th, td {
			padding: 8px;
			text-align: left;
			border-bottom: 1px solid #ddd;
         border-right: 1px solid #ddd;
		}
		th {
			background-color: #4CAF50;
			color: white;
		}
		td {
			padding-top: 12px;
			padding-bottom: 12px;
		}
	</style>
</head>
<body>
   <h2>Details of all courses</h2>
   <table>
      <tr><th>ID</th><th>Name</th></tr>

<?php
   foreach ($results as $row) {
      echo "<tr><td>".$row['course']."</td><td>".$row['instructor']."</td></tr>";
   }
?>
</table>
</body>
