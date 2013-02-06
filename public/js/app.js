angular.module('yawil', ['google-maps'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/registration', {
                templateUrl:'templates/registration.html',
                controller:RegistrationController
            })
            .when('/search', {
                templateUrl:'templates/search.html',
                controller:SearchController
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

