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
<script src="js/typeahead.bundle.min.js"></script>
<script src="js/fixutils.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="vendor/bootstrap-table/bootstrap-table-locale-all.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				<h1>Simple way to fix Cloud Drive file cache problem</h1>
				<p>
					The easiest way to sync Cloud Drive and OSS file cache<br>
				</p>
			</div>
		</div>
		<div class="input-group">
			<span class="input-group-addon">Please Input UserName:</span>
        <div id="users" class="input-group-addon">
          <input class="typeahead" type="text" placeholder="User...">
        </div>
			<span class="input-group-addon">
				<button type="button" class="btn btn-primary">Check now</button>
			</span>
		</div>
	</div>
	<div class="container">
		<div id="toolbar">
			<button id="remove" class="btn btn-danger" disabled>
				<i class="glyphicon glyphicon-remove"></i> Delete
			</button>
		</div>
		<table id="table" data-toolbar="#toolbar" data-search="true"
			data-show-refresh="true" data-show-export="true"
			data-pagination="true" data-id-field="fileid"
			data-page-list="[10, 25, 50, 100, ALL]" data-show-footer="false"
			data-side-pagination="server">
		</table>
	</div>
	<script>
    var $table = $('#table'),
        $remove = $('#remove'),
        selections = [];
    function initTable() {
        $table.bootstrapTable({
        	  height: getHeight(),
            columns: [{
                        field: 'state',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        title: 'fileid',
                        field: 'id',
                        align: 'center',
                        valign: 'middle',
                        width: '5%',
                        sortable: true
                    }, {
                        title: 'name',
                        align: 'center',
                        valign: 'middle',
                        width: '20%',
                        sortable: true
                    }, {
                    	  title: 'path',
                        align: 'center',
                    	  valign: 'middle',
                    	  width: '45%'
                    }, {
                    	  title: 'mimetype',
                        align: 'center',
                    	  valign: 'middle',
                    	  width: '10%'
                    }, {
                    	  title: 'size',
                    	  align: 'center',
                    	  valign: 'middle'
                    }, {
                    	  title: 'action',
                    	  align: 'center',
                    	  valign: 'middle',
                    	  width: '2%'
                    }]
        });
        // sometimes footer render error.
        setTimeout(function () {
            $table.bootstrapTable('resetView');
        }, 200);
        $table.on('check.bs.table uncheck.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
            // save your data, here just save the current page
            selections = getIdSelections();
            // push or splice the selections if you want to save all data selections
        });
        $table.on('all.bs.table', function (e, name, args) {
            console.log(name, args);
        });
        $remove.click(function () {
            var ids = getIdSelections();
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids
            });
            $remove.prop('disabled', true);
        });
        $(window).resize(function () {
            $table.bootstrapTable('resetView', {
                height: getHeight()
            });
        });
    }
    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.fileid
        });
    }
    function operateFormatter(value, row, index) {
        return [
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
    window.operateEvents = {
        'click .remove': function (e, value, row, index) {
            $table.bootstrapTable('remove', {
                field: 'fileid',
                values: [row.fileid]
            });
        }
    };
    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
    $(function () {
        initTable();
    });
</script>
</body>
</html>