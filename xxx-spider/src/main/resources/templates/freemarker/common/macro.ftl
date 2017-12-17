<#macro doctype>
	<!DOCTYPE html>
</#macro>
<#macro header>
  <link rel="icon" type="image/x-icon" href="${urls.getForLookupPath('/img/favicon.ico') }">
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <link href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
   <link href="http://cdn.bootcss.com/font-awesome/4.0.0/css/font-awesome.min.css" rel="stylesheet">
   <link href="${urls.getForLookupPath('/css/base/jquery-confirm.min.css') }" rel="stylesheet" type="text/css" />   
   
</#macro>

<#macro commonScript>
	<!--https://code.jquery.com/jquery-3.2.1.min.js-->
	<!--http://www.jq22.com/jquery-info122-->
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="${urls.getForLookupPath('/js/base/jquery-confirm.min.js') }"></script>
	<script type="text/javascript" src="${urls.getForLookupPath('/js/base/jquery-form$[compressflag].js') }"></script>
	<script type="text/javascript" src="${urls.getForLookupPath('/js/base/jquery$[compressflag].js') }"></script>
</#macro>

<#macro footer>  
      <font size="+2">PageFooter!</font>  
</#macro>  

<#macro titlePrefix>
		会小二
</#macro>
