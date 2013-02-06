angular.module('yawil').factory('searchService', function ($http) {
    var service = function () {
        this.selectedArtists = [
            {
                id: 1,
                name: "Justin bieber",
                position: {
                    lat: 36,
                    lng: 10
                }
            },
            {
                id: 2,
                name: "Bob L'eponge",
                position: {
                    lat: 36,
                    lng: 10
                }
            }

        ];

        this.addArtist = function (artistToAdd) {
            var alreadyAdded = _.filter(this.selectedArtists,function (artist) {
                return artist.id == artistToAdd.id;
            }).length > 0;

            if (!alreadyAdded) {
                this.selectedArtists.push(artistToAdd);
                $http.post("/users/1/artists/" + artistToAdd.id);
            }

            return this.selectedArtists;
        };

        this.removeArtist = function (artistToRemove) {
            this.selectedArtists = _.reject(this.selectedArtists, function (artist) {
                return artist.id == artistToRemove.id;
            });
            $http.delete("/users/1/artists/" + artistToRemove.id);

            return this.selectedArtists;
        };
    };
    return new service();
});