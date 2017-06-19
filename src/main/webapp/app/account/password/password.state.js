(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('password', {
            parent: 'home',
            url: '/passchange',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Password'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/account/password/password.html',
                    controller: 'PasswordController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                }).result.then(function() {
                    $state.go('home', null, { reload: 'home' });
                }, function() {
                    $state.go('home');
                });
            }]
        });
    }
})();
