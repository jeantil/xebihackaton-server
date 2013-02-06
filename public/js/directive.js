angular.module('yawil').directive('autocomplete', function () {
    return {
        require: '?ngModel',
        link: function (scope, element, attrs, controller) {
            var getSource = function () {
                return angular.extend([], scope.$eval(attrs.autocomplete));
            };
            var initAutocompleteWidget = function () {
                element.autocomplete({
                    source: getSource(),
                    select: function (event, ui) {
                        if (ui.item) {
                            scope[attrs.id] = ui.item.id;
                            scope.$apply();
                        }
                    }
                });
            };

            element.on('blur', function () {
                var currentModelValue = element.val();
                if (currentModelValue) {
                    //TODO faire le plain text

                }
            });

            // Watch for changes to the directives options
            scope.$watch(getSource, initAutocompleteWidget, true);
        }
    };
});