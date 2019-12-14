angular.module('treban')
    .factory('listsService',
        ['$http', 'URL_API_BASE',
            function($http, URL_API_BASE) {
                return {
                    listTaskLists: function () {
                        return $http.get(`${URL_API_BASE}lists`);
                    },
                    addTaskList: function (list) {
                        return $http.post(`${URL_API_BASE}lists`, list);
                    }
                }
            }
        ]);