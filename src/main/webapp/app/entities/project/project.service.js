(function() {
    'use strict';
    angular
        .module('projectoneApp')
        .factory('Project', Project);

    Project.$inject = ['$resource'];

    function Project ($resource) {
        var resourceUrl =  'api/projects/:id';

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
            'update': { method:'PUT' },
            'myprojects': { method: 'GET', isArray: true, url: 'api/myprojects'},
            'addmem': { method:'PUT', url: 'api/addmem' },
            'projcom': { method: 'GET', isArray: true, url: 'api/projcomments'}
        });
    }
})();
