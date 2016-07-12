# cazoomi-test-01

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Cazoomi Test</title>
</head>

<body>
<form action ='viewPersons.php'method='get'>
<?PHP

$username = "b3b9f5eb79a73e7059d49056e1a4bc40";
$password = "Password1!";
$remote_url = 'https://cazoomi1.highrisehq.com/tags.xml';
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
$file = file_get_contents($remote_url, false, $context);
$xml=simplexml_load_string($file);

echo "<h2>Cazomi Test 001</h2>Select Tags:<select name='cmbTags'>";
foreach ($xml->tag as $item)
{
	echo "<option value=";
	echo ($item->id).">";
	echo ($item->name)."</option>";

}

echo "</select><input type='Submit' name='btnSubmit' value='Search Contacts'></input>";
?>

</form>


</body>
</html>
