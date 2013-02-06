function ArtistMapController($scope, mapService, $http) {

    //TODO centrer sur les coordonnées de l'utilisateur
    var cityOfUser = {

    };

    var searchRadius = 5;

    $scope.displayedArtists = [];

    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 9 // the zoom level
    });

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