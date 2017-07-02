(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('CommentController', CommentController);

    CommentController.$inject = ['Comment', 'CommentSearch'];

    function CommentController(Comment, CommentSearch) {

        var vm = this;

        vm.comments = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Comment.query(function(result) {
                vm.comments = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CommentSearch.query({query: vm.searchQuery}, function(result) {
                vm.comments = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
