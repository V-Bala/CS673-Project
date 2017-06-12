(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('IssueDialogController', IssueDialogController);

    IssueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Issue', 'Project'];

    function IssueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Issue, Project) {
        var vm = this;

        vm.issue = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();

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
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
