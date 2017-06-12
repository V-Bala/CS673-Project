'use strict';

describe('Controller Tests', function() {

    describe('Tmember Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTmember, MockUser, MockTask, MockUserstory, MockTeam;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTmember = jasmine.createSpy('MockTmember');
            MockUser = jasmine.createSpy('MockUser');
            MockTask = jasmine.createSpy('MockTask');
            MockUserstory = jasmine.createSpy('MockUserstory');
            MockTeam = jasmine.createSpy('MockTeam');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tmember': MockTmember,
                'User': MockUser,
                'Task': MockTask,
                'Userstory': MockUserstory,
                'Team': MockTeam
            };
            createController = function() {
                $injector.get('$controller')("TmemberDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projectoneApp:tmemberUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
