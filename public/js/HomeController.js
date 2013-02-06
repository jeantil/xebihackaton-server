function HomeController($scope, searchService, $http) {

    $scope.selectedArtists = searchService.selectedArtists;

    $scope.removeArtist = function (artistToRemove) {
        $scope.selectedArtists = searchService.removeArtist(artistToRemove);
    };

}