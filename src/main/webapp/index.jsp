<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cloud Drive Fix Tool</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="vendor/bootstrap-table/bootstrap-table.min.css">
<link rel="stylesheet" type="text/css" href="css/fixutils.css">
<script src="js/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="vendor/bootstrap-table/locale/bootstrap-table-en-US.min.js"></script>
<script src="vendor/bootstrap-table/extensions/export/bootstrap-table-export.min.js"></script>
<script src="js/notify.min.js"></script>
<script src="js/typeahead.bundle.min.js"></script>
<script src="js/tableExport.js"></script>
<script src="js/fixutils.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				<h1>Simple way to fix Cloud Drive file cache problem</h1>
				<h4>
					The easiest way to sync Cloud Drive and OSS file cache<br>
				</h4>
			</div>
		</div>
		<div class="row">
		<div class="col-md-3 col-sm3"></div>
		<div class="col-md-6 col-sm6">
		  <div class="input-group">
        <div class="input-group-addon">
          <input id="input" class="typeahead" type="text" placeholder="User...">
        </div>
        <span class="input-group-addon">
				  <button id="check" type="button" class="btn btn-primary">Check now</button>
        </span>
		  </div>
		</div>
		<div class="col-md-3 col-sm3"></div>
	</div>
	<div id="files" class="container" style="display:none">
		<div id="toolbar">
			<button id="remove" class="btn btn-danger" disabled>
				<i class="glyphicon glyphicon-remove"></i> Delete
			</button>
		</div>
		<table id="table" data-toolbar="#toolbar" data-search="true"
			data-show-refresh="true" data-show-export="true"
			data-pagination="true" data-id-field="fileid"
			data-page-list="[10, 25, 50, 100, ALL]" data-show-footer="false"
			data-side-pagination="client">
		</table>
	</div>
</body>
</html>