function createPizza(event){
    axios.post('http://localhost:8080/api/pizza/create' , {
        nome:document.querySelector('#nome').value,
        descrizione:document.querySelector('#descrizione').value,
        foto:document.querySelector('#foto').value,
        prezzo:document.querySelector('#prezzo').value,
    }).then((response)=> {
        console.error('errore nella richiesta', response);
       
    })
}