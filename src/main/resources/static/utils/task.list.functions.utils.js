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
                updateTaskLists: function ($scope, data) {
                    const lists = data;

                    $scope.lists.forEach(list => {
                        const index = $scope.taskLists.findIndex(l => l.name === list.name);

                        if (index === -1) {
                            $scope.taskLists.push({ name: list.name, created: false, position: list.position, collapsed: false });
                        }

                        const indexUpdated = $scope.taskLists.findIndex(l => l.name === list.name);
                        $scope.taskLists[indexUpdated].created = !!lists.find(l => l.name === list.name);
                    });

                    this.verifyIfNoListsAreCreated($scope);
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
                    return scope.selected === "TODAS" || scope.selected === taskListName.toUpperCase();
                },
                toggleCollapseTaskList: function (list) {
                    list.collapsed = !list.collapsed;
                }
            }
        }
    );