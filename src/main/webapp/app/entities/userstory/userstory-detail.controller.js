(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('UserstoryDetailController', UserstoryDetailController);

    UserstoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Userstory', 'Task'];

    function UserstoryDetailController($scope, $rootScope, $stateParams, previousState, entity, Userstory, Task) {
        var vm = this;

        vm.userstory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectoneApp:userstoryUpdate', function(event, result) {
            vm.userstory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
