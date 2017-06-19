(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('PasswordController', PasswordController);

    PasswordController.$inject = ['Auth', 'Principal', '$uibModalInstance'];

    function PasswordController (Auth, Principal, $uibModalInstance) {
        var vm = this;

        vm.changePassword = changePassword;
        vm.doNotMatch = null;
        vm.error = null;
        vm.success = null;
        vm.clear = clear;

        Principal.identity().then(function(account) {
            vm.account = account;
        });

        function changePassword () {
            if (vm.password !== vm.confirmPassword) {
                vm.error = null;
                vm.success = null;
                vm.doNotMatch = 'ERROR';
            } else {
                vm.doNotMatch = null;
                Auth.changePassword(vm.password).then(function () {
                    vm.error = null;
                    vm.success = 'OK';
                }).catch(function () {
                    vm.success = null;
                    vm.error = 'ERROR';
                });
            }
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


    }
})();
