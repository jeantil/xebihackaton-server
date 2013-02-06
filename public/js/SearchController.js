function SearchController($scope) {
    $scope.searchTerm = '';


    $scope.addArtist = function(artistToAdd) {
        var alreadyAdded = _.filter($scope.selectedArtists, function(artist) {
            return artist.id == artistToAdd.id;
        }).length > 0;
        if(alreadyAdded) {
            return;
        }



        $scope.selectedArtists.push(artistToAdd);
    }

    // TODO : ajax call to get list
    $scope.selectedArtists = [
        {
            id : 1,
            name: "Justin bieber",
            position: {
                lat: 36,
                lng: 10
            }
        },
        {
            id : 2,
            name: "Bob L'eponge",
            position: {
                lat: 36,
                lng: 10
            }
        }

    ];

    $scope.searchResults = [];
    var searchForTerm = function () {
        if ($scope.searchTerm) {
            console.log('search term');
            //TODO Faire un appel coté serveur
            $scope.searchResults = [
                {
                    id : 3,
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