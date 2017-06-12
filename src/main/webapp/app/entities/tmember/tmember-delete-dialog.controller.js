(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('TmemberDeleteController',TmemberDeleteController);

    TmemberDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tmember'];

    function TmemberDeleteController($uibModalInstance, entity, Tmember) {
        var vm = this;

        vm.tmember = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tmember.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
