function UserMapController($scope, $http) {

    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 9 // the zoom level
    });

    $scope.artists = [];

    $scope.$watch('artistName', function () {
        $http.get('/artists?q=' + $scope.artistName).success(function (artists) {
            $scope.artists = _.map(artists, function (artist) {
                return {
                    id: artist.id,
                    label: artist.name,
                    value: artist.name
                };
            });
        });
    });

    $scope.$watch('artist', function () {
        var idArtist = $scope.artist;
        if (idArtist) {
            $http.get('/users-map/' + idArtist).success(function (fans) {
                _.forEach(fans, function (fan) {
                    $scope.markers.push({
                        latitude: parseFloat(fan.city.lat),
                        longitude: parseFloat(fan.city.lng),
                        label: fan.name
                    });
                });
            });
        }
    });

};