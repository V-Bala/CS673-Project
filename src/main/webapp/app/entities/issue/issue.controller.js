(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('IssueController', IssueController);

    IssueController.$inject = ['Issue', 'IssueSearch'];

    function IssueController(Issue, IssueSearch) {

        var vm = this;

        vm.issues = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Issue.query(function(result) {
                vm.issues = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            IssueSearch.query({query: vm.searchQuery}, function(result) {
                vm.issues = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
