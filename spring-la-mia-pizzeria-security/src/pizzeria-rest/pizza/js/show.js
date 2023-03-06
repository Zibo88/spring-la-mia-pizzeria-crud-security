const URLParams=new URLSearchParams(window.location.search);
const pizzaId=URLParams.get('id');

axios.get('http://localhost:8080/api/pizza/' + pizzaId).then((response) => {
    console.log('richiesta ok', response);
  
    document.querySelector('#nome').innerHTML= `Nome: ${response.data.nome}`;
    document.querySelector('#descrizione').innerHTML= `Descrizione: ${response.data.descrizione}` ;
    document.querySelector('#foto').setAttribute('src', response.data.foto);
    document.querySelector('#prezzo').innerHTML= `Prezzo: ${response.data.prezzo}`;
    response.data.ingredients.forEach(ingredient => {
        document.querySelector('#ingredients').innerHTML += `<li class="my-3"> ${ingredient.name} </li>`
    });

    response.data.offers.forEach(offer => {
        document.querySelector('#offers').innerHTML += `<li class="my-3"> ${offer.title} </li>`
    });
   
    console.log(response.data.ingredients)
}).catch((response) => {
    console.error('errore nella richiesta', response);
    alert('Errore durante la richiesta!');
});
