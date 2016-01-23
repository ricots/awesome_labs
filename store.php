<?php

 require("sql_con.php");

 $name = $_POST['n'];
 $add = $_POST['add'];
 $email = $_POST['e'];
 $d = $_POST['d'];
 $phone = $_POST['p'];


 $d = strtotime($d);
 $d = date('Y-m-d',$d);
 //echo $d;

$q = "CALL ak('$name','$add',$phone,'$email','$d')";
//echo $q;
 //$q ="INSERT INTO `tuserdetails` (`Name`,`Address`,`Phone`,`Email`,`Date`) VALUES ('$name','$add',$phone,'$email','$d');";

$res = mysqli_query($con,$q);

$response = array();//array for JSON resposne
 if($res)
	 {	 	
	 	$response["success"] = 1;
        $response["message"] = "successfully inserted";
 
        json_encode($response);
	 }
	 else
	 {
	 	 $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

        json_encode($response);
	 }

?>