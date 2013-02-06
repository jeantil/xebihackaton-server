function ArtistMapController($scope) {

    //TODO centrer sur les coordonnées de l'utilisateur
    angular.extend($scope, {
        center: {
            lat: 48.85, // initial map center latitude
            lng: 2.35 // initial map center longitude
        },
        markers: [],
        zoom: 12 // the zoom level
    });
}