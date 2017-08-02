(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('requirement', {
            parent: 'entity',
            url: '/requirement',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Requirements'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/requirement/requirements.html',
                    controller: 'RequirementController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('requirement-detail', {
            parent: 'requirement',
            url: '/requirement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Requirement'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/requirement/requirement-detail.html',
                    controller: 'RequirementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Requirement', function($stateParams, Requirement) {
                    return Requirement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'requirement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('requirement-detail.edit', {
            parent: 'requirement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requirement/requirement-dialog.html',
                    controller: 'RequirementDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        entity: ['Requirement', function(Requirement) {
                            return Requirement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('requirement.new', {
            parent: 'requirement',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requirement/requirement-dialog.html',
                    controller: 'RequirementDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('requirement', null, { reload: 'requirement' });
                }, function() {
                    $state.go('requirement');
                });
            }]
        })
        .state('requirement.edit', {
            parent: 'requirement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requirement/requirement-dialog.html',
                    controller: 'RequirementDialogController',
                    controllerAs: 'vm',
                    size: 'lg',
                    resolve: {
                        entity: ['Requirement', function(Requirement) {
                            return Requirement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('requirement', null, { reload: 'requirement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('requirement.delete', {
            parent: 'requirement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/requirement/requirement-delete-dialog.html',
                    controller: 'RequirementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Requirement', function(Requirement) {
                            return Requirement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('requirement', null, { reload: 'requirement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
