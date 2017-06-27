(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$state', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Project', 'Issue', 'User', 'Comment', 'Principal'];

    function ProjectDetailController($state, $scope, $rootScope, $stateParams, previousState, DataUtils, entity, Project, Issue, User, Comment, Principal) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.comments = Project.projcom({id: vm.project.id});
        vm.comment = null;
        vm.myProjects = Project.myprojects();
        vm.save = save;
        vm.isSaving = false;
        vm.isMember = false;


        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                for(var i = 0; i < vm.project.pmembers.length; i++){
                    if (vm.account.email === vm.project.pmembers[i].email){
                        vm.isMember = true;
                    }
                }
            });

        }

        function save () {
            if(vm.isSaving == false){
                vm.comment.projectcomment = vm.project;
                vm.comment.id = null;
                vm.isSaving = true;
                Comment.save(vm.comment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess () {
            vm.isSaving = false;
            $state.reload();
        }

        function onSaveError () {
            vm.isSaving = false;
        }




        var unsubscribe = $rootScope.$on('projectoneApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
