(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('RequirementDetailController', RequirementDetailController);

    RequirementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Requirement'];

    function RequirementDetailController($scope, $rootScope, $stateParams, previousState, entity, Requirement) {
        var vm = this;

        vm.requirement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectoneApp:requirementUpdate', function(event, result) {
            vm.requirement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
