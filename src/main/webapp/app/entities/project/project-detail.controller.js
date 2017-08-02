(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$state', '$stateParams', '$uibModal', '$scope', '$rootScope', 'previousState', 'DataUtils', 'entity', 'Project', 'Issue', 'Comment', 'Principal', 'Userstory', 'Requirement'];

    function ProjectDetailController($state, $stateParams, $uibModal, $scope, $rootScope, previousState, DataUtils, entity, Project, Issue, Comment, Principal, Userstory, Requirement) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;


        vm.comments = Project.projcom({id: vm.project.id});
        vm.userstories = Userstory.projus({id: vm.project.id});
        vm.issues = Issue.projissue({id: vm.project.id});
        vm.requirements = Requirement.projreq({id: vm.project.id});



        vm.comment = null;
        vm.myProjects = Project.myprojects();
        vm.save = save;
        vm.isSaving = false;
        vm.isMember = false;
        vm.myProjects = Project.myprojects();




        /* Requirement section */
        vm.addreq = addReq;
        function addReq() {
            $uibModal.open({
                templateUrl: 'app/entities/requirement/requirement-dialog.html',
                controller: 'RequirementDialogController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity:['Project', function (Project) {
                        return {
                            name: null,
                            status: null,
                            id: null,
                            project: Project.get({id: vm.project.id})
                        };
                    }]
                }
            })
        }

        vm.deletereq = deleteReq;
        function deleteReq(id) {
            $uibModal.open({
                templateUrl: 'app/entities/requirement/requirement-delete-dialog.html',
                controller: 'RequirementDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['Requirement', function(Requirement) {
                        return Requirement.get({id: id}).$promise;
                    }]
                }
            })
        }


        /* Issues section */
        vm.addissue = addIssue;
        function addIssue() {
            $uibModal.open({
                templateUrl: 'app/entities/issue/issue-dialog.html',
                controller: 'IssueDialogController',
                controllerAs: 'vm',
                size: 'md',
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

        vm.deleteiss = deleteiss;
        function deleteiss(id) {
            $uibModal.open({
                templateUrl: 'app/entities/issue/issue-delete-dialog.html',
                controller: 'IssueDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['Issue', function(Issue) {
                        return Issue.get({id : id}).$promise;
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
                size: 'md',
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

        vm.deleteus = deleteus;
        function deleteus(id) {
            $uibModal.open({
                templateUrl: 'app/entities/userstory/userstory-delete-dialog.html',
                controller: 'UserstoryDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['Userstory', function(Userstory) {
                        return Userstory.get({id : id}).$promise;
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
                Comment.save(vm.comment, onSaveSuccess, onSaveSuccess);
            }
        }
        function onSaveSuccess () {
            vm.isSaving = false;
            location.reload();
        }

        var unsubscribe = $rootScope.$on('projectoneApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
