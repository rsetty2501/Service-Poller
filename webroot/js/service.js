$(document).ready(function () {
    $('.modal').modal();
    getServices();
  }
)

// GET services data
function getServices(){

  let serviceRequest = new Request("/api/service");
  fetch(serviceRequest)
  .then(function (response) {return response.json();})
  .then(function (servicesList) {
    servicesList.forEach(service => {

      let table = document.getElementById("service-data");
      let row = table.insertRow();

      let cell0 = row.insertCell(0);
      let cell1 = row.insertCell(1);
      let cell2 = row.insertCell(2);
      let cell3 = row.insertCell(3);
      let cell4 = row.insertCell(4);

      cell0.innerText = service.id;
      cell1.innerText = service.name;
      cell2.innerText = service.url;
      cell3.innerText = service.dateTime;
      cell4.innerText = service.status;

    });
  });
}

// POST service
function postService(){
  // Extract service name
  let serviceName = document.querySelector('#service-name').value;

  // Extract url
  let url = document.querySelector('#url').value;

  let result = isValidUrl(url);

  if(result === true){

    // Store them in a JSON object
    let jsonObject = {serviceName: serviceName, url:url};

    fetch('/api/service', {
      method: 'post',
      headers: {
        'Accept': 'application/json, text/plain, */*',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(jsonObject)
    }).then(response=> location.reload());

  } else {
    window.alert("Invalid URL!");
    window.location.reload();
  }

}

function deleteService(){

  let id = document.querySelector('#service-id').value;
  console.log(id);
  fetch('/api/service', {
    method: 'delete',
    headers: {
      'Accept': 'application/json, text/plain, */*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({id:id})
  }).then(response=> location.reload());

}

function isValidUrl(url)
{
  let regexp =  /^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;
  return regexp.test(url);
}

