(function() {
    'use strict';
    angular
        .module('projectoneApp')
        .factory('Tmember', Tmember);

    Tmember.$inject = ['$resource'];

    function Tmember ($resource) {
        var resourceUrl =  'api/tmembers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
