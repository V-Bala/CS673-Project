(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('RequirementDialogController', RequirementDialogController);

    RequirementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Requirement', 'Userstory', 'Project', '$state'];

    function RequirementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Requirement, Userstory, Project, $state) {
        var vm = this;

        vm.requirement = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userstories = Userstory.query();
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.requirement.id !== null) {
                Requirement.update(vm.requirement, onSaveSuccess, onSaveError);
            } else {
                Requirement.save(vm.requirement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectoneApp:requirementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.reload();
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
