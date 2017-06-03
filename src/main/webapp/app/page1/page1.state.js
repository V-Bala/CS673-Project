(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('page1', {
            parent: 'app',
            url: '/page1',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/page1/page1.html',
                    controller: 'Page1Controller',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
