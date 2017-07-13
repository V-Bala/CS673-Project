'use strict';

describe('Controller Tests', function() {

    describe('Issue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIssue, MockProject, MockUserstory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIssue = jasmine.createSpy('MockIssue');
            MockProject = jasmine.createSpy('MockProject');
            MockUserstory = jasmine.createSpy('MockUserstory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Issue': MockIssue,
                'Project': MockProject,
                'Userstory': MockUserstory
            };
            createController = function() {
                $injector.get('$controller')("IssueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projectoneApp:issueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
