function ArtistMapController($scope) {

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
    var geocoder = new google.maps.Geocoder();
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

                //TODO Lancer une recherche autour de long lat et afficher les artistes

                var artists = [
                    {
                        name: 'Defecation of Vomit',
                        position: {
                            lat: 48.87, // initial map center latitude
                            lng: 2.34// initial map center longitude
                        }
                    },
                    {
                        name: 'Cattle decapitation',
                        position: {
                            lat: 48.88, // initial map center latitude
                            lng: 2.34 // initial map center longitude
                        }
                    }
                ];

                $scope.markers = [];
                _.forEach(artists, function (artist) {
                    $scope.markers.push({
                        latitude: parseFloat(artist.position.lat),
                        longitude: parseFloat(artist.position.lng),
                        label: artist.name
                    });
                });

                $scope.zoom = 12;
                $scope.$apply();
            } else {
                console.log("Geocode was not successful for the following reason: " + status);
            }
        });
    };
    $scope.$watch('localisation', _.debounce(searchForLocation, 1000));


}