(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('TmemberDialogController', TmemberDialogController);

    TmemberDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Tmember', 'User', 'Task', 'Userstory', 'Team'];

    function TmemberDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Tmember, User, Task, Userstory, Team) {
        var vm = this;

        vm.tmember = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.tasks = Task.query();
        vm.userstories = Userstory.query();
        vm.teams = Team.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tmember.id !== null) {
                Tmember.update(vm.tmember, onSaveSuccess, onSaveError);
            } else {
                Tmember.save(vm.tmember, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectoneApp:tmemberUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
