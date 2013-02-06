angular.module('yawil', ['google-maps'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/registration', {
                templateUrl:'templates/registration.html',
                controller:RegistrationController
            })
            .when('/home', {
                templateUrl:'templates/home.html',
                controller:HomeController
            })
            .when('/map/artist', {
                templateUrl:'templates/artist-map.html',
                controller:ArtistMapController
            })
            .when('/map/user', {
                templateUrl:'templates/user-map.html',
                controller:UserMapController
            })
            .otherwise({redirectTo:'/registration'});
    }]);


var NavBarController = function($scope, searchService, $http) {


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

