<?php

$username = "b3b9f5eb79a73e7059d49056e1a4bc40";
$password = "Password1!";
//$remote_url = 'https://cazoomi1.highrisehq.com/tags.xml';
$urlPeople="https://cazoomi1.highrisehq.com/parties.xml";

// Create a stream
$opts = array(
  'http'=>array(
    'method'=>"GET",
    'header' => "Authorization: Basic " . base64_encode("$username:$password")
             
  )
);

$context = stream_context_create($opts);

// Open the file using the HTTP headers set above
$filePeople = file_get_contents($urlPeople, false, $context);
$xmlPeople=simplexml_load_string($filePeople);


//print ($xml->tags->tag[0]->name);

echo "<br><br><table border=1 style='width:50%''>
  <tr>
  <th color='yellow'>ID</th>
    <th>Firstname</th>
    <th>Lastname</th> 
    <th>Tags</th> 
    </tr>";
    if($_GET['btnSubmit']='Search Contacts') 
{
	$x=$_GET['cmbTags'];
	echo "<h2> List of People </h2>";
	foreach ($xmlPeople->party as $item)
{
	if ($x==($item->tags->tag->id))
	echo "<tr><td>".($item->id);
		echo "<td>".($item->{'first-name'});
		echo "<td>".($item->{'last-name'});
		echo "<td>".($item->tags->tag->name)."</td></tr>";
break;

}
	
}
echo "</table>";

?>
