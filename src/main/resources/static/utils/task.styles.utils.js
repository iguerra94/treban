angular.module('treban')
    .factory('taskStylesUtils',
        function() {
            return {
                showAddTaskSuccessMessage: function (elements) {
                    const $successMessageBox = elements[0];
                    const $successMessage = elements[1];

                    $successMessageBox.classList.remove("hidden");
                    $successMessage.textContent = "La tarea fue creada con exito.";

                    setTimeout(() => {
                        $successMessageBox.classList.add("hidden");
                    }, 6500)
                },
                showEditTaskSuccessMessage(elements) {
                    const $successMessageBox = elements[0];
                    const $successMessage = elements[1];

                    $successMessageBox.classList.remove("hidden");
                    $successMessage.textContent = "La tarea fue modificada con exito.";

                    setTimeout(() => {
                        $successMessageBox.classList.add("hidden");
                    }, 6500)
                },
                showDeleteTaskSuccessMessage: function (elements) {
                    const $successMessageBox = elements[0];
                    const $successMessage = elements[1];

                    $successMessageBox.classList.remove("hidden");
                    $successMessage.textContent = "La tarea fue eliminada con exito.";

                    setTimeout(() => {
                        $successMessageBox.classList.add("hidden");
                    }, 6500)
                },
                showLoadingTaskDetail: function (elements) {
                    const $btnOpenModalDeleteTask = elements[0];
                    const $modalContentColumnItemBoxes = elements[1];
                    const $modalContentColumnItemElements = elements[2];

                    if ($btnOpenModalDeleteTask) {
                        $btnOpenModalDeleteTask.disabled = true;
                    }

                    $modalContentColumnItemElements.forEach(item => {
                        item.classList.add("hidden");
                    });

                    $modalContentColumnItemBoxes.forEach(item => {
                        item.classList.remove('hidden');
                    });
                },
                showTaskDetail: function (elements) {
                    const $btnOpenModalDeleteTask = elements[0];
                    const $modalContentColumnItemBoxes = elements[1];
                    const $modalContentColumnItemElements = elements[2];

                    if ($btnOpenModalDeleteTask) {
                        $btnOpenModalDeleteTask.disabled = false;
                    }

                    $modalContentColumnItemBoxes.forEach(item => {
                        item.classList.add("hidden");
                    });

                    $modalContentColumnItemElements.forEach(item => {
                        item.classList.remove('hidden');
                    });
                },
                closeModalEditionElementsIfOpened: function (elements) {
                    const $modalContentColumnItemElementValueElements = elements[0];
                    const $modalContentColumnItemElementEditElements = elements[1];

                    $modalContentColumnItemElementValueElements.forEach(item => {
                        item.classList.remove("hidden");
                    });

                    $modalContentColumnItemElementEditElements.forEach(item => {
                        item.classList.add('hidden');
                    });
                },
                editTaskDetail: function (elements) {
                    const $modalContentColumnItemElementValue = elements[0];
                    const $modalContentColumnItemElementEdit = elements[1];

                    $modalContentColumnItemElementValue.classList.add('hidden');
                    $modalContentColumnItemElementEdit.classList.remove('hidden');
                },
                finishEditingTaskDetail: function (elements) {
                    const $modalContentColumnItemElementValue = elements[0];
                    const $modalContentColumnItemElementEdit = elements[1];

                    $modalContentColumnItemElementEdit.classList.add('hidden');
                    $modalContentColumnItemElementValue.classList.remove('hidden');
                }
            }
        }
    );