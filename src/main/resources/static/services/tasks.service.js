angular.module('treban')
    .factory('tasksService',
        ['$http', 'URL_API_BASE',
            function($http, URL_API_BASE) {
                return {
                    listTasks: function (orderByFieldParam, orderByCriteriaParam) {
                        return $http.get(`${URL_API_BASE}tasks?order_by_field=${orderByFieldParam}&order_by_criteria=${orderByCriteriaParam}`);
                    },
                    getTask: function (taskId) {
                        return $http.get(`${URL_API_BASE}tasks/${taskId}`);
                    },
                    addTask: function (task) {
                        return $http.post(`${URL_API_BASE}tasks`, task);
                    },
                    editTask: function (task) {
                        return $http.put(`${URL_API_BASE}tasks/${task.id}`, task);
                    },
                    deleteTask: function (taskId) {
                        return $http.delete(`${URL_API_BASE}tasks/${taskId}`);
                    }
                }
            }
        ]);