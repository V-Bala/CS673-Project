(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tmember', {
            parent: 'entity',
            url: '/tmember',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tmembers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tmember/tmembers.html',
                    controller: 'TmemberController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tmember-detail', {
            parent: 'tmember',
            url: '/tmember/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tmember'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tmember/tmember-detail.html',
                    controller: 'TmemberDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tmember', function($stateParams, Tmember) {
                    return Tmember.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tmember',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tmember-detail.edit', {
            parent: 'tmember-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tmember/tmember-dialog.html',
                    controller: 'TmemberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tmember', function(Tmember) {
                            return Tmember.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tmember.new', {
            parent: 'tmember',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tmember/tmember-dialog.html',
                    controller: 'TmemberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                membername: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tmember', null, { reload: 'tmember' });
                }, function() {
                    $state.go('tmember');
                });
            }]
        })
        .state('tmember.edit', {
            parent: 'tmember',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tmember/tmember-dialog.html',
                    controller: 'TmemberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tmember', function(Tmember) {
                            return Tmember.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tmember', null, { reload: 'tmember' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tmember.delete', {
            parent: 'tmember',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tmember/tmember-delete-dialog.html',
                    controller: 'TmemberDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tmember', function(Tmember) {
                            return Tmember.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tmember', null, { reload: 'tmember' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
