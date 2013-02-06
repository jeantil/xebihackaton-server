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
            .otherwise({redirectTo:'/registration'});
    }]);

