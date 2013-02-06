angular.module('yawil', ['google-maps'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/search', {
                templateUrl:'templates/search.html',
                controller:SearchController
            })
            .otherwise({redirectTo:'/search'});
    }]);


function SearchController() {

}