var users;
$(document).ready(function() {
	initUsers();
	$("#check").click(function() {
		var user = $("#input").val();
		if (user === "") {
			$("#input").notify("please input user", "error");
		}
		var jsonObj = JSON.parse(users);
		var found = false;
		for (var i = 0; i < jsonObj.length; i++) {
			if (jsonObj[i].uid === user) {
				var storage = jsonObj[i].storage;
				getFiles(storage);
				found = true;
				break;
			}
		}
		if (!found) {
			$("#input").notify("user "+user+" not found", "error");
		}
	});
});

function getBasePath() {
	var location = (window.location + '').split('/');
	var basePath = location[0] + '//' + location[2] + '/' + location[3];
	return basePath;
}

function initUsers() {
	var url = getBasePath() + "/getusers.do";
	$.ajax({
		url : url,
		type : 'post',
		success : function(data) {
			users = JSON.stringify(data);
			var userMatcher = function(jsonStr) {
				return function findMatches(q, cb) {
					var matches = [];
					var substrRegex = new RegExp(q, 'i');
					var jsonObj = JSON.parse(jsonStr);
					$.each(jsonObj, function(i, json) {
						if (substrRegex.test(json.uid)) {
							matches.push(json.uid);
						}
					});
					cb(matches);
				};
			};

			$('#input').typeahead({
				hint : true,
				highlight : true,
				minLength : 1
			}, {
				name : 'users',
				source : userMatcher(users)
			});
		},
		error : function() {
			alert('get users from mysql error ...');
		}
	});
}

function getFiles(storage) {
	var url = getBasePath() + "/getfiles.do/" + storage;
	$.ajax({
		url : url,
		type : 'post',
		success : function(data) {
			initTable(data);
		},
		error : function() {
			alert('get files from mysql error ...');
		}
	});
}

function initTable(files) {
	var $files = $('#files');
	$files.show();
	var $table = $('#table'), $remove = $('#remove'), selections = [];
	$table.bootstrapTable('destroy').bootstrapTable({
		height : getHeight(),
		exportDataType : "all",
		rowStyle : function(row, index) {
			var strclass = '';
			if (row.ifExists == false) {
				strclass = '';
			} else if (row.ifExists == true) {
				strclass = '';
			} else {
				return {};
			}
			return {
				classes : strclass
			}
		},
		columns : [ {
			field : 'state',
			checkbox : true,
			align : 'center',
			valign : 'middle'
		}, {
			title : 'fileid',
			field : 'fileId',
			align : 'center',
			valign : 'middle',
			width : '5%',
			sortable : true
		}, {
			title : 'name',
			field : 'name',
			align : 'left',
			valign : 'middle',
			width : '20%',
			sortable : true
		}, {
			title : 'path',
			field : 'path',
			align : 'left',
			valign : 'middle',
			width : '45%'
		}, {
			title : 'mimetype',
			field : 'mimeTypeName',
			align : 'left',
			valign : 'middle',
			width : '10%'
		}, {
			title : 'size',
			field : 'size',
			align : 'center',
			valign : 'middle'
		}, {
			title : 'action',
			align : 'center',
			valign : 'middle',
			width : '2%',
			events : operateEvents,
			formatter : operateFormatter
		} ],
		data : files
	});
	// sometimes footer render error.
	setTimeout(function() {
		$table.bootstrapTable('resetView');
	}, 200);
	$table.on('check.bs.table uncheck.bs.table '
			+ 'check-all.bs.table uncheck-all.bs.table', function() {
		$remove
				.prop('disabled',
						!$table.bootstrapTable('getSelections').length);
		selections = getIdSelections();
	});
	$table.on('all.bs.table', function(e, name, args) {
		console.log(name, args);
	});
	$table.on('refresh.bs.table', function(params) {
		$('#check').click();
	});
	$remove.click(function() {
		var ids = getIdSelections();
		$table.bootstrapTable('remove', {
			field : 'fileId',
			values : ids
		});
		removeFiles(ids);
		$remove.prop('disabled', true);
	});
	$(window).resize(function() {
		$table.bootstrapTable('resetView', {
			height : getHeight()
		});
	});
}

function getIdSelections() {
	var $table = $('#table');
	return $.map($table.bootstrapTable('getSelections'), function(row) {
		return row.fileId
	});
}

function operateFormatter(value, row, index) {
	return [ '<a class="remove" href="javascript:void(0)" title="Remove">',
			'<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
}

function getHeight() {
	return $(window).height() - $('h1').outerHeight(true);
}

function removeFiles(ids) {
	alert(ids);
}

window.operateEvents = {
	'click .remove' : function(e, value, row, index) {
		var $table = $('#table');
		$table.bootstrapTable('remove', {
			field : 'fileId',
			values : [ row.fileId ]
		});
		removeFiles(row.fileId);
	}
};