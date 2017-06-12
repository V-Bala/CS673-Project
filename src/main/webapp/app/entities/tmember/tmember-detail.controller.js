(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('TmemberDetailController', TmemberDetailController);

    TmemberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tmember', 'User', 'Task', 'Userstory', 'Team'];

    function TmemberDetailController($scope, $rootScope, $stateParams, previousState, entity, Tmember, User, Task, Userstory, Team) {
        var vm = this;

        vm.tmember = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectoneApp:tmemberUpdate', function(event, result) {
            vm.tmember = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
