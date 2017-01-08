$(document).ready(function() {
	initUsers();
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
			var users = JSON.stringify(data);
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

			$('#users .typeahead').typeahead({
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

function getfiles() {
	
}