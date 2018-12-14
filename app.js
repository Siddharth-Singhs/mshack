var express = require("express"),
    app     = express();

app.set("view-engine", "ejs");
app.use(express.static("public"));

// ROUTES
app.get("/", function(req, res){
    res.redirect("/home");
});

app.get("/home", function(req, res){
    res.render("index");
});


// LISTEN
var PORT = 3000
app.listen(PORT, function(err){
    if(err){
        console.log(err);
    }
    else {
        console.log("Server has started on PORT "+PORT);
    }
});