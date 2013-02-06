
function HomeController($scope, searchService, $http, $rootScope, $location) {

    $rootScope.selectedArtists = searchService.selectedArtists;

    $scope.removeArtist = function (artistToRemove) {
        $rootScope.selectedArtists = searchService.removeArtist(artistToRemove);
    };


    $scope.artistEvents = [];


    var onArtistListChange = function() {

        console.log("*** artist list change");

        var newEvents = [];
        _.each(searchService.selectedArtists, function(artist) {

            var albumEvent = {
                icon : "album",
                artist : artist.name,
                title : "New album announced",
                date : "12/12/2013",
                description : "New album : My Little album "
            };

            newEvents.push(albumEvent);

            var concertEvent = {
                icon : "concert",
                artist : artist.name,
                title : "World Tour 2013",
                date : "12/12/2013",
                description : artist.name + " is in your city the 11 November for a concert"
            };

            newEvents.push(concertEvent);

        });

        $scope.artistEvents = newEvents;


    };
    onArtistListChange();



    $rootScope.$on("artists:add", onArtistListChange);
    $rootScope.$on("artists:remove", onArtistListChange);

    /*
    $rootScope.$watch('selectedArtists', function(newValue, oldValue) {

        console.log("selectedArtists changed", newValue, oldValue);

    });
    */

}