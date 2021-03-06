console.log("Map Connected");
var lattitude = [];
var longitude = [];

var database = firebase.database().ref().child("Coordinates");

database.on("child_added", snap => {
    var lat = snap.child("Lattitude").val();
    lattitude.push(lat);
    var long = snap.child("Longitude").val();
    longitude.push(long);

})


var centre = new L.LatLng(28.549948, 77.268241);
var map=new MapmyIndia.Map("map",{ center:centre,zoomControl: true,hybrid:true });
var marker = L.marker(centre);
// L.marker(centre).addTo(map);



for(var i=0; i<lattitude.length; i++){
    console.log(lattitude[i] + " " + longitude[i] )
    var temp = new L.LatLng(lattitude[i],longitude[i]);
    L.marker(temp).addTo(map);

    showCircle();
    function showCircle() {
        lat = lattitude[i];
        long = longitude[i];
        var radius = 100;
        marker.setLatLng([lat, long]);
        currentDiameter = L.circle([lat, long], {
            color: '3aff40',
            fillColor: '#abff00',
            fillOpacity: 0.5,
            radius: radius
        });
        currentDiameter.addTo(map);
        map.fitBounds(currentDiameter.getBounds());
    }
}




