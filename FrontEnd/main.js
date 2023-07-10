const urlApi = "http://localhost:8080/api/v1/messaggio";



function sendForm(event) {
    let formId = document.getElementById('id').value;
    let formEmail = document.getElementById('email').value;
    let formTesto = document.getElementById('testo').value;
    event.preventDefault()
    axios.post(urlApi, {
        id: formId,
        email: formEmail,
        testo: formTesto
    }) 
    .then(function (response){
        console.log(response);
    })
    .catch(function (error){
        console.log(error);
    })
}


const form = document.getElementById("mexForm")
form.addEventListener("submit", sendForm);