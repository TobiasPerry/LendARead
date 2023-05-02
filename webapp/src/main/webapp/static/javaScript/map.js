function initMap() {
    var geocoder = new google.maps.Geocoder()
    geocoder.geocode( { 'address': "San isidro, Argentina 1646"}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            //Got result, center the map and put it out ther
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 10,
                center: results[0].geometry.location,
            });
            const marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map,
            });
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
    // The map, centered at Uluru

    // The marker, positioned at Uluru

}
window.initMap = initMap;