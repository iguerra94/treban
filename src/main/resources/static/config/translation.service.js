angular.module('treban').config(
    function ($translateProvider) {
        $translateProvider.translations('es', {
            'LOW': 'BAJA',
            'MEDIUM': 'MEDIA',
            'HIGH': 'ALTA'
        });

        $translateProvider.preferredLanguage('es');
    });