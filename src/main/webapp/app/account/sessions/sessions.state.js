(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('sessions', {
            parent: 'home',
            url: '/sessions',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sessions'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/account/sessions/sessions.html',
                    controller: 'SessionsController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                }).result.then(function() {
                    $state.go('home', null, { reload: 'home' });
                }, function() {
                    $state.go('home');
                });
            }]
        });
    }
})();
