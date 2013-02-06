function RegistrationController($scope, $location) {

    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 12 // the zoom level
    });

    $scope.localisation = '';
    var geocoder = new google.maps.Geocoder();
    $scope.hasLocation = false;
    var searchForLocation = function () {
        geocoder.geocode({ 'address': $scope.localisation}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                var centerLocation = results[0].geometry.location;

                var longitude = centerLocation.lng();
                var latitude = centerLocation.lat();
                $scope.center = {
                    lat: latitude,
                    lng: longitude
                };

                $scope.markers = [
                    {
                        latitude: parseFloat(latitude),
                        longitude: parseFloat(longitude)
                    }
                ];

                $scope.zoom = 14;
                $scope.hasLocation = true;
                $scope.$apply();
            } else {
                console.log("Geocode was not successful for the following reason: " + status);
                $scope.hasLocation = false;
            }
        });
    };

    $scope.$watch('localisation', _.debounce(searchForLocation, 1000));
    $scope.register = function (isValid) {
        if (isValid && $scope.hasLocation) {
            $location.path('/search');
        }
    }

}
