angular.module('yawil', ['google-maps'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/registration', {
                templateUrl: 'templates/registration.html',
                controller: RegistrationController
            })
            .when('/home', {
                templateUrl: 'templates/home.html',
                controller: HomeController
            })
            .when('/map/artist', {
                templateUrl: 'templates/artist-map.html',
                controller: ArtistMapController
            })
            .when('/map/user', {
                templateUrl: 'templates/user-map.html',
                controller: UserMapController
            })
            .otherwise({redirectTo: '/registration'});
    }]);


var NavBarController = function ($scope, searchService, $http, $rootScope, $location) {


    $scope.searchTerm = '';

    $rootScope.selectedArtists = searchService.selectedArtists;

    $scope.addArtist = function (artistToAdd) {
        $scope.selectedArtists = searchService.addArtist(artistToAdd);
        $scope.searchTerm = '';
    };

    $scope.removeArtist = function (artistToRemove) {
        $rootScope.selectedArtists = searchService.addArtist(artistToAdd);
    };

    $scope.searchResults = [];
    var searchForTerm = function () {
        if ($scope.searchTerm) {

            $http.get('/artists?q=' + $scope.searchTerm).success(function (artists) {
                $scope.searchResults = artists
            });

        } else {
            $scope.searchResults = [];
        }
        $scope.$apply();
    };
    $scope.$watch('searchTerm', _.debounce(searchForTerm, 300));


    $rootScope.goToArtist = function () {
        $location.path('/map/artist');
    };

    $rootScope.goToFan = function () {
        $location.path('/map/user');
    };

    $rootScope.goToHome = function () {
        $location.path('/home');
    };

}

