function MapController($scope) {
    $scope.isMapElementHidden = true;

    setTimeout(function () {
        $scope.isMapElementHidden = false;
        $scope.$apply();
    }, 200);
}