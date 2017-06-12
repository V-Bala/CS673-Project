(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('userstory', {
            parent: 'entity',
            url: '/userstory',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Userstories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/userstory/userstories.html',
                    controller: 'UserstoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('userstory-detail', {
            parent: 'userstory',
            url: '/userstory/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Userstory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/userstory/userstory-detail.html',
                    controller: 'UserstoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Userstory', function($stateParams, Userstory) {
                    return Userstory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'userstory',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('userstory-detail.edit', {
            parent: 'userstory-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userstory/userstory-dialog.html',
                    controller: 'UserstoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Userstory', function(Userstory) {
                            return Userstory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('userstory.new', {
            parent: 'userstory',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userstory/userstory-dialog.html',
                    controller: 'UserstoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                comments: null,
                                status: null,
                                priority: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('userstory', null, { reload: 'userstory' });
                }, function() {
                    $state.go('userstory');
                });
            }]
        })
        .state('userstory.edit', {
            parent: 'userstory',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userstory/userstory-dialog.html',
                    controller: 'UserstoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Userstory', function(Userstory) {
                            return Userstory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('userstory', null, { reload: 'userstory' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('userstory.delete', {
            parent: 'userstory',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userstory/userstory-delete-dialog.html',
                    controller: 'UserstoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Userstory', function(Userstory) {
                            return Userstory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('userstory', null, { reload: 'userstory' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
