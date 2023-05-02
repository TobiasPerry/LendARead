function initMap() {
    document.addEventListener("DOMContentLoaded", () => {
        const geocoder = new google.maps.Geocoder();
        const address = document.getElementById("address").value;
        console.log(address);
        geocoder.geocode( { 'address': address}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                //Got result, center the map and put it out ther
                const map = new google.maps.Map(document.getElementById("map"), {
                    zoom: 10,
                    center: results[0].geometry.location,
                    disableDefaultUI: true,
                    draggable: false,
                    styles: [
                        {
                            "featureType": "water",
                            "elementType": "geometry",
                            "stylers": [
                                {
                                    "color": "#e9e9e9"
                                },
                                {
                                    "lightness": 17
                                }
                            ]
                        },
                        {
                            "featureType": "landscape",
                            "elementType": "geometry",
                            "stylers": [
                                {
                                    "color": "#f5f5f5"
                                },
                                {
                                    "lightness": 20
                                }
                            ]
                        },
                        {
                            "featureType": "road.highway",
                            "elementType": "geometry.fill",
                            "stylers": [
                                {
                                    "color": "#ffffff"
                                },
                                {
                                    "lightness": 17
                                }
                            ]
                        }
                    ]
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

    });

}
window.initMap = initMap;