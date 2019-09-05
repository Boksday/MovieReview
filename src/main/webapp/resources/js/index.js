
var searchBox = document.getElementById('searchBox');
var movieList = document.getElementsByClassName('movie');
var targetMovie = new Array();
var body = document.body;
searchBox.addEventListener("keydown", function(e){
    var searchWord = e.currentTarget.value.trim();
    
    for(var i = 0 ; i < movieList.length ; i++){
    	var movieName = movieList[i].children[1].children[0].children[1].textContent.trim();
        
        if(e.keyCode === 8 ){
           targetMovie=[];
           return;
        }
        document.getElementById('movieWrapper').remove();
        var div = document.createElement('div');
        div.setAttribute('id','movieWrapper');
        body.appendChild(div);
        for(var j = 0 ; j < movieList.length ; j++){
        	
        }
    	targetMovie.push(movieList[i]);
        console.log(targetMovie)  
    	if(movieName.includes(searchWord) > 0){
    		console.log(movieName)
    	}
    }
});