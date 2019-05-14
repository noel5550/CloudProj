/*
TODO : changer la vue pour un titre très gros, la description et le nombre de signataires de la pétition
garder le bouton signer
*/

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
            m("h1", User.current.firstName),
            m("h6", "Proposé par : " + User.current.firstName + " " + User.current.lastName),
            m("br"),
            m("h4", User.current.lastName + " signataires"),
            m("p", "lorem ipsum blablabla Nous configurons le Node ID de l’ancre en dans le message d’avertising, et pouvons donc associer la node à des coordonnées GPS pour réaliser nous même une triangulation de l’utilisateur mobile."),
            m("button", {class: "btn btn-primary my-2 my-sm-0"},"Signer la pétition"),
        ])
    }
}