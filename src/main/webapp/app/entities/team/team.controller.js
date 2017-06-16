(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('TeamController', TeamController);

    TeamController.$inject = ['Team', 'TeamSearch'];

    function TeamController(Team, TeamSearch) {

        var vm = this;

        vm.teams = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Team.query(function(result) {
                vm.teams = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TeamSearch.query({query: vm.searchQuery}, function(result) {
                vm.teams = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
