angular.module('yawil').factory('mapService', function () {
    var service = function () {
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
    };
    return new service();
});