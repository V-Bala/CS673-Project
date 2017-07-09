(function() {
    'use strict';

    angular
        .module('projectoneApp')
        .controller('AddPmemberController', AddPmemberController);

    AddPmemberController.$inject = ['$timeout', '$state', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'Issue', 'Principal', 'User'];

    function AddPmemberController ($timeout, $state, $scope, $stateParams, $uibModalInstance, entity, Project, Issue, Principal, User) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.save = save;
        vm.issues = Issue.query();
        vm.email = null;
        vm.add = add;
        vm.getusers = getUsers;


        function add() {
            vm.project.pmembers.push(vm.adder);
            vm.save();
            vm.clear();
            $state.reload();
        }


        function getUsers() {
            vm.adder = User.byemail({email : vm.email});
        }




        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }



        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {

                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectoneApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            location.reload();
        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }
})();
