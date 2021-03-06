(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .factory('TaskSearch', TaskSearch);

    TaskSearch.$inject = ['$resource'];

    function TaskSearch($resource) {
        var resourceUrl =  'api/_search/tasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
