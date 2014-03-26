$(document).ready(function(){
  "use strict";
  function Map() {
    this.geocoder  = new google.maps.Geocoder();
    this.inputBox  = $('#latlng');
    this.button    = $('#get-result');
    this.resultBox = $('#result-area');
  }
  
  Map.prototype.codeLatLng = function() {
    var i = 0;
    var context = this;
    var latlngStr = context.inputBox.val().split(',', 2);
    var lat = parseFloat(latlngStr[0]);
    var lng = parseFloat(latlngStr[1]);
    var latlng = new google.maps.LatLng(lat, lng);
    var result = "Your Address is: ";
    var components;
    context.geocoder.geocode({'latLng': latlng}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        if (results[0]) {
          result += results[0].formatted_address + '\n';
          components = results[0].address_components;
          for(;i < components.length ;i++) {
			  result += '\n' + components[i].types[0] + ': ' + components[i].long_name;
		  }
		  result += '\n\n' + 'Location Accuracy is: ' + results[0].geometry.location_type;
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
