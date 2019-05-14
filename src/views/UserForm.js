// src/views/UserForm.js
const m = require('mithril').default;
var User = require("../models/User");

module.exports = {
    oninit: function(vnode) {User.load(vnode.attrs.id)},
    view: function() {
        return m("form", {
                onsubmit: function(e){
                    e.preventDefault();
                    User.save();
                }
            },[

            m("label.label", "Titre de la pétition"),
            m("input.input[type=text][placeholder=titre]",{
                oninput: function(e) {User.current.firstName = e.target.value},
                value: User.current.firstName
            }),
            m("label.label", "Texte de la pétition"),
            m("input.input[placeholder=texte]", {
                oninput: function(e) {User.current.lastName = e.target.value},
                value: User.current.lastName
            }),
            m("label.label", "Tags"),
            m("input.input[placeholder=tags]", {
                oninput: function(e) {User.current.Petition = e.target.value},
                value: User.current.Petition
            }),
            m("button.button[type=button]", "Publier !"),
        ])
    }
}