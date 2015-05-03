/*global Webapp, Backbone*/

Webapp.Models = Webapp.Models || {};

(function () {
    'use strict';

    Webapp.Models.ChessApp = Backbone.Model.extend({

        url: '',

        initialize: function() {
        },

        defaults: {
        },

        validate: function(attrs, options) {
        },

        parse: function(response, options)  {
            return response;
        }
    });

})();
