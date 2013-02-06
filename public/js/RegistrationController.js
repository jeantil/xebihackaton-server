function RegistrationController($scope, mapService, userService, $location, $http) {
    $scope.localisation = '';
    $scope.hasLocation = false;

    mapService.geolocalize(function (address) {
        $scope.localisation = address;
        $scope.hasLocation = true;
        $scope.$apply();
    });

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
            userService.currentUser().then(function(user) {
                user.city = {lat:1.1, lng:2.2};
                $http.post("/users/" + user.id, user);
            })
            $location.path('/home');
        }
    }

}
