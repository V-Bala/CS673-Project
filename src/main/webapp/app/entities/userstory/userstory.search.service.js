(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .factory('UserstorySearch', UserstorySearch);

    UserstorySearch.$inject = ['$resource'];

    function UserstorySearch($resource) {
        var resourceUrl =  'api/_search/userstories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
