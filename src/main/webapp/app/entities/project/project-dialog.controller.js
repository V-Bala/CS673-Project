(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('ProjectDialogController', ProjectDialogController);

    ProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Project', 'Issue', 'User', 'Comment', 'Userstory', 'Requirement'];

    function ProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Project, Issue, User, Comment, Userstory, Requirement) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.issues = Issue.query();
        vm.users = User.query();
        vm.comments = Comment.query();
        vm.userstories = Userstory.query();
        vm.requirements = Requirement.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveSuccess);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveSuccess);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectoneApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        vm.setPfiles = function ($file, project) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        project.pfiles = base64Data;
                        project.pfilesContentType = $file.type;
                    });
                });
            }
        };

    }
})();
