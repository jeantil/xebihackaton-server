function UserMapController($scope) {

    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 9 // the zoom level
    });

    //TODO récupérer la liste des artistes
    $scope.artists = [
        {id: 3, label: "dada", value: "dadaaaaaa"}
    ];

    $scope.$watch('artist', function () {
        var idArtist = $scope.artist;
        if (idArtist) {
            //TODO récupérer la liste des fans
            var fans = [
                {
                    position: {
                        lat: 48.84,
                        lng: 2.34
                    },
                    name: 'JOHNSON'
                }
            ];
            _.forEach(fans, function (fan) {
                $scope.markers.push({
                    latitude: parseFloat(fan.position.lat),
                    longitude: parseFloat(fan.position.lng),
                    label: fan.name
                });
            });
        }
    });


};