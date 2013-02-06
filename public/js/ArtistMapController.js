function ArtistMapController($scope, mapService, $http, userService) {

    var searchRadius = 5;

    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 9 // the zoom level
    });

    userService.currentUser().then(function (user) {
        var city = user.city;
        $scope.center = {
            lat: city.lat,
            lng: city.lng
        };
        $scope.zoom = 12;
        mapService.reverseGeocode(city.lat, city.lng, function (address) {
            $scope.localisation = address[2].long_name + ',' + address[4].long_name;
        });
    });

    $scope.displayedArtists = [];
    var searchForLocation = function () {

        mapService.geocode($scope.localisation, function (centerLocation) {
            var longitude = centerLocation.lng();
            var latitude = centerLocation.lat();
            $scope.center = {
                lat: latitude,
                lng: longitude
            };

            $scope.markers = [];
            $http.get('/artists-map/' + latitude + '/' + longitude + '/' + searchRadius).success(function (artists) {
                _.forEach(artists, function (artist) {
                    $scope.markers.push({
                        latitude: parseFloat(artist.position.lat),
                        longitude: parseFloat(artist.position.lng),
                        label: artist.name
                    });
                });
            });

            $scope.zoom = 12;
            $scope.$apply();
        });

    };
    $scope.$watch('localisation', _.debounce(searchForLocation, 1000));


}