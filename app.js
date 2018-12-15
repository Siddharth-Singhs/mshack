var express = require("express"),
    app     = express(),
    bodyParser = require("body-parser"),
    lat,
    long,
    center;

app.set("view engine", "ejs");
app.use(express.static("public"));

app.use(bodyParser.urlencoded({extended: true}));

// ROUTES
app.get("/", function(req, res){
    res.redirect("/home");
});

app.get("/home", function(req, res){
    res.render("trial");
});

app.get("/maps", function(req, res){
    res.render("maps");
});

app.post("/search", function (req, res) {
    lat = parseFloat(req.body.lattitude);
    long = parseFloat(req.body.longitude);
    res.redirect("/home");
    center = [lat, long];
    console.log(center);
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