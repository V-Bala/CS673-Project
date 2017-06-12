(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('UserstoryDeleteController',UserstoryDeleteController);

    UserstoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Userstory'];

    function UserstoryDeleteController($uibModalInstance, entity, Userstory) {
        var vm = this;

        vm.userstory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Userstory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
