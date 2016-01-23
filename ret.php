<?php
/* retrieval of data using stored procedure. There's some issue in this, needs to be worked on*/
require("sql_con.php");
//$id = $_GET["id"];
$id = 2;

$response = array();

$res = mysql_query("CALL rett($id, @p_name,@p_address,@p_phone,@p_email,@p_date);");
echo $res;
while($row = (mysql_fetch_assoc($res)))
{
print_r($row);
echo "<br />";
}
echo "<br />";
$rs2 = mysql_query("select 
 @p_name as p_name,
 @p_address as p_address,
 @p_phone as p_phone,
 @p_email as p_email,
 @p_date as p_date;");
echo $rs2;
$total = mysql_fetch_assoc($rs2);
print_r($total);
?>