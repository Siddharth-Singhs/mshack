var express = require("express"),
    app     = express(),
    bodyParser = require("body-parser"),
    loginid,
    password;

app.set("view engine", "ejs");
app.use(express.static("public"));

app.use(bodyParser.urlencoded({extended: true}));

// ROUTES
app.get("/", function(req, res){
    res.redirect("/login");
});

app.get("/login", function(req, res){
    res.render("login");
});

app.get("/home", function(req, res){
    res.render("home");
});

// app.get("/maps", function(req, res){
//     res.render("maps");
// });

app.post("/login", function (req, res) {
    loginid = req.body.loginid;
    password = req.body.password;
    if(loginid=="kunal" && password=="1234"){
        res.redirect("/home")
    }
    else {
        res.redirect("/login")
    }
})


// LISTEN
var PORT = 5000
app.listen(PORT, function(err){
    if(err){
        console.log(err);
    }
    else {
        console.log("Server has started on PORT "+PORT);
    }
});