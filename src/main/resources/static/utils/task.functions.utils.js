angular.module('treban')
    .factory('taskFunctionsUtils',
        function() {
            return {
                normalizeTaskDataObject: function (taskData) {
                    return {
                        ...taskData,
                        createdAt: moment(taskData.createdAt).format("DD [de] MMMM [de] YYYY [a las] HH:mm"),
                        lastModified: moment(taskData.lastModified).fromNow()
                    }
                },
                updateTasks: function (scope, data) {
                    const tasksList = angular.copy(data);

                    scope.tasks = [];

                    tasksList.forEach(task => {
                        const taskDataNormalized = this.normalizeTaskDataObject(task);

                        scope.tasks.push(taskDataNormalized);
                    });
                }
            }
        }
    );