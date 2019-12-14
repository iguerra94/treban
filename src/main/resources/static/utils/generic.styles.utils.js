angular.module('treban')
    .factory('genericStylesUtils',
        function() {
            return {
                closeMessageBox: function(messageBox) {
                    messageBox.classList.add("hidden");
                },
                closeMessageBoxIfVisible: function(messageBox) {
                    if (messageBox.classList.contains("hidden")) return;

                    if (!messageBox.classList.contains("hidden")) {
                        this.closeMessageBox(messageBox);
                    }
                },
                validateEmptyAttributes: function (data, elements) {
                    const errorMessageBox = elements[0];
                    const errorMessage = elements[1];

                    for (const prop in data) {
                        if (data[`${prop}`] === null || data[`${prop}`].length === 0) {
                            errorMessage.textContent = "Ninguno de los campos deben quedar vacios.";
                            errorMessageBox.classList.remove("hidden");

                            return false;
                        }
                    }

                    return true;
                },
                showErrorMessage: function (message, elements) {
                    const errorMessageBox = elements[0];
                    const errorMessage = elements[1];

                    errorMessage.textContent = message;
                    errorMessageBox.classList.remove("hidden");
                }
            }
        }
    );