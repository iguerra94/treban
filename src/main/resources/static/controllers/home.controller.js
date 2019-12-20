angular.module('treban')
    .controller('HomeController', ['$rootScope', '$scope', '$location','$window','$log', 'listsService', 'tasksService', 'genericStylesUtils', 'taskListFunctionsUtils', 'taskListStylesUtils', 'taskFunctionsUtils', 'taskStylesUtils', 'RESPONSE_CODE_OK', 'RESPONSE_CODE_CREATED', HomeController]);

function HomeController($rootScope, $scope, $location, $window, $log, listsService, tasksService, genericStylesUtils, taskListFunctionsUtils, taskListStylesUtils, taskFunctionsUtils, taskStylesUtils, RESPONSE_CODE_OK, RESPONSE_CODE_CREATED) {
    console.log("home");

    $window.document.title = "Treban - Inicio";

    $overlay = $window.document.querySelector(".overlay");
    $successMessageBox = $window.document.querySelector(".success-message-box");
    $successMessage = $window.document.querySelector(".success-message");
    $btnOpenModalAddTaskList = $window.document.querySelector("#btn-open-modal-add-task-list");
    $userMenu = $window.document.querySelector('.user-menu');

    $scope.lists = [ { position: 1, name: "BACKLOG" }, { position: 2, name: "TODO" }, { position: 3, name: "WAITING" }, { position: 4, name: "IN PROGRESS" }, { position: 5, name: "DONE" } ];
    $scope.taskPriorityOptions = ["LOW", "MEDIUM", "HIGH"];
    $scope.taskEstimationOptions = [1,2,3,4,5,6,7,8,9,10];

    $scope.taskLists = [];
    $scope.tasks = [];

    $scope.orderByFieldParam = "created_at";
    $scope.orderByCriteriaParam = "asc";
    $scope.listShowed = "TODAS";

    $scope.listsToCreate = { sprintName: "", lists: [] };

    $scope.newTask = { name: "", priority: "LOW", estimation: 5, status: { name: "BACKLOG" } };

    $scope.currentTaskDetail = { status: { name: ""} };

    $scope.currentTaskDetailEdition = {};

    $scope.noListsCreated = true;

    $scope.showLeftMenuItems = function () {
        $selectBoxes = $window.document.querySelectorAll(".select-box");
        $selectInputs = $window.document.querySelectorAll(".select-input");

        taskListStylesUtils.showLeftMenuItems([
            $selectBoxes,
            $selectInputs
        ]);
    };

    $scope.showMainContainerHeaderItems = function () {
        $mainContainerHeaderItemBoxes = $window.document.querySelectorAll(".main-container__header-item-box");
        $mainContainerHeaderItems = $window.document.querySelectorAll(".main-container__header-item");

        taskListStylesUtils.showMainContainerHeaderItems([
            $mainContainerHeaderItemBoxes,
            $mainContainerHeaderItems
        ]);
    };

    $scope.showLoadingLists = function () {
        $noListsCreatedContainer = $window.document.querySelector(".no-lists-created");

        $listItemsBoxes = $window.document.querySelectorAll(".main-container__content-item-box");
        $listItems = $window.document.querySelectorAll(".main-container__content-item");

        taskListStylesUtils.showLoadingLists([
            $noListsCreatedContainer,
            $listItemsBoxes,
            $listItems
        ]);
    };

    $scope.showLists = function () {
        $noListsCreatedContainer = $window.document.querySelector(".no-lists-created");

        $listItemsBoxes = $window.document.querySelectorAll(".main-container__content-item-box");
        $listItems = $window.document.querySelectorAll(".main-container__content-item");

        taskListStylesUtils.showLists($scope.noListsCreated, [
            $noListsCreatedContainer,
            $listItemsBoxes,
            $listItems
        ]);
    };

    $scope.loadLists = function() {
        return new Promise(function(resolve, reject) {
             $scope.verifyIfAllListsAreCreated();
             $scope.getLists();
             $scope.getTasks($scope.orderByFieldParam, $scope.orderByCriteriaParam);

             $window.setTimeout(() => {
                 resolve();
             }, 1500);
         });
     };

    $scope.refreshLists = function() {
        return new Promise(function(resolve, reject) {
            $scope.showLoadingLists();
            $scope.getLists();
            $scope.getTasks($scope.orderByFieldParam, $scope.orderByCriteriaParam);

            $window.setTimeout(() => {
                resolve();
            }, 1000);
        });
    };

    $scope.verifyIfAllListsAreCreated = function () {
        listsService.listTaskLists()
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    taskListFunctionsUtils.decideIfAddingDisabledAttributeToButtonIsNeeded($btnOpenModalAddTaskList, resp.data, $scope.lists);
                    taskListStylesUtils.decideIfAddingDisabledClassToButtonIsNeeded($btnOpenModalAddTaskList, resp.data, $scope.lists);
                }
            })
            .catch((e) => console.log(e));
    };

    $scope.getLists = function () {
        listsService.listTaskLists()
            .then((resp) => {
                if (resp.status === RESPONSE_CODE_OK) {
                    taskListFunctionsUtils.updateTaskLists($rootScope, $scope, resp.data);
                }
            })
            .catch((e) => console.log(e));
    };

    $scope.getTasks = function (orderByFieldParam, orderByCriteriaParam) {
        tasksService.listTasks(orderByFieldParam, orderByCriteriaParam)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    taskFunctionsUtils.updateTasks($scope, resp.data);
                }
            })
            .catch((e) => console.log(e));
    };

    this.$onInit = function() {
        $scope.loadLists()
            .then(() => {
                $scope.showLeftMenuItems();
                $scope.showMainContainerHeaderItems();
                $scope.showLists();
            });
    };

    this.$onInit();

    $scope.currentUserRoleIsNotAllowedToMoveTasksFromListName = function (userRole, listName) {
        return (userRole !== $rootScope.roles.PROJECT_LEADER) && ($scope.currentTaskDetail.status.name === listName);
    };

    $scope.currentListNameIsTheListOfTheGreaterPositionCreated = function (listName) {
        const indexOfList = $scope.taskLists.findIndex(l => l.name === listName);

        if (!$scope.taskLists[indexOfList]) return;

        return ($scope.taskLists[indexOfList].created) && ($scope.taskLists[indexOfList].position === $rootScope.currentListOfGreaterPositionCreated);
    };

    $scope.setOrderByCriteriaParam = function ($event) {
        $listOrderCriteriaInputs = $window.document.querySelectorAll('.lists-order-criteria-input');

        $listOrderCriteriaInputs.forEach(item => {
            item.classList.add('hidden');
        });

        if ($event.target.classList.contains('fa-arrow-down')) {
            $listOrderCriteriaAscInput = $window.document.querySelector('.lists-order-criteria-input.fa-arrow-up');
            $listOrderCriteriaAscInput.classList.remove('hidden');

            $scope.orderByCriteriaParam = "asc";
        } else {
            $listOrderCriteriaDescInput = $window.document.querySelector('.lists-order-criteria-input.fa-arrow-down');
            $listOrderCriteriaDescInput.classList.remove('hidden');

            $scope.orderByCriteriaParam = "desc";
        }
    };

    $scope.getUpdatedTasksOrdered = function () {
        $scope.refreshLists()
            .then(() => {
                $scope.showLists();
            });
    };

    $scope.shouldTaskListBeShowed = function (taskListName) {
        return taskListFunctionsUtils.shouldTaskListBeShowed($scope, taskListName);
    };

    $scope.toggleCollapseTaskList = function (list, $event) {
        const $collapseTaskListIcon = $event.target;
        const $taskListItemsWrapper = $event.target.parentElement.parentElement.children[1];

        taskListStylesUtils.toggleCollapseTaskList(list, [$collapseTaskListIcon, $taskListItemsWrapper]);
        taskListFunctionsUtils.toggleCollapseTaskList(list);
    };

    $scope.openUserMenu = function ($event) {
        taskListStylesUtils.openUserMenu($event, $rootScope, [$userMenu]);
    };

    $scope.closeUserMenuIfOpened = function() {
        taskListStylesUtils.closeUserMenuIfOpened($rootScope, [$userMenu]);
    };

    $scope.getCurrentUserNameInitials = function () {
        const currentUserNameSplitted = $rootScope.currentUser.name.split(" ");
        return `${ currentUserNameSplitted[0].slice(0,1) }${ currentUserNameSplitted.length > 1 ? currentUserNameSplitted[currentUserNameSplitted.length-1].slice(0,1) : "" }`;
    };

    $scope.loadTaskLists = function() {
        return new Promise(function(resolve, reject) {
            $scope.showLoadingTaskLists();
            $scope.getLists();

            $window.setTimeout(() => {
                resolve();
            }, 1000);
        });
    };

    $scope.showLoadingTaskLists = function () {
        $modalAddTaskListElements = $window.document.querySelectorAll(".modal-add-task-list-element");

        $taskListItemBoxes = $window.document.querySelectorAll(".task-list-item-box");
        $taskListItems = $window.document.querySelectorAll(".task-list-item");

        taskListStylesUtils.showLoadingTaskLists([
            $modalAddTaskListElements,
            $taskListItemBoxes,
            $taskListItems
        ]);
    };

    $scope.showTaskLists = function () {
        $modalAddTaskListElements = $window.document.querySelectorAll(".modal-add-task-list-element");

        $taskListItemBoxes = $window.document.querySelectorAll(".task-list-item-box");
        $taskListItems = $window.document.querySelectorAll(".task-list-item");

        taskListStylesUtils.showTaskLists([
            $modalAddTaskListElements,
            $taskListItemBoxes,
            $taskListItems
        ]);
    };

    $scope.openModalAddTaskList = function() {
        const $modalAddTaskList = document.querySelector("#modal-add-task-list");
        $scope.closeAllModals();
        $scope.showOverlay();
        taskListStylesUtils.showModal($modalAddTaskList);

        $scope.loadTaskLists()
            .then(() => {
                $scope.showTaskLists();
            });
    };

    $scope.openModalAddTask = function() {
        const $modalAddTask = document.querySelector("#modal-add-task");
        $scope.closeAllModals();
        $scope.showOverlay();
        taskListStylesUtils.showModal($modalAddTask);
    };

    $scope.normalizeTaskDataObject = function (taskData) {
        return taskFunctionsUtils.normalizeTaskDataObject(taskData);
    };

    $scope.loadTaskDetail = function(taskId) {
        return new Promise(function(resolve, reject) {
            $scope.showLoadingTaskDetail();
            $scope.getTask(taskId);

            $window.setTimeout(() => {
                resolve();
            }, 1000);
        });
    };

    $scope.showLoadingTaskDetail = function () {
        $btnOpenModalDeleteTask = $window.document.querySelector("#btn-open-modal-delete-task");

        $modalContentColumnItemBoxes = $window.document.querySelectorAll(".modal-content-column-item-box");
        $modalContentColumnItemElements = $window.document.querySelectorAll(".modal-content-column-item-element");

        taskStylesUtils.showLoadingTaskDetail([
            $btnOpenModalDeleteTask,
            $modalContentColumnItemBoxes,
            $modalContentColumnItemElements
        ]);
    };

    $scope.initTaskDetailEditionObject = function () {
        $scope.currentTaskDetailEdition = angular.copy($scope.currentTaskDetail);
        $scope.currentTaskDetailEdition.status.name = $scope.currentTaskStatusEditedOption;

        delete $scope.currentTaskDetailEdition.createdAt;
        delete $scope.currentTaskDetailEdition.lastModified;
        delete $scope.currentTaskDetailEdition.status.id;
        delete $scope.currentTaskDetailEdition.status.sprintName;
    };

    $scope.showTaskDetail = function () {
        $btnOpenModalDeleteTask = $window.document.querySelector("#btn-open-modal-delete-task");

        $modalContentColumnItemBoxes = $window.document.querySelectorAll(".modal-content-column-item-box");
        $modalContentColumnItemElements = $window.document.querySelectorAll(".modal-content-column-item-element");

        taskStylesUtils.showTaskDetail([
            $btnOpenModalDeleteTask,
            $modalContentColumnItemBoxes,
            $modalContentColumnItemElements
        ]);
    };

    $scope.openModalTaskDetail = function(taskId) {
        const $modalTaskDetail = document.querySelector("#modal-task-detail");

        $scope.closeAllModals();
        $scope.showOverlay();
        $scope.closeModalEditionElementsIfOpened();
        taskListStylesUtils.showModal($modalTaskDetail);

        $scope.loadTaskDetail(taskId)
            .then(() => {
                $scope.initCurrentTaskEditionVariables();
                $scope.updateCurrentTaskEditionChangedVariable();
                $scope.initTaskDetailEditionObject();
                $scope.showTaskDetail();
            });
    };

    $scope.getCurrentTaskDetailEditionStatusNamePosition = function (listName) {
        if (listName !== undefined && listName.length > 0){
            const index = $scope.lists.findIndex(l => l.name === listName);
            return $scope.lists[index].position;
        }

    };

    $scope.shouldShowList = function (list) {
        if ($scope.currentTaskDetail.status.name === $scope.lists[0].name && $rootScope.currentUser.roles[0] !== $rootScope.roles.PROJECT_LEADER) return false;
        const position = $scope.getCurrentTaskDetailEditionStatusNamePosition($scope.currentTaskDetail.status.name);
        return list.position > position;
    };

    $scope.shouldShowPriority = function (priority) {
        return priority !== $scope.currentTaskDetail.priority;
    };

    $scope.shouldShowEstimationNumber = function (estimationNumber) {
        return estimationNumber !== $scope.currentTaskDetail.estimation;
    };

    $scope.initCurrentTaskEditionVariables = function() {
        $scope.currentTaskStatusEditedOption = "SELECCIONAR";
    };

    $scope.updateCurrentTaskEditionChangedVariable = function() {
        $scope.currentTaskDetailEditionObjectDidntChange = $scope.currentTaskStatusEditedOption === "SELECCIONAR";
    };

    $scope.initCurrentTaskEditionVariables();
    $scope.updateCurrentTaskEditionChangedVariable();

    $scope.openModalTaskDeleteQuestion = function() {
        const $modalTaskDeleteQuestion = document.querySelector("#modal-task-delete-question");
        $scope.closeAllModals();
        taskListStylesUtils.showModal($modalTaskDeleteQuestion);
    };

    $scope.closeModalTaskDeleteQuestion = function() {
        const $modalTaskDetail = document.querySelector("#modal-task-detail");
        $scope.closeAllModals();
        taskListStylesUtils.showModal($modalTaskDetail);
    };

    $scope.setElementTitleAttrBasedOnClass = function (element, className, textContent) {
        const el = document.querySelector(element);
        taskListStylesUtils.setElementTitleAttrBasedOnClass(el, className, textContent);
    };

    $scope.animateSpinner = function($spinner) {
        return new Promise(function(resolve, reject) {
            $scope.showSpinner($spinner);

            $window.setTimeout(() => {
                $scope.hideSpinner($spinner);
                resolve();
            }, 500);
        });
    };

    $scope.showSpinner = function ($spinner) {
        taskListStylesUtils.showSpinner($spinner);
    };

    $scope.hideSpinner = function ($spinner) {
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
        const $spinner = $window.document.querySelector("#btn-add-task-lists").querySelector(".fa-spinner");
        const $errorMessageBox = $window.document.querySelector("#modal-add-task-list").querySelector(".error-message-box");

        $scope.animateSpinner($spinner)
            .then(() => {
                // validations
                if (!$scope.validateEmptyAttributes(listsData, $errorMessageBox)) return;

                $scope.addTaskLists(listsData);
            }).catch((e) => console.error(e));
    };

    $scope.validateTaskData = function () {
        const newTaskData = $scope.newTask;
        const $spinner = $window.document.querySelector("#btn-add-task").querySelector(".fa-spinner");
        const $errorMessageBox = $window.document.querySelector("#modal-add-task").querySelector(".error-message-box");

        $scope.animateSpinner($spinner)
            .then(() => {
                // validations
                if (!$scope.validateEmptyAttributes(newTaskData, $errorMessageBox)) return;

                $scope.addTask(newTaskData);
            })
    };

    $scope.editTaskDetail = function ($event) {
        if ($event.target.tagName !== "BUTTON") {
            $element = $event.target.parentElement;

            $modalContentColumnItemElementValue = $element.parentElement.parentElement.querySelector(".modal-content-column-item-element-value");
            $modalContentColumnItemElementEdit = $element.parentElement.parentElement.querySelector(".modal-content-column-item-element-edit");
        } else {
            $modalContentColumnItemElementValue = $event.target.parentElement.parentElement.querySelector(".modal-content-column-item-element-value");
            $modalContentColumnItemElementEdit = $event.target.parentElement.parentElement.querySelector(".modal-content-column-item-element-edit");
        }

        taskStylesUtils.editTaskDetail([
            $modalContentColumnItemElementValue,
            $modalContentColumnItemElementEdit
        ]);
    };

    $scope.resetCurrentTaskEditionIndividualVariable = function (option) {
        switch (option) {
            case "currentTaskStatusEditedOption":
                $scope.currentTaskStatusEditedOption = "SELECCIONAR";
                break;
            default:
                break;
        }

        $scope.updateCurrentTaskEditionChangedVariable();
    };

    $scope.finishEditingTaskDetail = function ($event, option) {
        if ($event.target.tagName !== "BUTTON") {
            $element = $event.target.parentElement;

            $modalContentColumnItemElementValue = $element.parentElement.parentElement.parentElement.querySelector(".modal-content-column-item-element-value");
            $modalContentColumnItemElementEdit = $element.parentElement.parentElement.parentElement.querySelector(".modal-content-column-item-element-edit");
        } else {
            $modalContentColumnItemElementValue = $event.target.parentElement.parentElement.parentElement.querySelector(".modal-content-column-item-element-value");
            $modalContentColumnItemElementEdit = $event.target.parentElement.parentElement.parentElement.querySelector(".modal-content-column-item-element-edit");
        }

        $scope.resetCurrentTaskEditionIndividualVariable(option);

        taskStylesUtils.finishEditingTaskDetail([
            $modalContentColumnItemElementValue,
            $modalContentColumnItemElementEdit
        ]);
    };

    $scope.closeModalEditionElementsIfOpened = function () {
        $modalContentColumnItemElementValueElements = $window.document.querySelectorAll(".modal-content-column-item-element-value");
        $modalContentColumnItemElementEditElements = $window.document.querySelectorAll(".modal-content-column-item-element-edit");

        taskStylesUtils.closeModalEditionElementsIfOpened([
            $modalContentColumnItemElementValueElements,
            $modalContentColumnItemElementEditElements
        ]);
    };

    $scope.normalizeTaskEditedDataObject = function () {
        $scope.currentTaskDetailEdition = {
            ...$scope.currentTaskDetailEdition,
            status: {
                name: ($scope.currentTaskStatusEditedOption !== "SELECCIONAR" ? $scope.currentTaskStatusEditedOption : $scope.currentTaskDetail.status.name)
            }
        };
    };

    $scope.validateEditionCurrentTaskData = function () {
        const $spinner = $window.document.querySelector("#btn-edit-task").querySelector(".fa-spinner");

        $scope.normalizeTaskEditedDataObject();

        $scope.animateSpinner($spinner)
            .then(() => {
                $scope.editTask($scope.currentTaskDetailEdition);
            })
    };

    $scope.validateEmptyAttributes = function(data, $errorMessageBox) {
        const $errorMessage = $errorMessageBox.querySelector(".error-message");
        return genericStylesUtils.validateEmptyAttributes(data, [$errorMessageBox, $errorMessage]);
    };

    $scope.resetArrayListsToCreate = function () {
        $scope.listsToCreate = { sprintName: '', lists: [] };
    };

    $scope.resetNewTaskObject = function () {
        $scope.newTask = { name: "", priority: "LOW", estimation: 5, status: { name: "BACKLOG" } };
    };

    $scope.resetCurrentTaskDetailObject = function () {
        $scope.currentTaskDetail = { status: { name: ""} };
    };

    $scope.closeErrorMessageBox = function ($event) {
        const $errorMessageBox = $event.target.parentElement;
        genericStylesUtils.closeMessageBox($errorMessageBox);
    };

    $scope.closeErrorMessageBoxIfVisible = function($errorMessageBox) {
        genericStylesUtils.closeMessageBoxIfVisible($errorMessageBox);
    };

    $scope.closeErrorMessageBoxOnInputChange = function(containerId) {
        const $errorMessageBox = $window.document.querySelector(containerId).querySelector(".error-message-box");
        genericStylesUtils.closeMessageBoxIfVisible($errorMessageBox);
    };

    $scope.closeModalAddTaskList = function() {
        $scope.resetArrayListsToCreate();
        $scope.deselectAllTaskListItems();
        $scope.closeAllModals();

        const $errorMessageBox = $window.document.querySelector("#modal-add-task-list").querySelector(".error-message-box");
        $scope.closeErrorMessageBoxIfVisible($errorMessageBox);
        $scope.hideOverlay();
    };

    $scope.closeModalAddTask = function() {
        $scope.resetNewTaskObject();
        $scope.closeAllModals();

        const $errorMessageBox = $window.document.querySelector("#modal-add-task").querySelector(".error-message-box");
        $scope.closeErrorMessageBoxIfVisible($errorMessageBox);
        $scope.hideOverlay();
    };

    $scope.closeModalTaskDetail = function() {
        $scope.resetCurrentTaskDetailObject();
        $scope.closeAllModals();

        const $errorMessageBox = $window.document.querySelector("#modal-task-detail").querySelector(".error-message-box");
        $scope.closeErrorMessageBoxIfVisible($errorMessageBox);
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
        const $errorMessageBox = $window.document.querySelector("#modal-add-task-list").querySelector(".error-message-box");
        $scope.closeErrorMessageBoxIfVisible($errorMessageBox);

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

            listsService.addTaskList(listData)
                .then(resp => {
                    if (resp.status === RESPONSE_CODE_CREATED) {
                        console.log("EXITO");

                        if (index === listsData.lists.length - 1) {
                            return new Promise(function(resolve, reject) {
                                $window.setTimeout(() => {
                                    resolve();
                                }, 0);
                            });
                        }
                    }
                })
                .then(() => {
                    $scope.verifyIfAllListsAreCreated();
                    $scope.closeModalAddTaskList();

                    $scope.refreshLists()
                        .then(() => {
                            taskListStylesUtils.showAddTaskListsSuccessMessage(listsData.lists, [$successMessageBox, $successMessage]);
                            $scope.showLists();
                        });
            });
        });
    };

    $scope.getTask = function (taskId) {
        tasksService.getTask(taskId)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    $scope.currentTaskDetail = $scope.normalizeTaskDataObject(resp.data);
                }
            });
    };

    $scope.addTask = function (newTaskData) {
        tasksService.addTask(newTaskData)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_CREATED) {
                    console.log("EXITO");

                    taskStylesUtils.showAddTaskSuccessMessage([$successMessageBox, $successMessage]);

                    $scope.getTasks($scope.orderByFieldParam, $scope.orderByCriteriaParam);

                    $scope.closeModalAddTask();
                }
            });
    };

    $scope.showErrorMessage = function(message, $errorMessageBox) {
        const $errorMessage = $errorMessageBox.querySelector(".error-message");
        return genericStylesUtils.showErrorMessage(message, [$errorMessageBox, $errorMessage]);
    };

    $scope.editTask = function (changedTaskData) {
        tasksService.editTask(changedTaskData)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    console.log("EXITO");

                    $scope.closeModalTaskDetail();

                    $scope.refreshLists()
                        .then(() => {
                            taskStylesUtils.showEditTaskSuccessMessage([$successMessageBox, $successMessage]);
                            $scope.showLists();
                        });
                } else {
                    const $errorMessageBox = $window.document.querySelector("#modal-task-detail").querySelector(".error-message-box");
                    $scope.showErrorMessage(resp.data.message, $errorMessageBox);
                }
            })
            .catch((err) => {
                const $errorMessageBox = $window.document.querySelector("#modal-task-detail").querySelector(".error-message-box");
                $scope.showErrorMessage(err.data.message, $errorMessageBox);
            });
    };

    $scope.deleteTask = function (taskId) {
        tasksService.deleteTask(taskId)
            .then(resp => {
                if (resp.status === RESPONSE_CODE_OK) {
                    console.log("EXITO");

                    $scope.closeModalTaskDetail();

                    $scope.refreshLists()
                        .then(() => {
                            taskStylesUtils.showDeleteTaskSuccessMessage([$successMessageBox, $successMessage]);
                            $scope.showLists();
                        });
                }
            });
    };

    $scope.validateTaskDeletion = function (taskId) {
        const $spinner = $window.document.querySelector("#btn-delete-task").querySelector(".fa-spinner");

        $scope.animateSpinner($spinner)
            .then(() => {
                $scope.deleteTask(taskId);
            })
    };

}