(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Project', 'Issue', 'User', 'Comment'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Project, Issue, User, Comment) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        vm.comments = Project.projcom({id: vm.blog.id});

        var unsubscribe = $rootScope.$on('projectoneApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
