(function() {
    'use strict';
    angular
        .module('projectoneApp')
        .factory('Userstory', Userstory);

    Userstory.$inject = ['$resource'];

    function Userstory ($resource) {
        var resourceUrl =  'api/userstories/:id';

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
