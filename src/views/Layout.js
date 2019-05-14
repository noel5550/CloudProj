// src/views/Layout.js
const m = require("mithril").default

module.exports = {
    view: function(vnode) {
        return m("main.layout", [
	        m("div", {id: "menu"}, m(menu1)),
	        m("section", vnode.children)
       ])
    }
}

var menu1 = {
    view: function() {
        return m("menu1", [
        m("nav", {class: "navbar navbar-light bg-light"}, 
            m("a[href='/list']",  {oncreate: m.route.link, class: "navbar-brand navbar-light bg-light"}, "TinyPet"),
            m("form", {class: "form-inline"},
                m("a[href='/create']", {oncreate: m.route.link, class: "btn btn-link my-2 my-sm-0"}, "Créer"),
                m("a[href='/mypet']", {oncreate: m.route.link, class: "btn btn-link my-2 my-sm-0"}, "Mes pétitions"),
                m("a[href='/logout']", {oncreate: m.route.link, class: "btn btn-primary my-2 my-sm-0"},"Déconnexion"))),
        ])
    }
}

var menu2 = {
    view: function() {
        return m("menu2", [
        m("nav", {class: "navbar"}, 
            m("a[href='/list']", {oncreate: m.route.link, class: "navbar-brand navbar-light bg-light"}, "TinyPet"),
            m("form", {class: "form-inline"},
                m("a[href='/login']", {oncreate: m.route.link, class: "btn btn-primary my-2 my-sm-0"},"Connexion"))),
        ])
    }
}