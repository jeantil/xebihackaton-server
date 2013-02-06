function SearchController($scope, searchService, $http) {
    $scope.searchTerm = '';

    $scope.selectedArtists = searchService.selectedArtists;

    $scope.addArtist = function (artistToAdd) {
        $scope.selectedArtists = searchService.addArtist(artistToAdd);
        $http.post("http://xpua.cloudfoundry.com/users/1/artists/" + artistToAdd.id);
    };

    $scope.removeArtist = function (artistToRemove) {
        $scope.selectedArtists = searchService.removeArtist(artistToRemove);
        $http.delete("/users/1/artists/" + artistToRemove.id);
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