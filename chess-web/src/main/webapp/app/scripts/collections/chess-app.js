/*global Webapp, Backbone*/

Webapp.Collections = Webapp.Collections || {};

(function () {
    'use strict';

    Webapp.Collections.ChessApp = Backbone.Collection.extend({

        model: Webapp.Models.ChessApp

    });

})();
