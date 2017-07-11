(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'User', 'Issue', 'Userstory', 'Requirement'];

    function HomeController ($scope, Principal, LoginService, $state, Project, User, Issue, Userstory, Requirement) {
        var vm = this;

        vm.account = null;
        vm.myProjects = Project.myprojects();
        vm.user = User.query(); //TODO: Try to get the user's firstname into home.html to get username displayed in dashboard as 'User's Dashboard', this currently gets array of users
        vm.issues = Issue.query(); //TODO: Fix data coming from the issues query, Project field is displaying a complete JSON object vs just the project name in the view
        vm.userstories = Userstory.query();

        //TODO: Get requirements data pulled in here
        vm.requirements = Requirement.query();

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
