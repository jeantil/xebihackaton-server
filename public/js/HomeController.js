function HomeController($scope, searchService, $location) {

    $scope.selectedArtists = searchService.selectedArtists;

    $scope.removeArtist = function (artistToRemove) {
        $scope.selectedArtists = searchService.removeArtist(artistToRemove);
    };



}