(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('Page2Controller', Page2Controller);

    Page2Controller.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function Page2Controller ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
