angular.module('yawil').factory('userService', function ($http, $q) {

    var service = function() {

        var deferred = $q.defer();
        var self = this;
        this.user = null;

        this.currentUser = function() {
            if (this.user) {
                deferred.resolve(this.user);
                return deferred.promise;
            } else {
                var promise = $http.get("/user");
                return promise.then(function (response) {
                    self.user = response.data;
                    return response.data;
                });
            }
        };
    };
    return new service();
})