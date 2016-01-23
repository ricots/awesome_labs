<?php
require("sql_con.php");

$id = $_GET["id"];
//$id = 2;
//echo $id;
$response = array();
$query = "SELECT * FROM `tuserdetails` WHERE `ID`=$id";
$q=mysqli_query($con,$query);
	if(!empty($q))
	{
		if (mysqli_num_rows($q) > 0)
		{
			//$result = mysqli_fetch_array($q);
			$pid = array();
			while($result=mysqli_fetch_row($q))
			{
			
			$pid[]=$result;
			
			$response["pid"] = array();
			array_push($response["pid"], $pid);
			}

			json_encode($response);
		}
		else
		{
			$response["message"]="ID does not exist";
			json_encode($response);
		}
	}
	
?>