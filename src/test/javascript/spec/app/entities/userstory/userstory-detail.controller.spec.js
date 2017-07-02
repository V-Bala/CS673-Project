'use strict';

describe('Controller Tests', function() {

    describe('Userstory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserstory, MockTask, MockProject, MockIssue;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserstory = jasmine.createSpy('MockUserstory');
            MockTask = jasmine.createSpy('MockTask');
            MockProject = jasmine.createSpy('MockProject');
            MockIssue = jasmine.createSpy('MockIssue');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Userstory': MockUserstory,
                'Task': MockTask,
                'Project': MockProject,
                'Issue': MockIssue
            };
            createController = function() {
                $injector.get('$controller')("UserstoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projectoneApp:userstoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
