var opts = {
  lines: 10 // The number of lines to draw
, length: 20 // The length of each line
, width: 8 // The line thickness
, radius: 20 // The radius of the inner circle
, scale: 1 // Scales overall size of the spinner
, corners: 1 // Corner roundness (0..1)
, color: '#000' // #rgb or #rrggbb or array of colors
, opacity: 0.25 // Opacity of the lines
, rotate: 40 // The rotation offset
, direction: 1 // 1: clockwise, -1: counterclockwise
, speed: 1 // Rounds per second
, trail: 60 // Afterglow percentage
, fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
, zIndex: 2e9 // The z-index (defaults to 2000000000)
, className: 'spinner' // The CSS class to assign to the spinner
, top: '49%' // Top position relative to parent
, left: '50%' // Left position relative to parent
, shadow: false // Whether to render a shadow
, hwaccel: false // Whether to use hardware acceleration
, position: 'absolute' // Element positioning
};
var spinner = new Spinner(opts);
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
				getFiles(user, storage);
				found = true;
				break;
			}
		}
		if (!found) {
			$("#input").notify("user " + user + " not found", "error");
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
		type : "POST",
		beforeSend : function() {
			var target = document.getElementById('loading');
			spinner.spin(target);
		},
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
			spinner.spin();
		},
		error : function() {
			spinner.spin();
			$.notify("get users from mysql error ...", "error");
		}
	});
}

function getFiles(user, storage) {
	var url = getBasePath() + "/getfiles.do/" + user + "/" + storage;
	$.ajax({
		url : url,
		type : "POST",
		beforeSend : function() {
			var target = document.getElementById('loading');
			spinner.spin(target);
		},
		success : function(data) {
			initTable(data);
			spinner.spin();
		},
		error : function() {
			spinner.spin();
			$.notify("get files from mysql error ...", "error");
		}
	});
}

function initTable(files) {
	var $files = $('#files');
	$files.show();
	var $table = $('#table'), $remove = $('#remove');
	$table.bootstrapTable('destroy').bootstrapTable({
		height : getHeight(),
		exportDataType : "all",
		rowStyle : function(row, index) {
			var strclass = '';
			if (row.ifExists == false) {
				strclass = 'danger';
			} else {
				return {};
			}
			return {
				classes : strclass
			}
		},
		columns : [ {
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
	$table.on('check.bs.table', function(event, row) {
		var index = getIndex(row);
		if (row.ifExists === true) {
			$table.bootstrapTable('uncheck', index);
		}
		$remove
				.prop('disabled',
						!$table.bootstrapTable('getSelections').length);
	});
	$table.on('uncheck.bs.table uncheck-all.bs.table', function() {
		$remove
				.prop('disabled',
						!$table.bootstrapTable('getSelections').length);
	})
	$table.on('check-all.bs.table', function(event, rows) {
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].ifExists === true) {
				var index = getIndex(rows[i]);
				$table.bootstrapTable('uncheck', index);
			}
		}
		$remove
				.prop('disabled',
						!$table.bootstrapTable('getSelections').length);
	});
	$table.on('all.bs.table', function(e, name, args) {
		console.log(name, args);
	});
	$table.on('refresh.bs.table', function(params) {
		$('#check').click();
	});
	$remove.click(function() {
		var ids = getIdSelections();
		ids.forEach(function(e) {
			removeFiles(e);
		});
		$table.bootstrapTable('remove', {
			field : 'fileId',
			values : ids
		});
		$remove.prop('disabled', true);
		stopPropagation();
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
		return row.fileId;
	});
}

function operateFormatter(value, row, index) {
	if (row.ifExists === false) {
		return [ '<a class="remove" href="javascript:void(0)" title="Remove">',
				'<i class="glyphicon glyphicon-remove"></i>', '</a>' ].join('');
	}
}

function getHeight() {
	return $(window).height() - $('h1').outerHeight(true);
}

function getIndex(row) {
	var i = 0;
	var $table = $('#table');
	var allData = $table.bootstrapTable('getData');
	for (; i < allData.length; i++) {
		if (allData[i].fileId === row.fileId) {
			break;
		}
	}
	return i;
}

function getFile(fileId) {
	var fileinfo;
	var $table = $('#table');
	var allData = $table.bootstrapTable('getData');
	for (var i = 0; i < allData.length; i++) {
		if (allData[i].fileId === fileId) {
			fileinfo = allData[i];
			break;
		}
	}
	return fileinfo;
}

function removeFiles(id) {
	var fileinfo = getFile(id);
	var url = getBasePath() + "/fixfiles.do";
	$.ajax({
		url : url,
		type : "POST",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(fileinfo),
		success : function(data) {
			if (data === true) {
				$.notify("fix file ok", "info");
			}
			else {
				$.notify("fix file error", "error");
			}
		},
		error : function() {
			$.notify("fix file error", "error");
		}
	});
}

window.operateEvents = {
	'click .remove' : function(e, value, row, index) {
		var $table = $('#table');
		removeFiles(row.fileId);
		$table.bootstrapTable('remove', {
			field : 'fileId',
			values : [ row.fileId ]
		});
	}
};