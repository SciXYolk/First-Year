<?php

   // Connect to database, and print error message if it fails
   try {
      $dbhandle = new PDO('','', ''); //edited out to not reveal private information
   } 
   catch (PDOException $e) {
      // The PDO constructor throws an exception if it fails
      die('Error Connecting to Database: ' . $e->getMessage());
   }

   $user_id = $_GET["user_id"];

   $userDetails = "SELECT * FROM User WHERE User.uid = '$user_id'";
   $userQQuery = $dbhandle->prepare($userDetails);
   if ( $userQQuery->execute() === FALSE ) {
       die('Error Running Query: ' . implode(' ', $userQQuery->errorInfo())); 
   }
   $userResults = $userQQuery->fetchAll();

   // Run the SQL query, and print error message if it fails.
   $sql = "SELECT c.cid, course, instructor, start_time, reservation
         FROM Booking b 
         JOIN Timeslot t ON b.tid = t.tid 
         JOIN Class c ON t.cid = c.cid
         WHERE b.uid = '$user_id' ";
         

   $query = $dbhandle->prepare($sql);

   if ( $query->execute() === FALSE ) {
        die('Error Running Query: ' . implode(' ', $query->errorInfo())); 
    }

   // Put the results into a nice big associative array
   $results = $query->fetchAll();

   // Printing out the course details in the array results
   $sql1 = "SELECT cid, SUM(reservation) AS Final
            FROM Booking b JOIN Timeslot t ON b.tid = t.tid
            JOIN User u ON u.uid = b.uid
            WHERE u.uid = '$user_id'
            GROUP BY cid";
    $query2 = $dbhandle->prepare($sql1);
    if ( $query2->execute() === FALSE ) {
        die('Error Running Query: ' . implode(' ', $query2->errorInfo())); 
    }
    $results1 = $query2->fetchAll();
    $sql2 = "SELECT student FROM User WHERE uid = '$user_id'";
    $stmt2 = $dbhandle->prepare($sql2);
    if ( $stmt2->execute() === FALSE ) {
        die('Error Running Query: ' . implode(' ', $stmt2->errorInfo())); 
    }
    $user_type = $stmt2->fetchColumn();
     
?>
<?php
    echo "<head>";
    echo "<title>Task 7</title>";
    echo "<link rel='stylesheet' type='text/css' href='styles.css'>";
    echo "</head>";
    echo "<body>";
    echo "<h2>User Detail</h2>";
    echo "<table>";
    echo "<tr><th>uid</th><th>Forename</th><th>Surname</th><th>Student</th>";
    foreach ($userResults as $row3){
        echo "<tr><td>" . $row3['uid'] . "</td><td>" . $row3['forename'] . "</td><td>" . $row3['surname'] . "</td><td>" . $row3['student'] . "</td></tr>";
    }
    echo "<table>";
    echo "<h2>Details for User $user_id</h2>";
    echo "<tr><th>Class ID</th><th>Course</th><th>Instructor</th><th>Start Time</th><th>Reservation</th></tr>";
    foreach ($results as $row) {
        echo "<tr><td>" . $row['cid'] . "</td><td>" . $row['course'] . "</td><td>" . $row['instructor'] . "</td><td>" . $row['start_time'] . "</td><td>" . $row['reservation'] . "</td></tr>";
    }
    if($user_type == 'Y'){
    echo "</table>";
        echo "<h2>Class Reservation Details</h2>";
        echo "<table>";
        echo "<tr><th>Class ID</th><th>Total Reservations</th></tr>";    
        foreach ($results1 as $row1) {
            echo "<tr><td>" . $row1['cid'] . "</td><td>" . $row1['Final'] . "</td></tr>";
        }
        echo "</table>";
    }
    echo "</body>";
?>
