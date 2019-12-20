angular.module('treban')
    .factory('taskListFunctionsUtils',
        function() {
            return {
                verifyIfNoListsAreCreated: function ($scope) {
                    $scope.taskLists.forEach(list => {
                        if (list.created) {
                            $scope.noListsCreated = false;
                        }
                    });
                },
                updateCurrentListOfGreaterPositionCreatedVariable: function ($rootScope, $scope) {
                    $scope.taskLists.forEach(list => {
                        if (list.created && (list.position > $rootScope.currentListOfGreaterPositionCreated)) {
                            $rootScope.currentListOfGreaterPositionCreated = list.position;
                        }
                    });

                    console.log("currentListOfGreaterPositionCreated: " + $rootScope.currentListOfGreaterPositionCreated);
                },
                updateTaskLists: function ($rootScope, $scope, data) {
                    const lists = data;

                    $scope.lists.forEach(list => {
                        const index = $scope.taskLists.findIndex(l => l.name === list.name);

                        if (index === -1) {
                            $scope.taskLists.push({ name: list.name, created: false, position: list.position, collapsed: false });
                        }

                        const indexUpdated = $scope.taskLists.findIndex(l => l.name === list.name);
                        $scope.taskLists[indexUpdated].created = !!lists.find(l => l.name === list.name);
                    });

                    console.log($scope.taskLists);

                    this.verifyIfNoListsAreCreated($scope);
                    this.updateCurrentListOfGreaterPositionCreatedVariable($rootScope, $scope);
                },
                decideIfAddingDisabledAttributeToButtonIsNeeded: function (btn, list1, list2) {
                    btn.disabled = list1.length === list2.length;
                },
                decideIfSelectingOrNotTheTaskListItem: function (event, list, lists) {
                    if (event.target.classList.contains("selected")) {
                        lists.push(list.name);
                    } else {
                        const index = lists.findIndex(l => l === list.name);
                        lists.splice(index, 1);
                    }
                },
                shouldTaskListBeShowed: function (scope, taskListName) {
                    return scope.listShowed === "TODAS" || scope.listShowed === taskListName.toUpperCase();
                },
                toggleCollapseTaskList: function (list) {
                    list.collapsed = !list.collapsed;
                }
            }
        }
    );