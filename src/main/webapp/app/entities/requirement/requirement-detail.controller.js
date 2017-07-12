(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('RequirementDetailController', RequirementDetailController);

    RequirementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Requirement', 'Userstory', 'Project'];

    function RequirementDetailController($scope, $rootScope, $stateParams, previousState, entity, Requirement, Userstory, Project) {
        var vm = this;

        vm.requirement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectoneApp:requirementUpdate', function(event, result) {
            vm.requirement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
