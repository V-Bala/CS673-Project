(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('IssueDialogController', IssueDialogController);

    IssueDialogController.$inject = ['$state','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Issue', 'Userstory'];

    function IssueDialogController ($state, $timeout, $scope, $stateParams, $uibModalInstance, entity, Issue, Userstory) {
        var vm = this;

        vm.issue = entity;
        vm.clear = clear;
        vm.save = save;

        getstories();

        function getstories() {
            var lap = 0;
            if (vm.issue.project.id > 0){
                vm.userstories = Userstory.projus({id: vm.issue.project.id});
            } else {
                lap = lap + 1;
                if (lap <= 5){
                    window.setTimeout(getstories, 500);
                }
            }
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.issue.id !== null) {
                Issue.update(vm.issue, onSaveSuccess, onSaveError);
            } else {
                Issue.save(vm.issue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectoneApp:issueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.reload()
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
