var movieList = $('.movie')

function replaceAll(str, searchStr, replaceStr) {
	  return str.split(searchStr).join(replaceStr);
}


$('#searchBox')
		.keyup(
				function(e) {
					$('#movieWrapper').empty();
					var searchVal = replaceAll($('#searchBox').val()," ","");
					
					for (var i = 0; i < movieList.length; i++) {
						var movieName = replaceAll(movieList.get(i).children[0].children[1].children[0].children[1].textContent
								.trim()," ", "");
						if (movieName.includes(searchVal)) {
							
							$('#movieWrapper').append(movieList.get(i));
						}

					}

				})