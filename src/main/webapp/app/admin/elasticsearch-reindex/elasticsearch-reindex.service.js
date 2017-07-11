(function () {
    'use strict';

    angular
        .module('projectoneApp')
        .factory('ElasticsearchReindex', ElasticsearchReindex);

    ElasticsearchReindex.$inject = ['$resource'];

    function ElasticsearchReindex($resource) {
        var service = $resource('api/elasticsearch/index', {}, {
            'reindex': {method: 'POST'}
        });

        return service;
    }
})();
