(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('UserstoryDetailController', UserstoryDetailController);

    UserstoryDetailController.$inject = ['$state','$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Userstory', 'Task', 'Project'];

    function UserstoryDetailController($state, $scope, $rootScope, $stateParams, previousState, entity, Userstory, Task, Project) {
        var vm = this;

        vm.userstory = entity;
        vm.previousState = previousState.name;
        vm.myProjects = Project.myprojects();

        var unsubscribe = $rootScope.$on('projectoneApp:userstoryUpdate', function(event, result) {
            vm.userstory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
