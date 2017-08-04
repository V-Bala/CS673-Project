(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('UserstoryDialogController', UserstoryDialogController);

    UserstoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Userstory', 'Task', 'Project', 'Issue', 'Requirement', '$state'];

    function UserstoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Userstory, Task, Project, Issue, Requirement, $state) {
        var vm = this;

        vm.userstory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tasks = Task.query();
        vm.projects = Project.query();
        vm.issues = Issue.query();
        vm.requirements = Requirement.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userstory.id !== null) {
                Userstory.update(vm.userstory, onSaveSuccess, onSaveError);
            } else {
                Userstory.save(vm.userstory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectoneApp:userstoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.reload();
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
