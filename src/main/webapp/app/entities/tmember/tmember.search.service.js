(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .factory('TmemberSearch', TmemberSearch);

    TmemberSearch.$inject = ['$resource'];

    function TmemberSearch($resource) {
        var resourceUrl =  'api/_search/tmembers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
