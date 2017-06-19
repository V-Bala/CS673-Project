(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = [ '$timeout', 'Auth', 'LoginService'];

    function RegisterController ($timeout, $state, Auth, LoginService, $uibModalInstance) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = login;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;
        vm.cancel = cancel;

        $timeout(function (){angular.element('#login').focus();});

        function login() {
            cancel();
            LoginService.open();
        }

        function cancel () {
            vm.registerAccount = {
                username: null,
                password: null,
            };
            vm.authenticationError = false;
            $uibModalInstance.dismiss('cancel');
        }

        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey =  'en' ;
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                    Auth.login({
                        username: vm.registerAccount.login,
                        password: vm.registerAccount.password,
                        rememberMe: true
                    }).then(function () {
                        vm.authenticationError = false;
                        $uibModalInstance.close();
                        location.reload();
                        if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                            $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                            $state.go('home');
                        }

                        $rootScope.$broadcast('authenticationSuccess');

                        // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                        // since login is succesful, go to stored previousState and clear previousState
                        if (Auth.getPreviousState()) {
                            var previousState = Auth.getPreviousState();
                            Auth.resetPreviousState();
                            $state.go(previousState.name, previousState.params);
                        }
                    }).catch(function () {
                        vm.authenticationError = true;
                    });
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
