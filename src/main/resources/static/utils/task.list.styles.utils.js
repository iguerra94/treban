angular.module('treban')
    .factory('taskListStylesUtils',
        function() {
            return {
                showSpinner: function (spinner) {
                    spinner.classList.remove("hidden");
                },
                hideSpinner: function (spinner) {
                    spinner.classList.add("hidden");
                },
                showOverlay: function (overlay) {
                    overlay.classList.add("active");
                },
                hideOverlay: function (overlay) {
                    overlay.classList.remove("active");
                },
                showModal: function (modal) {
                    modal.classList.remove('hidden');
                },
                openAddNewTaskForm: function(newTaskForm) {
                    newTaskForm.classList.remove("hidden");
                },
                closeAddNewTaskForm: function(newTaskForm) {
                    newTaskForm.classList.add("hidden");
                },
                openUserMenu: function($event, $scope, $elements) {
                    $userMenu = $elements[0];

                    if ($event.currentTarget.id === 'btn-username') {
                        $userMenu.classList.toggle('hidden');
                        $userMenu.classList.toggle('active');
                        $scope.userMenuOpened = !$scope.userMenuOpened;
                    }
                },
                closeUserMenuIfOpened: function($event, $scope, $elements) {
                    $userMenu = $elements[0];

                    if ($event.currentTarget.id !== 'btn-username') {
                        if ($scope.userMenuOpened) {
                            $userMenu.classList.remove('active');
                            $userMenu.classList.add('hidden');
                            $scope.userMenuOpened = false;
                        }
                    }
                },
                closeAllModals: function(modalBoxes) {
                    modalBoxes.forEach(modalBox => {
                        if (!modalBox.classList.contains('hidden')) {
                            modalBox.classList.add('hidden');
                        }
                    })
                },
                deselectAllTaskListItems: function(taskListItems) {
                    taskListItems.forEach(list => {
                        if (list.classList.contains("selected")) list.classList.remove("selected")
                    });
                },
                decideIfAddingDisabledClassToButtonIsNeeded: function (btn, list1, list2) {
                    (list1.length === list2.length) ? btn.classList.add('disabled') : btn.classList.remove('disabled');
                },
                toggleClassSelectedToTaskListItem: function (event) {
                    event.target.classList.toggle("selected");
                },
                toggleCollapseTaskList: function (list, elements) {
                    const collapseTaskListIcon = elements[0];
                    const taskListItemsWrapper = elements[1];
                    const taskListItemsWrapperChildren = Array.from(taskListItemsWrapper.children);

                    if (list.collapsed) {
                        collapseTaskListIcon.style.transform = 'rotate(0deg)';
                        setTimeout(() => {
                            taskListItemsWrapper.style.height = '100%';
                            taskListItemsWrapper.style.padding = '0em';
                            taskListItemsWrapperChildren.forEach((item) => {
                                item.style.opacity = 1;
                            });
                        },200);
                    } else {
                        collapseTaskListIcon.style.transform = 'rotate(-180deg)';
                        setTimeout(() => {
                            taskListItemsWrapper.style.height = '0em';
                            taskListItemsWrapper.style.padding = '1.2em';
                            taskListItemsWrapperChildren.forEach((item) => {
                                item.style.opacity = 0;
                            });
                        },200);
                    }
                },
                showAddTaskListsSuccessMessage: function (data, elements) {
                    const $successMessageBox = elements[0];
                    const $successMessage = elements[1];

                    if (data.length > 1) {
                        $successMessageBox.classList.remove("hidden");
                        $successMessage.textContent = "¡Exito! Las listas fueron creadas correctamente.";
                    } else {
                        $successMessageBox.classList.remove("hidden");
                        $successMessage.textContent = "¡Exito! La lista fue creada correctamente.";
                    }
                },
                setElementTitleAttrBasedOnClass: function (el, className, textContent) {
                    el.title = (el.classList.contains(className)) ? textContent : "";
                },
                showLeftMenuItems: function (elements) {
                    const $selectBoxes = elements[0];
                    const $selectInputs = elements[1];

                    $selectBoxes.forEach(item => {
                        item.classList.add("hidden");
                    });

                    $selectInputs.forEach(item => {
                        item.classList.remove('hidden');
                    });
                },
                showMainContainerHeaderItems: function (elements) {
                    const $mainContainerHeaderItemBoxes = elements[0];
                    const $mainContainerHeaderItems = elements[1];

                    $mainContainerHeaderItemBoxes.forEach(item => {
                        item.classList.add("hidden");
                    });

                    $mainContainerHeaderItems.forEach(item => {
                        item.classList.remove('hidden');
                    });
                },
                showLists: function (condition, elements) {
                    const noListsCreated = condition;

                    const $noListsCreatedContainer = elements[0];
                    const $listItemsBoxes = elements[1];
                    const $listItems = elements[2];

                    $listItemsBoxes.forEach(item => {
                        item.classList.add('hidden');
                    });

                    if (noListsCreated) {
                        $noListsCreatedContainer.classList.remove('hidden');
                    } else {
                        $noListsCreatedContainer.classList.add('hidden');

                        $listItems.forEach(item => {
                            item.classList.remove('hidden');
                        });
                    }
                },
                showLoadingLists: function (elements) {
                    const $noListsCreatedContainer = elements[0];
                    const $listItemsBoxes = elements[1];
                    const $listItems = elements[2];

                    $noListsCreatedContainer.classList.add('hidden');

                    $listItemsBoxes.forEach(item => {
                        item.classList.remove('hidden');
                    });

                    $listItems.forEach(item => {
                        item.classList.add('hidden');
                    });
                },
                showLoadingTaskLists: function (elements) {
                    const $modalAddTaskListElements = elements[0];

                    const $taskListItemBoxes = elements[1];
                    const $taskListItems = elements[2];

                    $modalAddTaskListElements.forEach(element => {
                        element.disabled = true;
                    });

                    $taskListItemBoxes.forEach(item => {
                        item.classList.remove('hidden');
                    });

                    $taskListItems.forEach(item => {
                        item.classList.add('hidden');
                    });
                },
                showTaskLists: function (elements) {
                    const $modalAddTaskListElements = elements[0];

                    const $taskListItemBoxes = elements[1];
                    const $taskListItems = elements[2];

                    $modalAddTaskListElements.forEach(element => {
                        element.disabled = false;
                    });

                    $taskListItemBoxes.forEach(item => {
                        item.classList.add('hidden');
                    });

                    $taskListItems.forEach(item => {
                        item.classList.remove('hidden');
                    });
                }
            }
        }
    );