(function() {
    'use strict';
    angular
        .module('projectoneApp')
        .factory('Task', Task);

    Task.$inject = ['$resource', 'DateUtils'];

    function Task ($resource, DateUtils) {
        var resourceUrl =  'api/tasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.targetdate = DateUtils.convertDateTimeFromServer(data.targetdate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
