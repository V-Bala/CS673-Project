(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('IssueDetailController', IssueDetailController);

    IssueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Issue', 'Project'];

    function IssueDetailController($scope, $rootScope, $stateParams, previousState, entity, Issue, Project) {
        var vm = this;

        vm.issue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectoneApp:issueUpdate', function(event, result) {
            vm.issue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
