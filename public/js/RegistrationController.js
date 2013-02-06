function RegistrationController($scope, mapService, $location) {
    $scope.localisation = '';
    $scope.hasLocation = false;
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            mapService.reverseGeocode(position.coords.latitude, position.coords.longitude, function (address) {
                $scope.localisation = address[2].long_name+','+address[4].long_name;
                $scope.hasLocation = true;
                $scope.$apply();
            });

        });
    }


    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 9 // the zoom level
    });

    var geocoder = new google.maps.Geocoder();
    var searchForLocation = function () {
        mapService.geocode($scope.localisation, function (centerLocation) {
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

            $scope.zoom = 12;
            $scope.hasLocation = true;
            $scope.$apply();
        });

    };

    $scope.$watch('localisation', _.debounce(searchForLocation, 1000));
    $scope.register = function (isValid) {
        if (isValid && $scope.hasLocation) {
            $location.path('/search');
        }
    }

}
