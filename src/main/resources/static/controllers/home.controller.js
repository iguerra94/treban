angular.module('treban')
    .controller('HomeController', ['$rootScope', '$scope', '$location','$window','$log', 'coreService', 'genericStylesUtils', 'taskListFunctionsUtils', 'taskListStylesUtils', 'RESPONSE_CODE_OK', 'RESPONSE_CODE_CREATED', HomeController]);

function HomeController($rootScope, $scope, $location, $window, $log, coreService, genericStylesUtils, taskListFunctionsUtils, taskListStylesUtils, RESPONSE_CODE_OK, RESPONSE_CODE_CREATED) {
    console.log("home");

    $window.document.title = "Treban - Inicio";

    $overlay = $window.document.querySelector(".overlay");
    $errorMessageBox = $window.document.querySelector(".error-message-box");
    $successMessageBox = $window.document.querySelector(".success-message-box");
    $errorMessage = $window.document.querySelector(".error-message");
    $successMessage = $window.document.querySelector(".success-message");
    $spinner = $window.document.querySelector(".fa-spinner");
    $btnAddTaskList = $window.document.querySelector("#btn-add-task-list");
    $userMenu = $window.document.querySelector('.user-menu');
    $loadingBoardWrapper = $window.document.querySelector(".loading-board-wrapper");
    $boardWrapper = $window.document.querySelector('.board-wrapper');

    $scope.lists = [
        { position: 1, name: "BACKLOG" },
        { position: 2, name: "TODO" },
        { position: 3, name: "WAITING" },
        { position: 4, name: "IN PROGRESS" },
        { position: 5, name: "DONE" }
    ];

    $scope.taskLists = [];

    $scope.selected = "TODAS";

    $scope.listsToCreate = {
        sprintName: "",
        lists: []
    };

    $scope.noListsCreated = true;

    $scope.animateLoadingBoardSpinner = function() {
        return new Promise(function(resolve, reject) {
            $scope.showLoadingBoardSpinner();

            $window.setTimeout(() => {
                resolve();
            }, 3000);
        });
    };

    $scope.showLoadingBoardSpinner = function() {
        taskListStylesUtils.showLoadingBoardSpinner([$loadingBoardWrapper, $boardWrapper]);
    };

    $scope.hideLoadingBoardSpinner = function() {
        taskListStylesUtils.hideLoadingBoardSpinner([$loadingBoardWrapper, $boardWrapper]);
    };

    $scope.verifyIfAllListsAreCreated = function () {
        coreService.listTaskLists()
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    taskListFunctionsUtils.decideIfAddingDisabledAttributeToButtonIsNeeded($btnAddTaskList, resp.data, $scope.lists);
                    taskListStylesUtils.decideIfAddingDisabledClassToButtonIsNeeded($btnAddTaskList, resp.data, $scope.lists);
                }
            })
            .catch(err => console.log(err));
    };

    $scope.getLists = function () {
        coreService.listTaskLists()
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    taskListFunctionsUtils.updateTaskLists($scope, resp.data);
                }
            })
            .catch(err => console.log(err));
    };

    this.$onInit = function() {
        $scope.animateLoadingBoardSpinner()
            .then(() => {
                $scope.verifyIfAllListsAreCreated();
                $scope.getLists();
            })
            .then(() => {
                $scope.hideLoadingBoardSpinner();
            });
    };

    this.$onInit();

    $scope.shouldTaskListBeShowed = function (taskListName) {
        return taskListFunctionsUtils.shouldTaskListBeShowed($scope, taskListName);
    };

    $scope.toggleCollapseTaskList = function (list, $event) {
        const $collapseTaskListIcon = $event.target;
        const $taskListItemsWrapper = $event.target.parentElement.parentElement.children[1];
        const $taskListItems = $event.target.parentElement.parentElement.children[1].children[0];

        taskListStylesUtils.toggleCollapseTaskList(list, [$collapseTaskListIcon, $taskListItemsWrapper, $taskListItems]);
        taskListFunctionsUtils.toggleCollapseTaskList(list);
    };

    $scope.openUserMenu = function ($event) {
        taskListStylesUtils.openUserMenu($event, $rootScope, [$userMenu]);
    };

    $scope.closeUserMenuIfOpened = function($event) {
        taskListStylesUtils.closeUserMenuIfOpened($event, $rootScope, [$userMenu]);
    };

    $scope.openModalAddTaskList = function() {
        const $modalAddTaskList = document.querySelector("#modal-add-task-list");
        $scope.closeAllModals();
        $scope.showOverlay();
        taskListStylesUtils.showModal($modalAddTaskList);
    };

    $scope.setElementTitleAttrBasedOnClass = function (element, className, textContent) {
        const el = document.querySelector(element);
        taskListStylesUtils.setElementTitleAttrBasedOnClass(el, className, textContent);
    };

    $scope.animateSpinner = function() {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner();

            $window.setTimeout(() => {
                $scope.hideSpinner();
                resolve();
            }, 500);
        });
    };

    $scope.showSpinner = function () {
        taskListStylesUtils.showSpinner($spinner);
    };

    $scope.hideSpinner = function () {
        taskListStylesUtils.hideSpinner($spinner);
    };

    $scope.showOverlay = function () {
        taskListStylesUtils.showOverlay($overlay);
    };

    $scope.hideOverlay = function () {
        taskListStylesUtils.hideOverlay($overlay);
    };

    $scope.validateTaskListsData = function () {
        const listsData = $scope.listsToCreate;

        $scope.animateSpinner()
            .then(() => {
                // validations
                if (!$scope.validateEmptyAttributes(listsData)) return;

                $scope.addTaskLists(listsData);
            })
    };

    $scope.validateEmptyAttributes = function(listsData) {
        return genericStylesUtils.validateEmptyAttributes(listsData, [$errorMessage, $errorMessageBox]);
    };

    $scope.resetArrayListsToCreate = function () {
        $scope.listsToCreate = { sprintName: '', lists: [] };
    };

    $scope.closeErrorMessageBox = function () {
        genericStylesUtils.closeMessageBox($errorMessageBox);
    };

    $scope.closeErrorMessageBoxIfVisible = function() {
        genericStylesUtils.closeMessageBoxIfVisible($errorMessageBox);
    };

    $scope.closeModalAddTaskList = function($event) {
        if ($event) $event.preventDefault();

        $scope.resetArrayListsToCreate();
        $scope.deselectAllTaskListItems();
        $scope.closeAllModals();
        $scope.closeErrorMessageBoxIfVisible();
        $scope.hideOverlay();
    };

    $scope.openModalTaskDetail = function() {
        const $modalTaskDetail = document.querySelector("#modal-task-detail");
        $scope.closeAllModals();
        $scope.showOverlay();
        taskListStylesUtils.showModal($modalTaskDetail);
    };

    $scope.closeModalTaskDetail = function($event) {
        if ($event) $event.preventDefault();

//        $scope.resetListsToCreateArray();
//        $scope.deselectAllTaskListItems();
        $scope.closeAllModals();
//        taskListStylesUtils.closeMessageBoxIfVisible($errorMessageBox);
        $scope.hideOverlay();
    };

    $scope.openAddNewTaskForm = function() {
        const $newTaskForm = document.querySelector("#new-task__form");
        taskListStylesUtils.openAddNewTaskForm($newTaskForm);
    };

    $scope.closeAddNewTaskForm = function() {
        const $newTaskForm = document.querySelector("#new-task__form");
        taskListStylesUtils.closeAddNewTaskForm($newTaskForm);
    };

    $scope.closeAllModals = function() {
        const $modalBoxList = document.querySelectorAll(".modal-box");
        taskListStylesUtils.closeAllModals($modalBoxList);
    };

    $scope.selectTaskListItem = function ($event, list) {
        $scope.closeErrorMessageBoxIfVisible();

        taskListStylesUtils.toggleClassSelectedToTaskListItem($event);
        taskListFunctionsUtils.decideIfSelectingOrNotTheTaskListItem($event, list, $scope.listsToCreate.lists);
    };

    $scope.deselectAllTaskListItems = function () {
        const $taskListItems = $window.document.querySelectorAll(".task-list-item");
        taskListStylesUtils.deselectAllTaskListItems($taskListItems);
    };

    $scope.addTaskLists = function (listsData) {
        listsData.lists.forEach((list, index) => {
            const listData = {
                name: list,
                sprintName: listsData.sprintName
            };

            coreService.addTaskList(listData)
                .then(resp => {
                    if (resp.status === RESPONSE_CODE_CREATED) {
                        console.log("EXITO");

                        if (index === listsData.lists.length - 1) {
                            taskListStylesUtils.showCustomSuccessMessage(listsData.lists, [$successMessage, $successMessageBox]);

                            $scope.verifyIfAllListsAreCreated();
                            $scope.getLists();

                            $scope.closeModalAddTaskList();
                        }
                    }
                });
        });
    };

}