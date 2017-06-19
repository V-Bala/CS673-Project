(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('SessionsController', SessionsController);

    SessionsController.$inject = ['Sessions', 'Principal', '$uibModalInstance'];

    function SessionsController (Sessions, Principal, $uibModalInstance) {
        var vm = this;

        vm.account = null;
        vm.error = null;
        vm.invalidate = invalidate;
        vm.sessions = Sessions.getAll();
        vm.success = null;
        vm.clear = clear;


        Principal.identity().then(function(account) {
            vm.account = account;
        });

        function invalidate (series) {
            Sessions.delete({series: encodeURIComponent(series)},
                function () {
                    vm.error = null;
                    vm.success = 'OK';
                    vm.sessions = Sessions.getAll();
                },
                function () {
                    vm.success = null;
                    vm.error = 'ERROR';
                });
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
