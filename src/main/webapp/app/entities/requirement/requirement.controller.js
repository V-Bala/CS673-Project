(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('RequirementController', RequirementController);

    RequirementController.$inject = ['Requirement', 'RequirementSearch'];

    function RequirementController(Requirement, RequirementSearch) {

        var vm = this;

        vm.requirements = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.back = back;

        loadAll();

        function loadAll() {
            Requirement.query(function(result) {
                vm.requirements = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            RequirementSearch.query({query: vm.searchQuery}, function(result) {
                vm.requirements = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }

})();
