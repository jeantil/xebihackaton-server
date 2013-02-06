function SearchController($scope, searchService) {
    $scope.searchTerm = '';


    $scope.selectedArtists = searchService.selectedArtists;
    $scope.addArtist = function (artistToAdd) {
        $scope.selectedArtists = searchService.addArtist(artistToAdd);
    };
    $scope.removeArtist = function (artistToRemove) {
        $scope.selectedArtists = searchService.removeArtist(artistToRemove);
    };

    $scope.searchResults = [];
    var searchForTerm = function () {
        if ($scope.searchTerm) {
            //TODO Faire un appel coté serveur
            $scope.searchResults = [
                {
                    id: 3,
                    name: "Bob Marley",
                    position: {
                        lat: 36,
                        lng: 10
                    }
                }
            ];

        } else {
            $scope.searchResults = [];
        }
        $scope.$apply();
    };
    $scope.$watch('searchTerm', _.debounce(searchForTerm, 300));


}