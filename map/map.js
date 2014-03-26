$(document).ready(function(){
  "use strict";
  function Map() {
    this.geocoder  = new google.maps.Geocoder();
    this.inputBox  = $('#latlng');
    this.button    = $('#get-result');
    this.resultBox = $('#result-area');
    this.distance = $('#distance');
  }
  
  Map.prototype.codeLatLng = function() {
    var i = 0;
    var context = this;
    var latlngStr = context.inputBox.val().split(',', 2);
    var lat = parseFloat(latlngStr[0]);
    var lng = parseFloat(latlngStr[1]);
    var latlng = new google.maps.LatLng(lat, lng);
    var result = "Places nearby this location are: ";
    
    var map = new google.maps.Map(document.getElementById('map-canvas'), {
      center: latlng,
      zoom: 15
    });

    var request = {
      location: latlng,
      radius: context.distance.val()
    };
  
    var service = new google.maps.places.PlacesService(map);

    service.nearbySearch(request, function(results, status) {
      if (status == google.maps.places.PlacesServiceStatus.OK) {
        if (results) {
					console.log(results);
          for(;i < results.length ;i++) {
			      result += '\n\n"' + results[i].name + '" and is of type: ' + results[i].types.join(', ');
		      }
          context.resultBox.val(result);
        } else {
          alert('No results found');
        }
      } else {
        alert('Geocoder failed due to: ' + status);
      }
    });
  }
  
  Map.prototype.registerListener = function() {
	this.button.on('click', this.codeLatLng.bind(this));
  }
   
  new Map().registerListener();
});
