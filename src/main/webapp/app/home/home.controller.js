(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'User'];

    function HomeController ($scope, Principal, LoginService, $state, Project, User) {
        var vm = this;

        vm.account = null;
        vm.myProjects = Project.myprojects();
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
