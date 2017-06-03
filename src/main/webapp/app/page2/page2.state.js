(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('page2', {
            parent: 'app',
            url: '/page2',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/page2/page2.html',
                    controller: 'Page2Controller',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
