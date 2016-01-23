<?php
//connect to localhost
$con=mysqli_connect("localhost","root","","awesome_labs");
date_default_timezone_set('Asia/kolkata');
if(mysqli_connect_errno())
{
	printf("Connection failed %s",mysqli_connect_error());
	exit();
}