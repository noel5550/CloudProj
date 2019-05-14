// src/index.js
const m = require("mithril").default

var UserList = require("./views/UserList");
var UserForm = require("./views/UserForm");
var PetView = require("./views/PetView");
var Layout = require("./views/Layout");

m.route(document.body, "/list", {
    "/list": {
        render: function(){
            return m(Layout, m(UserList));
        }
    },
    "/view/:id": {
        render: function(vnode) {
            return m(Layout, m(PetView, vnode.attrs))
        }
    },
    "/create": {
        render: function(){
            return m(Layout, m(UserForm));
        }
    },
})