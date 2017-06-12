(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('TmemberController', TmemberController);

    TmemberController.$inject = ['Tmember', 'TmemberSearch'];

    function TmemberController(Tmember, TmemberSearch) {

        var vm = this;

        vm.tmembers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Tmember.query(function(result) {
                vm.tmembers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TmemberSearch.query({query: vm.searchQuery}, function(result) {
                vm.tmembers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
