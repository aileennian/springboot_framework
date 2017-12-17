<#include "/common/macro.ftl" >
<@doctype/>
<html>
<head>
	<@header/>
	<title><@titlePrefix/>-Upload</title>
</head>
<body>
	<form id="submitForm" enctype="multipart/form-data">
		<input type="file" name="file"> <br>
		<input type="file" name="file"> <br>
		<input type="file" name="file"> <br>
		<input type="file" name="file"> <br>
		<button type="button" id="submitBtn">提交</button>
	</form>
</body>
<@commonScript />
<script type="text/javascript" src="${urls.getForLookupPath('/js/home/upload.js') }"></script>
</html>