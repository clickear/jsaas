<%-- 
    Document   : sysFileDialogDemo
    Created on : 2015-4-27, 16:56:48
    Author     : X230
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<title>Plupload - jQuery UI Widget</title>
<%@include file="/commons/dynamic.jspf"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/ui/css/cupertino/jquery-ui-1.10.4.custom.min.css" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/upload/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />

<script src="${ctxPath}/scripts/jquery//ui/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/upload/i18n/zh_CN.js"></script>
<!-- production -->
<script type="text/javascript" src="${ctxPath}/scripts/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/upload/jquery.ui.plupload/jquery.ui.plupload.js"></script>


</head>
<body style="font: 13px Verdana; background: #eee; color: #333">

<h1>jQuery UI Widget</h1>

<form id="form" method="post" action="../dump.php">
	<div id="uploader">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
	<br />
	<input type="submit" value="Submit" />
</form>

<script type="text/javascript">
// Initialize the widget when the DOM is ready
$(function() {
	$("#uploader").plupload({
		// General settings
		runtimes : 'html5,flash,silverlight,html4',
		url : '../upload.php',

		// User can upload no more then 20 files in one go (sets multiple_queues to false)
		max_file_count: 20,
		
		chunk_size: '1mb',

		/* Resize images on clientside if we can
		resize : {
			width : 200, 
			height : 200, 
			quality : 95,
			crop: true // crop to exact dimensions
		},*/
		
		filters : {
			// Maximum file size
			max_file_size : '1000mb',
			// Specify what files to browse for
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"}
			]
		},

		// Rename files by clicking on their titles
		rename: true,
		
		// Sort files
		sortable: true,

		// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
		dragdrop: true,

		// Views to activate
		views: {
			list: true,
			/*thumbs: true
                         // Show thumbs
			active: 'thumbs'*/
		},

		// Flash settings
		flash_swf_url : '../../js/Moxie.swf',

		// Silverlight settings
		silverlight_xap_url : '../../js/Moxie.xap'
	});


	// Handle the case when form was submitted before uploading has finished
	$('#form').submit(function(e) {
		// Files in queue upload them first
		if ($('#uploader').plupload('getFiles').length > 0) {

			// When all files are uploaded submit form
			$('#uploader').on('complete', function() {
				$('#form')[0].submit();
			});

			$('#uploader').plupload('start');
		} else {
			alert("You must have at least one file in the queue.");
		}
		return false; // Keep the form from submitting
	});
});
</script>
</body>
</html>
