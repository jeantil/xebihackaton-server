function HomeController($scope, searchService, $http) {

    $scope.selectedArtists = searchService.selectedArtists;

}