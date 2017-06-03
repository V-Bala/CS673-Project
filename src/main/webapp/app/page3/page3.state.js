(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('page3', {
            parent: 'app',
            url: '/page3',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/page3/page3.html',
                    controller: 'Page3Controller',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
