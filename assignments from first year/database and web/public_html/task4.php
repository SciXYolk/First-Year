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
   $sql = "SELECT instructor, start_time FROM Timeslot t JOIN Class c ON t.cid = c.cid WHERE course = 'Yoga' ORDER BY start_time DESC";

   $query = $dbhandle->prepare($sql);

   if ( $query->execute() === FALSE ) {
      die('Error Running Query: ' . implode($query->errorInfo(),' '));
   }

   // Put the results into a nice big associative array
   $results = $query->fetchAll();

   // Printing out the course details in the array results
?>
   <h2>Details information related to class course Yoga</h2>

<?php
   foreach ($results as $row) {
      echo "<p>".$row['instructor'].": ".$row['start_time']."</p>";
   }
?>
