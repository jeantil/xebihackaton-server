function SearchController($scope) {
    $scope.searchTerm = '';

    $scope.searchResults = [];
    var searchForTerm = function () {
        if ($scope.searchTerm) {
            console.log('search term');
            //TODO Faire un appel coté serveur
            $scope.searchResults = [
                {
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