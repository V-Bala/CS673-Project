(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('issue', {
            parent: 'entity',
            url: '/issue',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Issues'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/issue/issues.html',
                    controller: 'IssueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('issue-detail', {
            parent: 'issue',
            url: '/issue/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Issue'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/issue/issue-detail.html',
                    controller: 'IssueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Issue', function($stateParams, Issue) {
                    return Issue.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'issue',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('issue-detail.edit', {
            parent: 'issue-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issue/issue-dialog.html',
                    controller: 'IssueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Issue', function(Issue) {
                            return Issue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('issue.new', {
            parent: 'issue',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issue/issue-dialog.html',
                    controller: 'IssueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                comments: null,
                                status: null,
                                priority: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('issue', null, { reload: 'issue' });
                }, function() {
                    $state.go('issue');
                });
            }]
        })
        .state('issue.edit', {
            parent: 'issue',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issue/issue-dialog.html',
                    controller: 'IssueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Issue', function(Issue) {
                            return Issue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('issue', null, { reload: 'issue' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('issue.delete', {
            parent: 'issue',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/issue/issue-delete-dialog.html',
                    controller: 'IssueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Issue', function(Issue) {
                            return Issue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('issue', null, { reload: 'issue' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
