console.log("Map Connected");
var lattitude = [];
var longitude = [];
var lattitude1 = [];
var longitude1 = [];


var database1 = firebase.database().ref().child("SafeCoordinates");

var database = firebase.database().ref().child("Coordinates");

database.on("child_added", snap => {
    var lat = snap.child("Lattitude").val();
    console.log(lat)
    lattitude.push(lat);
    var long = snap.child("Longitude").val();
    longitude.push(long);
    // $(".show").append("<p>"+lat+ " " + long + "</p>");
})
console.log(lattitude);

database1.on("child_added", snap => {
    var lat1 = snap.child("Lattitude").val();
    console.log(lat1)
    lattitude1.push(lat1);
    var long1 = snap.child("Longitude").val();
    longitude1.push(long1);
    // $(".show").append("<p>"+lat+ " " + long + "</p>");
})
console.log(lattitude1);

setTimeout(function(){
    var centre = new L.LatLng(28.549948, 77.268241);
    var map=new MapmyIndia.Map("map",{ center:centre,zoomControl: true,hybrid:true });
    var marker = L.marker(centre);
// L.marker(centre).addTo(map);

// var lattitude = [28.545, 28.546];
// var longitude = [77.268241, 77.266];

    for(var i=0; i<lattitude.length; i++){
        console.log(lattitude[i] + " " + longitude[i] )
        var temp = new L.LatLng(lattitude[i],longitude[i]);
        // L.marker(temp).addTo(map);

        showCircle();
        function showCircle() {
            lat = lattitude[i];
            long = longitude[i];
            var radius = 1000;
            marker.setLatLng([lat, long]);
            currentDiameter = L.circle([lat, long], {
                color: 'ff1517',
                fillColor: '#ff1517',
                fillOpacity: 0.5,
                radius: radius
            });
            currentDiameter.addTo(map);
            map.fitBounds(currentDiameter.getBounds());
        }
    }
    for(var i=0; i<lattitude1.length; i++){
        console.log(lattitude1[i] + " " + longitude1[i] )
        var temp = new L.LatLng(lattitude1[i],longitude1[i]);
        // L.marker(temp).addTo(map);
        var icon = L.icon(
            {
                iconUrl: '/images/marker.png',
                iconRetinaUrl: '/images/marker.png',
                iconSize: [30, 30],
                popupAnchor: [-3, -15]
            });

        showCircle();
        function showCircle() {
            lat = lattitude1[i];
            long = longitude1[i];
            var radius = 1000;
            marker.setLatLng([lat, long]);
            currentDiameter = L.circle([lat, long], {
                color: '186800',
                fillColor: '#186800',
                fillOpacity: 0.5,
                radius: radius
            });
            currentDiameter.addTo(map);
            map.fitBounds(currentDiameter.getBounds());
        }
    }


}, 5000);




