angular.module('yawil').factory('mapService', function () {
    var service = function () {
        var self = this;

        var geocoder = new google.maps.Geocoder();

        this.geocode = function (label, callback) {
            geocoder.geocode({ 'address': label}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    var centerLocation = results[0].geometry.location;
                    callback(centerLocation);
                } else {
                    console.log("Geocode was not successful for the following reason: " + status);
                }
            });
        };

        this.reverseGeocode = function (lat, lng, callback) {
            geocoder.geocode({'latLng': new google.maps.LatLng(lat, lng)}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[1]) {
                        callback(results[1].address_components);
                    }
                } else {
                    console.log("Geocode was not successful for the following reason: " + status);
                }
            });
        }

        this.geolocalize = function (callback) {
            if (navigator.geolocation) {

                navigator.geolocation.getCurrentPosition(function (position) {
                    self.reverseGeocode(position.coords.latitude, position.coords.longitude, function (address) {
                        callback(address[2].long_name + ',' + address[4].long_name, position.coords);
                    });
                });
            }
        }
    };
    return new service();
});