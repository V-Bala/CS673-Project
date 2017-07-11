(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'User', 'Issue', 'Requirement'];

    function HomeController ($scope, Principal, LoginService, $state, Project, User, Issue) {
        var vm = this;

        vm.account = null;
        vm.myProjects = Project.myprojects();
        vm.user = User.firstName; //TODO: Try to get the user's firstname into home.html to get username displayed in dashboard as 'User's Dashboard', this currently isn't working
        vm.issues = Issue.projissue(); //TODO: Get issue data pulled into home.html

        //TODO: Get requirements data pulled in here

        /* HERE IS A QUERY TO BRING PROJECTS TO HOME, NOTICE THE INJECT OF PROJECT ABOVE*/
        vm.projects = Project.query();
        /* Project.query is defined in projects.service.js and handled on the server side with ProjectResource.java  */
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
