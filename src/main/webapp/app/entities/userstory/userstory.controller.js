(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('UserstoryController', UserstoryController);

    UserstoryController.$inject = ['Userstory', 'UserstorySearch'];

    function UserstoryController(Userstory, UserstorySearch) {

        var vm = this;

        vm.userstories = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Userstory.query(function(result) {
                vm.userstories = result;
                vm.searchQuery = null;
            });
        }

        vm.byId = Userstory.findById;

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UserstorySearch.query({query: vm.searchQuery}, function(result) {
                vm.userstories = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
