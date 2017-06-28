(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$state', '$stateParams', '$uibModal', '$scope', '$rootScope', 'previousState', 'DataUtils', 'entity', 'Project', 'Issue', 'Comment', 'Principal', 'Userstory',];

    function ProjectDetailController($state, $stateParams, $uibModal, $scope, $rootScope, previousState, DataUtils, entity, Project, Issue, Comment, Principal, Userstory) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;


        vm.comments = Project.projcom({id: vm.project.id});
        vm.userstories = Userstory.projus({id: vm.project.id});
        vm.issues = Issue.projissue({id: vm.project.id});


        vm.comment = null;
        vm.myProjects = Project.myprojects();
        vm.save = save;
        vm.isSaving = false;
        vm.isMember = false;
        vm.myProjects = Project.myprojects();


        /* Issues section */
        vm.addissue = addIssue;
        function addIssue() {
            $uibModal.open({
                templateUrl: 'app/entities/issue/issue-dialog.html',
                controller: 'IssueDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity:['Project', function (Project) {
                        return {
                            name: null,
                            comments: null,
                            status: null,
                            priority: null,
                            id: null,
                            project: Project.get({id: vm.project.id})
                        };
                    }]
                }
            })
        }

        /* Userstories section */
        vm.addus = addus;
        function addus(){
            $uibModal.open({
                templateUrl: 'app/entities/userstory/userstory-dialog.html',
                controller: 'UserstoryDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity:['Project', function (Project) {
                        return {
                            title: null,
                            description: null,
                            comments: null,
                            status: null,
                            priority: null,
                            id: null,
                            project: Project.get({id: vm.project.id})
                        };
                    }]
                }
            })
        }


        getAccount();

        $scope.$on('projectoneApp:userstoryUpdate', function() {
            location.reload();
        });

        $scope.$on('projectoneApp:issueUpdate', function() {
            location.reload();
        });

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

        /* comments */
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
