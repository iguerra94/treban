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
                showLoadingBoardSpinner: function (elements) {
                    const loadingBoardWrapper = elements[0];
                    const boardWrapper = elements[1];

                    loadingBoardWrapper.classList.remove('hidden');
                    boardWrapper.classList.add('hidden');
                },
                hideLoadingBoardSpinner: function (elements) {
                    const loadingBoardWrapper = elements[0];
                    const boardWrapper = elements[1];

                    loadingBoardWrapper.classList.add('hidden');
                    boardWrapper.classList.remove('hidden');
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
                    const taskListItems = elements[2];

                    if (list.collapsed) {
                        collapseTaskListIcon.style.transform = 'rotate(0deg)';
                        setTimeout(() => {
                            taskListItemsWrapper.style.height = '100%';
                            taskListItems.style.opacity = 1;
                        },200);
                    } else {
                        collapseTaskListIcon.style.transform = 'rotate(-180deg)';
                        setTimeout(() => {
                            taskListItemsWrapper.style.height = '0em';
                            taskListItems.style.opacity = 0;
                        },200);
                    }
                },
                showCustomSuccessMessage: function (data, elements) {
                    const $successMessage = elements[0];
                    const $successMessageBox = elements[1];

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
                }
            }
        }
    );